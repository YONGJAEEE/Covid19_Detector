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
    
    var locationManager: CLLocationManager!
    
    let GSMlatitude = 35.142985302362
    let GSMlongitude = 126.80071381358424
    
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
        
        
        
        let marker = GMSMarker()
        
        marker.position = CLLocationCoordinate2D(latitude: GSMlatitude, longitude: GSMlongitude)
        marker.title = "광주소프트웨어마이스터고등학교"
        marker.snippet = "광주광역시"
        marker.map = mapView
        
        mapView.settings.compassButton = true
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
    }
}

extension ViewController: GMSAutocompleteViewControllerDelegate {
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        print("Place name: \(String(describing: place.name))")
        dismiss(animated: true, completion: nil)
        
        
        self.mapView.clear()
        self.txtSearch.placeholder = "         장소를 입력하여 주세요."
        
        let cord2D = CLLocationCoordinate2D(latitude: (place.coordinate.latitude), longitude: (place.coordinate.longitude))
        let marker = GMSMarker()
        
        marker.position =  cord2D
        marker.title = place.name
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
