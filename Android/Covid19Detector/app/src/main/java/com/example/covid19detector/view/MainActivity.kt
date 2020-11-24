package com.example.covid19detector.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    // 지도 조작을 위한 객체.
    val markerOptions : MarkerOptions = MarkerOptions()
    var fusedLocationProviderClient : FusedLocationProviderClient? = null
    // 위치값 얻어오기 객체
    var locationRequest: LocationRequest? = null // 위치 요청

    var locationCallback: MyLocationCallBack? = null // 내부 클래스, 위치 변경 후 지도에 표시.

    val polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    //경로를 표시할 펜 구성.
    // 다각으로 꺽어지는 선, 굵기는 5f, 색상은 빨강.

    val REQUEST_ACCESS_FINE_LOCATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 세로모드 고정.

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

                        // 권한 허용
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_ACCESS_FINE_LOCATION
                        )


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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
// 권한이 승인 됐다면
                    addLocationListener()
                } else {
// 권한이 거부 됐다면
                    Toast.makeText(this, "권한이 거부됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        // 현재 사용자 위치를 저장.
        locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // 위치 요청의 우선순위 = 높은 정확도 우선.
        locationRequest!!.interval = 10000 // 내 위치 지도 전달 간격
        locationRequest!!.fastestInterval = 5000 // 지도 갱신 간격.

    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 지도가 준비되었다면 호출.
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0) // 위도 경도, 변수에 저장.
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
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
    // 위험 권한 사용시 요청 코드가 호출되어야 하는데,
    // 없어서 발생됨. 요청 코드는 따로 처리 했음.
    fun addLocationListener() {
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        //위치 권한을 요청해야 함.
        // 액티비티가 잠깐 쉴 때,
        // 자신의 위치를 확인하고, 갱신된 정보를 요청
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation
            // 위도 경도를 지도 서버에 전달하면,
            // 위치에 대한 지도 결과를 받아와서 저장.

            location?.run {
                val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                // 지도에 애니메이션 효과로 카메라 이동.
                // 좌표 위치로 이동하면서 배율은 17 (0~19까지 범위가 존재.)

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")

//              markerOptions.position(latLng) // 마커를 latLng으로 설정
//
//              mMap.addMarker(markerOptions) // googleMap에 marker를 표시.

                polyLineOptions.add(latLng) // polyline 기준을 latLng으로 설정

                mMap.addPolyline(polyLineOptions) // googleMap에 ployLine을 그림.

            }
        }
    }
}
