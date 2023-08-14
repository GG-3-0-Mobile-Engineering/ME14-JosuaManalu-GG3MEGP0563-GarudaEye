package com.yosha10.final_project.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.yosha10.final_project.R
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.ui.DisasterListAdapter
import com.yosha10.final_project.core.ui.RegionListAdapter
import com.yosha10.final_project.core.utils.DateFormatter
import com.yosha10.final_project.core.utils.DisasterType
import com.yosha10.final_project.core.utils.Region
import com.yosha10.final_project.databinding.ActivityMainBinding
import com.yosha10.final_project.detail.DetailDisasterActivity
import com.yosha10.final_project.detail.DetailDisasterActivity.Companion.EXTRA_DATA
import com.yosha10.final_project.notification.NotificationWorker
import com.yosha10.final_project.setting.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var mMap: GoogleMap

    private lateinit var disasterListAdapter: DisasterListAdapter

    private var filterDisaster: String? = null
    private var filterLocation: String? = null

    private val boundsBuilder = LatLngBounds.Builder()

    private var mapNotReady: Boolean = true

    private val regions = Region.listRegions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide Action Bar
//        supportActionBar?.hide()

        // Init Adapter
        setupDisasterAdapter()

        // Show SearchBar and SearchView
        setupSearchBar()
        setupSearchViewSuggestion()

        // Call All Report
        callApi()

        // Show Map Fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Show Notification
        setupNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        with(binding) {
            if (searchView.isShowing) {
                searchView.hide()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        mMap.setPadding(0, 0, 0, 600)
        mapNotReady = !mapNotReady
    }

    private fun callApi() {
        getAllReport(admin = filterLocation, disaster = filterDisaster)
    }

    private fun getAllReport(
        admin: String? = null,
        disaster: String? = null,
    ) {
        mainViewModel.getAllReport(admin = admin, disasterType = disaster)
            .observe(this) { report ->
                if (report != null) {
                    when (report) {
                        is Resource.Loading -> showLoading(true)

                        is Resource.Success -> {
                            showLoading(false)
                            if (report.data.isNullOrEmpty()) {
                                showTvError(true)
                            } else {
                                showTvError(false)
                                disasterListAdapter.setData(report.data)
                                if (!mapNotReady) {
                                    mMap.clear()
                                    report.data.forEach { item ->
                                        val disasterType = item.disasterType
                                        val disasterTypeText =
                                            when (DisasterType.valueOf(disasterType.uppercase())) {
                                                // the color of the badge card will change based on disaster type
                                                DisasterType.FLOOD -> DisasterType.FLOOD.IDvalue
                                                DisasterType.EARTHQUAKE -> DisasterType.EARTHQUAKE.IDvalue
                                                DisasterType.FIRE -> DisasterType.FIRE.IDvalue
                                                DisasterType.HAZE -> DisasterType.HAZE.IDvalue
                                                DisasterType.WIND -> DisasterType.WIND.IDvalue
                                                DisasterType.VOLCANO -> DisasterType.VOLCANO.IDvalue
                                            }

                                        val createdAt =
                                            DateFormatter.formatDate(item.createdAt)
                                        val coordinate =
                                            LatLng(item.lat, item.lon)
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(coordinate)
                                                .title(disasterTypeText)
                                                .icon(
                                                    BitmapDescriptorFactory.defaultMarker()
                                                )
                                                .snippet(
                                                    getString(
                                                        R.string.created_at_text,
                                                        createdAt
                                                    )
                                                )
                                        )
                                        boundsBuilder.include(coordinate)

                                        val bounds: LatLngBounds = boundsBuilder.build()
                                        mMap.animateCamera(
                                            CameraUpdateFactory.newLatLngBounds(
                                                bounds,
                                                resources.displayMetrics.widthPixels,
                                                resources.displayMetrics.heightPixels,
                                                300
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        is Resource.Error -> {
                            showLoading(false)
                            Toast.makeText(this@MainActivity, report.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
    }

    private fun setupSearchViewSuggestion() {
        with(binding) {
            val regionAdapter = RegionListAdapter { item ->
                searchBar.text = item
                searchView.hide()
                filterLocation = regions[item]
                callApi()
            }

            rvRegion.adapter = regionAdapter
            rvRegion.layoutManager = LinearLayoutManager(this@MainActivity)
            regionAdapter.submitList(regions.keys.toList())

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val filteredRegions =
                        regions.filter { it.key.contains(s ?: "", ignoreCase = true) }
                    regionAdapter.submitList(filteredRegions.keys.toList())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.main_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.setting_menu -> {
                        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        false
                    }

                    R.id.filter_menu -> {
                        showFilterDisasters()
                        false
                    }

                    else -> false
                }
            }
        }
    }

    private fun showTvError(isError: Boolean) {
        if (isError) {
            binding.myBottomSheet.tvError.visibility = View.VISIBLE
            binding.myBottomSheet.rvListDisaster.visibility = View.GONE
        } else {
            binding.myBottomSheet.tvError.visibility = View.GONE
            binding.myBottomSheet.rvListDisaster.visibility = View.VISIBLE
        }
    }

    private fun setupDisasterAdapter() {
        disasterListAdapter = DisasterListAdapter()
        disasterListAdapter.onItemClick = { data ->
            val intentDetail = Intent(this, DetailDisasterActivity::class.java)
            intentDetail.putExtra(EXTRA_DATA, data)
            startActivity(intentDetail)
        }
        with(binding.myBottomSheet.rvListDisaster) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = disasterListAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAnimation.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Showing menu filter disasters
    private fun showFilterDisasters() {
        val filterView = findViewById<View>(R.id.filter_menu) ?: return
        PopupMenu(this, filterView).run {
            menuInflater.inflate(R.menu.filter_disasters, menu)

            setOnMenuItemClickListener { itemMenu ->
                val disasterType = when (itemMenu.itemId) {
                    R.id.flood -> DisasterType.FLOOD
                    R.id.earthquake -> DisasterType.EARTHQUAKE
                    R.id.wind -> DisasterType.WIND
                    R.id.haze -> DisasterType.HAZE
                    R.id.fire -> DisasterType.FIRE
                    R.id.volcano -> DisasterType.VOLCANO
                    else -> null
                }
                disasterType?.let {
                    filterDisaster = it.name.lowercase()
                }
                callApi()
                true
            }
            show()
        }
    }

    private fun setupNotification() {
        val workManager = WorkManager.getInstance(this@MainActivity)

        val data = Data.Builder()
            .putString(NotificationWorker.NOTIFICATION_CHANNEL_ID, "Water Level Alert")
            .build()

        val periodicWorkReq =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 30, TimeUnit.MINUTES)
                .setInputData(data)
                .build()

        workManager.enqueue(periodicWorkReq)

    }
}