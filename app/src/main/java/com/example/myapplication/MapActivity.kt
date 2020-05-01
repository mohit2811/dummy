package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks {
    var line: ArrayList<LatLng> = ArrayList<LatLng>()
    var new_lat = 0.0
    var new_long = 0.0
    var count = 0
    var marknext: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment: SupportMapFragment? = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        marknext = findViewById(R.id.marknext)
        val lm =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        new_lat = location.longitude
        new_long = location.latitude
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val currentLocation = LatLng(new_lat, new_long)
        googleMap.addMarker(
            MarkerOptions().position(currentLocation)
                .title("Marker number $new_lat $new_long")
                .snippet((count + 1).toString())
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        line.add(currentLocation)
        count++
        marknext!!.setOnClickListener { marknext(googleMap) }
    }

    override fun onConnected(bundle: Bundle?) {}
    override fun onConnectionSuspended(i: Int) {}
    fun marknext(googleMap: GoogleMap) {
        val meters = 200.0

// number of km per degree = ~111km (111.32 in google maps, but range varies
        //between 110.567km at the equator and 111.699km at the poles)
// 1km in degree = 1 / 111.32km = 0.0089
// 1m in degree = 0.0089 / 1000 = 0.0000089
        val degree = meters * 0.0000089
        new_lat = new_lat + degree
        val angle = Random().nextInt(360).toDouble()
        val pi = Math.PI / angle
        new_long = new_long + degree / Math.cos(new_lat * pi)
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(new_lat, new_long))
                .title("Marker number lat= $new_lat  long = $new_long")
                .snippet((count + 1).toString())
        )
        Toast.makeText(this, "Marker number $count lat= $new_lat  long = $new_long", Toast.LENGTH_SHORT).show()
        line.add(LatLng(new_lat, new_long))
        count++
        if (count % 5 == 0) {
            val polylineOptions = PolylineOptions()
            polylineOptions.color(Color.RED)
            polylineOptions.width(3F)
            polylineOptions.addAll(line)
            googleMap.addPolyline(polylineOptions)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(new_lat, new_long)))
        }
    }
}