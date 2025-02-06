package com.example.shopease

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var selectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Initialize the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set default location (e.g., New York)
        val defaultLocation = LatLng(40.7128, -74.0060)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Add a marker on map click
        mMap.setOnMapClickListener { latLng ->
            selectedMarker?.remove() // Remove previous marker if exists
            selectedMarker = mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
        }

        // Handle long click to confirm selection
        mMap.setOnMapLongClickListener { latLng ->
            if (selectedMarker != null) {
                val resultIntent = Intent().apply {
                    putExtra("location", "${latLng.latitude}, ${latLng.longitude}")
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Close the activity and return the result
            } else {
                Toast.makeText(this, "Please select a location first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
