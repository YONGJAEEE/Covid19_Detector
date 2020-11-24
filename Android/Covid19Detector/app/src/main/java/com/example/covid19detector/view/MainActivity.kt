package com.example.covid19detector.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.covid19detector.R
import com.example.covid19detector.model.CovidResponse
import com.example.covid19detector.model.PostBody
import com.example.covid19detector.model.PostResponse
import com.example.covid19detector.network.ApiRetrofitClient
import com.example.covid19detector.network.JusoRetrofitClient
import com.example.covid19detector.widget.DataUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    val markerOptions: MarkerOptions = MarkerOptions()
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null // 위치 요청
    var locationCallback: MainActivity.MyLocationCallBack? = null // 내부 클래스, 위치 변경 후 지도에 표시.
    val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    val REQUEST_ACCESS_FINE_LOCATION = 1000

    var currentLatLng = LatLng(0.1, 0.1)
    var tempLatLng = LatLng(0.1, 0.1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClickEvent()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
        locationInit()
        getright()

        DataUtil.LatLngData.observe(this, Observer {
            Handler().postDelayed({
                val latlng = LatLng(DataUtil.LatLngData.value!!.lat,DataUtil.LatLngData.value!!.lon)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17f))
                mMap.addMarker(MarkerOptions().position(latlng).title("ㅎㅎ"))
            }, 1000)
        })
    }

    fun onClickEvent(){
        tv_adress.setOnClickListener() {
            btnClick()
        }
        btn_search.setOnClickListener() {
            btnClick()
        }
        fab1.setOnClickListener {
            val intent = Intent(this,NowCovid19Activity::class.java)
            startActivity(intent)
        }
        fab2.setOnClickListener {
            // TODO: 2020-11-25 액티비티 이동
        }
        fab3.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
        }
    }

    fun btnClick() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    addLocationListener()
                } else {
                    Toast.makeText(this, "권한이 거부됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        locationRequest!!.interval = 5000 // 내 위치 지도 전달 간격
        locationRequest!!.fastestInterval = 5000 // 지도 갱신 간격.

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0) // 위도 경도, 변수에 저장.
        // 지도의 표시를 하고 제목을 추가.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // 마커 위치로 지도 이동.
    }

    override fun onResume() { // 잠깐 쉴 때.
        super.onResume()
        addLocationListener()
    }

    override fun onPause() {
        super.onPause()
    }

    fun removeLocationLister() {
        fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)

    }

    @SuppressLint("MissingPermission")
    fun addLocationListener() {
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    fun getright(){
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) { // 허용되지 않았다면 다시 확인.

                val builder = AlertDialog.Builder(
                    ContextThemeWrapper(
                        this,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
                builder.setTitle("권한 허용")
                builder.setMessage("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.")

                builder.setNegativeButton("허용하지 않음.") { _, _ ->
                    Toast.makeText(this, "허용하지 않았습니다. 앱을 다시 시작해주세요.", Toast.LENGTH_SHORT).show()
                }
                builder.setPositiveButton("허용") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION
                    )
                    Toast.makeText(this, "권한을 허용했습니다.", Toast.LENGTH_SHORT).show()
                }
                builder.show()


            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            addLocationListener()
        }
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation

            location?.run {
                val nowLatLng = LatLng(latitude, longitude)

                if (tempLatLng == LatLng(0.1, 0.1)) {
                    currentLatLng = nowLatLng
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
                    polyLineOptions.add(currentLatLng)
                    mMap.addPolyline(polyLineOptions)
                    mMap.addMarker(MarkerOptions().position(currentLatLng).title("ㅎㅎ"))
                }

                if (nowLatLng != tempLatLng) {
                    //서버로 좌표 전송
                    val postBody = PostBody(
                        nowLatLng.latitude.toString(),
                        nowLatLng.longitude.toString()
                    )
                    getPostedResponse(postBody)
                    tempLatLng = nowLatLng

                }
                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")
            }
        }
        fun getPostedResponse(postBody : PostBody) : Int{
            var score = 0
            val call : Call<PostResponse> = ApiRetrofitClient.instance.GetData.postLatLon(postBody)

            call.enqueue(object : retrofit2.Callback<PostResponse>{
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    score = response.body()!!.distance
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.w("Fail","Retrofit Failed.")
                }
            })
            return score
        }
    }
}