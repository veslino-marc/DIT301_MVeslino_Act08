package com.example.simplelocationtrackerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationText: TextView
    private lateinit var refreshButton: Button
    private var googleMap: GoogleMap? = null
    private val handler = Handler(Looper.getMainLooper())
    
    // Demo location (Manila, Philippines)
    private var currentLat = 14.5995
    private var currentLng = 120.9842
    private var updateCount = 0
    
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationText = findViewById(R.id.locationText)
        refreshButton = findViewById(R.id.refreshButton)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Button click
        refreshButton.setOnClickListener {
            Toast.makeText(this, "Location refreshed!", Toast.LENGTH_SHORT).show()
            updateLocation()
        }

        // Request permission first
        if (!hasLocationPermission()) {
            requestLocationPermission()
        } else {
            startDemo()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        
        // Start demo immediately
        startDemo()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationText.text = "Requesting location permission..."
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun startDemo() {
        Toast.makeText(this, "Location tracking started!", Toast.LENGTH_SHORT).show()
        updateLocation()
        
        // Auto-update every 3 seconds
        handler.postDelayed(object : Runnable {
            override fun run() {
                updateLocation()
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun updateLocation() {
        updateCount++
        
        // Simulate movement
        currentLat += (Math.random() - 0.5) * 0.001
        currentLng += (Math.random() - 0.5) * 0.001
        
        val accuracy = 10.0f + (Math.random() * 5).toFloat()
        
        // Update text
        locationText.text = """
            📍 Location Update #$updateCount
            Latitude: ${"%.6f".format(currentLat)}
            Longitude: ${"%.6f".format(currentLng)}
            Accuracy: ${"%.1f".format(accuracy)}m
        """.trimIndent()

        // Update map
        val latLng = LatLng(currentLat, currentLng)
        googleMap?.let { map ->
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Current Location #$updateCount")
            )
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted! ✓", Toast.LENGTH_SHORT).show()
                startDemo()
            } else {
                Toast.makeText(this, "Permission denied - using demo mode", Toast.LENGTH_SHORT).show()
                startDemo()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
