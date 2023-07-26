package com.yosha10.final_project.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.yosha10.final_project.core.ui.ViewModelFactory
import com.yosha10.final_project.core.utils.DateFormatter
import com.yosha10.final_project.core.utils.DisasterType
import com.yosha10.final_project.databinding.ActivityMainBinding
import com.yosha10.final_project.setting.SettingsActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mMap: GoogleMap

    private lateinit var disasterListAdapter: DisasterListAdapter

    private var filterDisaster: String? = null
    private var filterLocation: String? = null

    private val boundsBuilder = LatLngBounds.Builder()

    private var mapNotReady: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide Action Bar
        supportActionBar?.hide()

        // Init Adapter
        setupAdapter()

        // Show SearchBar
        setupSearchBar()

        // Init ViewModel
        val factory = ViewModelFactory.getInstance(this@MainActivity)

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        // Call All Report
        getAllReport(admin = filterLocation, disaster = filterDisaster, timeperiod = 432_000)


        // Show Map Fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

    private fun getAllReport(
        admin: String? = null,
        disaster: String? = null,
        timeperiod: Int? = null
    ) {
        mainViewModel.getAllReport(admin = admin, disaster = disaster, timeperiod = timeperiod)
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
                                        val createdAt = DateFormatter.formatDate(item.properties.created_at)
                                        val coordinate =
                                            LatLng(item.coordinates[1], item.coordinates[0])
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(coordinate)
                                                .title(item.properties.disaster_type.replaceFirstChar {
                                                    if (it.isLowerCase()) it.titlecase(
                                                        Locale.getDefault()
                                                    ) else it.toString()
                                                })
                                                .icon(
                                                    BitmapDescriptorFactory.defaultMarker()
                                                )
                                                .snippet(getString(R.string.created_at_text, createdAt))
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

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }

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

    private fun setupAdapter() {
        disasterListAdapter = DisasterListAdapter()
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
                getAllReport(
                    admin = filterLocation,
                    disaster = filterDisaster,
                    timeperiod = 432_000
                )
                true
            }
            show()
        }
    }
}