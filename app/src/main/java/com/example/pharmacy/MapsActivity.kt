package com.example.pharmacy

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.pharmacy.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


private  const val LOCATION_PERMISSION_REQUEST_CODE = 2000
private const val  DEFAULT_MAP_SCALE = 13.0F

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val farmacias = mutableListOf<Farmacias>()
    private lateinit var farmaciaIcon: BitmapDescriptor
    private val userLocation = Location("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        farmacias.add(Farmacias("FARMACIA EL RENACIMIENTO", 19.92097589, -96.85714046))
        farmacias.add(Farmacias("FARMACIA CANDELARIA", 19.92286247, -96.85613831))
        farmacias.add(Farmacias("FARMACIA CONCHITA", 19.92674877, -96.85460821))
        farmacias.add(Farmacias("FARMACIA DE CRISTO", 19.92882553, -96.85231419))
        farmacias.add(Farmacias("FARMACIA DE GENERICOS SIMILARES", 19.92816173, -96.85331022))
        farmacias.add(Farmacias("FARMACIA DE GENERICOS Y PATENTE", 19.92264073, -96.85160255))
        farmacias.add(
            Farmacias(
                "FARMACIA DE GENERICOS Y SIMILARES HERNANDEZ",
                19.9263733,
                -96.85565558
            )
        )
        farmacias.add(Farmacias("FARMACIA DE JESUS", 19.92796404, -96.85524423))
        farmacias.add(Farmacias("FARMACIA DEL ANGEL", 19.92853724, -96.85325074))
        farmacias.add(Farmacias("FARMACIA DEL CENTRO", 19.92838597, -96.85427965))
        farmacias.add(Farmacias("FARMACIA LA ASUNCION", 19.9249687, -96.85482097))
        farmacias.add(Farmacias("FARMACIA SAN JOSE", 19.92725175, -96.85463479))
        farmacias.add(Farmacias("FARMACIAS DE SIMILARES", 19.93723875, -96.85003188))
        farmacias.add(Farmacias("FARMACIAS DE SIMILARES", 19.92944954, -96.85300371))
        farmacias.add(Farmacias("FARMACIAS DE SIMILARES", 19.92724918, -96.85471421))
        farmacias.add(Farmacias("FARMACIAS DE SIMILARES", 19.93307014, -96.85124091))
        farmacias.add(Farmacias("FARMACIAS G I", 19.92568431, -96.8532791))
        farmacias.add(Farmacias("SUPER FARMACIA DEL PUEBLO", 19.9272557, -96.85456989))
        farmacias.add(Farmacias("FARMACIA GUADALAJARA", 19.93670104025009, -96.85006678104402))

        farmaciaIcon = getFarmaciaIcon()

        checkLocationPermission()
    }

    fun mostrarFarmacias(view: View) {
        val intent = Intent(this, DatosFarmacia::class.java)
        startActivity(intent)
    }

    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                getUserLocation()
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            getUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
                setupMap()
            }
        }
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getUserLocation()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showLocationPermissionRationaleDialog()
            } else {
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLocationPermissionRationaleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.need_location_permission_dialog_title)
            .setMessage(R.string.need_location_permission_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }.setNegativeButton(R.string.no) { _, _ ->
                finish()
            }
        dialog.show()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val userLatLng = LatLng(userLocation.latitude, userLocation.longitude)
        val userMarker = MarkerOptions().position(userLatLng)
        mMap.addMarker(userMarker)

        for (farmacia in farmacias) {
            val farmaciaPosition = LatLng(farmacia.latitud, farmacia.longitude)
            val farmaciaLocation = Location("")

            farmaciaLocation.latitude = farmacia.latitud
            farmaciaLocation.longitude = farmacia.longitude

            val distanceToFarmacia = farmaciaLocation.distanceTo(userLocation)

            val farmaciaMarkerOptions = MarkerOptions()
                .icon(farmaciaIcon)
                .position(farmaciaPosition)
                .title(farmacia.name)
                .snippet(getString(R.string.distance_to_format, distanceToFarmacia))

            mMap.addMarker(farmaciaMarkerOptions)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, DEFAULT_MAP_SCALE))
        }
    }



        private fun getFarmaciaIcon(): BitmapDescriptor {
            val drawable = ContextCompat.getDrawable(this, R.drawable.farmacia)
            drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                drawable?.intrinsicWidth ?: 0,
                drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable?.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }


    }
