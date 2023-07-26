package com.yosha10.final_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yosha10.final_project.databinding.ActivityMainBinding
import com.yosha10.final_project.setting.SettingsActivity

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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
        Log.d("MainActivity", "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("OnMapReady", "onMapReady")
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    // Showing menu filter disasters
    private fun showFilterDisasters() {
        val filterView = findViewById<View>(R.id.filter_menu) ?: return
        PopupMenu(this, filterView).run {
            menuInflater.inflate(R.menu.filter_disasters, menu)

            show()
        }

    }
}