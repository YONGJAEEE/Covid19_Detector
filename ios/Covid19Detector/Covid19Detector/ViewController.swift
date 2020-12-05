//
//  ViewController.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/23.
//

import UIKit
import GooglePlaces
import GoogleMaps
import CoreLocation

var searchHistory: [String] = []

class ViewController: UIViewController {
    
    //LocationManager 선언
    var locationManager: CLLocationManager!
    
    
    //위도와 경도
    let GSMlatitude = 35.142985302362
    let GSMlongitude = 126.80071381358424
    
    let JEJUlatitude = 33.391917920866156
    let JEJUlongitude = 126.25355410006284
    
    
    @IBOutlet weak var vectorBtn: UIButton!
    @IBOutlet weak var txtSearch: UITextField!
    @IBOutlet weak var mapView: GMSMapView!
    @IBOutlet weak var covidBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        txtSearch.layer.borderColor = UIColor.black.cgColor
        txtSearch.layer.borderWidth = 1.0
        
        covidBtn.clipsToBounds = true
        covidBtn.layer.cornerRadius = covidBtn.layer.frame.size.width/2;
        
        vectorBtn.clipsToBounds = true
        vectorBtn.layer.cornerRadius = covidBtn.layer.frame.size.width/2;
        
        
//        let camera = GMSCameraPosition.camera(withLatitude: JEJUlatitude, longitude: JEJUlongitude, zoom: 6.0)
//        let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
//
//        view = mapView
        
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: JEJUlatitude, longitude: JEJUlongitude)
        marker.title = "제주홍익호텔"
        marker.snippet = "제주도"
        marker.map = mapView
        
        mapView.settings.compassButton = true
        
        // GOOGLE MAPS SDK: USER'S LOCATION
        mapView.isMyLocationEnabled = true
        mapView.settings.myLocationButton = true
        
    }
    
    @IBAction func locationTapped(_ sender: Any) {
        gotoPlaces()
    }
    
    func gotoPlaces() {
        txtSearch.resignFirstResponder()
        let acController = GMSAutocompleteViewController()
        acController.delegate = self
        present(acController, animated: true, completion: nil)
    }
    
}

extension ViewController: CLLocationManagerDelegate {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let locValue: CLLocationCoordinate2D = manager.location?.coordinate else { return }
        print("locations = \(locValue.latitude) \(locValue.longitude)")
        //        //locationManager 인스턴스 생성 및 델리게이트 생성
        //        locationManager = CLLocationManager()
        //        locationManager.delegate = self
        //
        //        //포그라운드 상태에서 위치 추적 권한 요청
        //        locationManager.requestWhenInUseAuthorization()
        //
        //        //배터리에 맞게 권장되는 최적의 정확도
        //        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        //
        //        //위치업데이트
        //        locationManager.startUpdatingLocation()
        //
        //        //위도 경도 가져오기
        //        let coor = locationManager.location?.coordinate
        //        latitude = coor?.latitude
        //        longitude = coor?.longitude
        
        //lblLocation.text = "latitude = \(locValue.latitude), longitude = \(locValue.longitude)"
    }
}

extension ViewController: GMSAutocompleteViewControllerDelegate {
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        print("Place name: \(String(describing: place.name))")
        dismiss(animated: true, completion: nil)
//        var count: Int = 0
        
        self.mapView.clear()
        self.txtSearch.placeholder = "         장소를 입력하여 주세요."
        
        
        let cord2D = CLLocationCoordinate2D(latitude: (place.coordinate.latitude), longitude: (place.coordinate.longitude))
        
        let marker = GMSMarker()
        marker.position =  cord2D
        marker.title = place.name
//        marker.snippet = place.name
        marker.map = mapView
        
        self.mapView.camera = GMSCameraPosition.camera(withTarget: cord2D, zoom: 15)
        
        searchHistory.append(place.name!)
    }
    
    
    func viewController(_ viewController: GMSAutocompleteViewController, didFailAutocompleteWithError error: Error) {
        print(error.localizedDescription)
    }
    
    func wasCancelled(_ viewController: GMSAutocompleteViewController) {
        dismiss(animated: true, completion: nil)
    }
    
    
}
//extension ViewController: CLLocationManagerDelegate {
//    // 2
//    func locationManager(
//        _ manager: CLLocationManager,
//        didChangeAuthorization status: CLAuthorizationStatus
//    ) {
//        // 3
//        guard status == .authorizedWhenInUse else { return }
//        // 4
//
//    }
//
//    // 6
//    func locationManager(
//        _ manager: CLLocationManager,
//        didUpdateLocations locations: [CLLocation]) {
//        guard let location = locations.first else {
//            return
//        }
//
//        // 7
//        mapView.camera = GMSCameraPosition(
//            target: location.coordinate,
//            zoom: 15,
//            bearing: 0,
//            viewingAngle: 0)
//    }
//
//    // 8
//    func locationManager(
//        _ manager: CLLocationManager,
//        didFailWithError error: Error
//    ) {
//        print(error)
//    }
//}


