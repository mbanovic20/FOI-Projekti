package hr.foi.rampu.snackalchemist

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.io.IOException

class LocationActivityMain : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val requestCode = 123
    private var googleMap: GoogleMap? = null
    private lateinit var edtManualLocation: EditText
    private lateinit var btnSearch: ImageButton

    private lateinit var btnZatvoriDialog: ImageButton
    private lateinit var btnSpremi: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_main)

        btnZatvoriDialog = findViewById(R.id.btnZatvoriDialog)
        btnSpremi = findViewById(R.id.btnSpremi)

        btnZatvoriDialog.setOnClickListener {
            finish()
        }

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        edtManualLocation = findViewById(R.id.edtManualLocation)
        btnSearch = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener {
            val manualLocation = edtManualLocation.text.toString()
            if (manualLocation.isNotEmpty()) {
                val geocoder = Geocoder(this)
                try {
                    val addresses = geocoder.getFromLocationName(manualLocation, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val latitude = address.latitude
                        val longitude = address.longitude
                        if (googleMap != null) {
                            val location = LatLng(latitude, longitude)
                            googleMap?.addMarker(MarkerOptions().position(location).title(manualLocation))
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                        }
                    } else {
                        Toast.makeText(this, "Adresa nije pronađena.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Unesite lokaciju prije nego što pritisnete Pretraži.", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(this, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            getAndShowLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(locationPermission), requestCode)
        }

        btnSpremi.setOnClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            this.requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAndShowLocation()
                } else {
                    Toast.makeText(this, "Dozvola za lokaciju je potrebna za dohvaćanje vaše lokacije.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getAndShowLocation() {
        if (ContextCompat.checkSelfPermission(this, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null && googleMap != null) {
                            val userLocation = LatLng(location.latitude, location.longitude)
                            googleMap?.addMarker(MarkerOptions().position(userLocation).title("Korisnikova lokacija"))
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                        }
                    }
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException){
                    try {
                        exception.startResolutionForResult(this@LocationActivityMain, requestCode)
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(locationPermission), requestCode)
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        getAndShowLocation()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
