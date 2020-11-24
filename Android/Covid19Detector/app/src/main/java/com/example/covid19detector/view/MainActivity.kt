package com.example.covid19detector.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.covid19detector.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    
    override fun onLocationChanged(location: Location) {
        currentPosition = LatLng(location.getLatitude(), location.getLongitude())
        Log.d(TAG, "onLocationChanged : ")
        val markerTitle: String = getCurrentAddress(currentPosition)
        val markerSnippet = ("위도:" + java.lang.String.valueOf(location.getLatitude())
                + " 경도:" + java.lang.String.valueOf(location.getLongitude()))

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location)
        mCurrentLocatiion = location
    }
    // endregion

    // 현재 위도, 경도 얻어오기
    override fun getCurrentAddress(latlng: LatLng): String? {
        // GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>
        addresses = try {
            geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1)
        } catch (ioException: IOException) {
            // 네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: java.lang.IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        return if (addresses == null || addresses.size == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            "주소 미발견"
        } else {
            val address: Address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }
    // endregion

    // 맵을 현재 위치에 맞춤
    override fun setCurrentLocation(location: Location) {
        mMoveMapByUser = false
        val currentLatLng = LatLng(location.getLatitude(), location.getLongitude())
        if (mMoveMapByAPI) {
            Log.d(TAG, ("setCurrentLocation: mGoogleMap moveCamera "
                    + location.getLatitude()) + " " + location.getLongitude())
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
            mGoogleMap.moveCamera(cameraUpdate)
        }
    }

    // region 런타임 permission 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        val fineLocationRationale: Boolean = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
        val hasFineLocationPermission: Int = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED && fineLocationRationale) showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.") else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.")
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음")
            if (mGoogleApiClient.isConnected() === false) {
                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음")
                mGoogleApiClient.connect()
            }
        }
    }
    // endregion
}
