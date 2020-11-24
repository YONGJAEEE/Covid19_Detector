package com.example.covid19detector.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.covid19detector.R
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


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    val markerOptions : MarkerOptions = MarkerOptions()
    var fusedLocationProviderClient : FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null // 위치 요청
    var locationCallback: MainActivity.MyLocationCallBack? = null // 내부 클래스, 위치 변경 후 지도에 표시.
    val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    val REQUEST_ACCESS_FINE_LOCATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_adress.setOnClickListener(){
            btnClick()
        }
        btn_search.setOnClickListener(){
            btnClick()
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        // as 는 형변환
        mapFragment.getMapAsync(this)
        // 비동기 -> 기다리지 않고 처리하는 것(타이밍을 맞추지 않고 처리)
        // 전화기, 무전기
        locationInit()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
            )
            != PackageManager.PERMISSION_GRANTED
// 사용자 권한 체크로
// 외부 저장소 읽기가 허용되지 않았다면
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
    fun btnClick(){
        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
        locationRequest!!.interval = 10000 // 내 위치 지도 전달 간격
        locationRequest!!.fastestInterval = 5000 // 지도 갱신 간격.

    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 지도가 준비되었다면 호출.
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

    fun removeLocationLister(){
        fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        // 어플이 종료되면 지도 요청 해제.
    }

    @SuppressLint("MissingPermission")
    fun addLocationListener() {
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
    }
    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation

            location?.run {
                val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

                polyLineOptions.add(latLng) // polyline 기준을 latLng으로 설정

                mMap.addPolyline(polyLineOptions) // googleMap에 ployLine을 그림.
                mMap.addMarker(MarkerOptions().position(latLng).title("ㅎㅎ"))
            }
        }
    }
}
