package co.lavapp.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_maps.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var longitute = ""
    var latitute = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




        var permissionCheck = ContextCompat.checkSelfPermission(

            this@MapsActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){

            if(this@MapsActivity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){

                var check=false
            }

        }else{

            var check=true
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener {

                Log.i("location", it.latitude.toString())
                val sydney = LatLng(it.latitude, it.longitude)
                latitute = it.latitude.toString()
                longitute = it.longitude.toString()


                mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                var ubicate = CameraUpdateFactory.newLatLng(sydney)
                var zoom = CameraUpdateFactory.zoomTo(14.toFloat())
                mMap.moveCamera(ubicate)
                mMap.animateCamera(zoom)



            }

        iamhere.setOnClickListener{

            setUbication(longitute, latitute)
            Toast.makeText(this@MapsActivity, "Location recorded successfully completed $longitute $latitute ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MapsActivity, MainActivity::class.java)
            startActivity(intent)

        }

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }

    fun setUbication(longitute : String, latitute: String){

        var newUbication = Ubication()

        newUbication.latitute = latitute
        newUbication.longitute = longitute

        var json = Gson().toJson(newUbication)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://159.203.156.208:8082")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var ubicationService : UbicationService = retrofit.create(UbicationService::class.java)

        var call = ubicationService.setUbication(JSONObject(json))

        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("error", t.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.i("response", response.body().toString())
            }

        })

    }}