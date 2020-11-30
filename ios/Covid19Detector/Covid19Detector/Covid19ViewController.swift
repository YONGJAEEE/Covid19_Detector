//
//  Covid19ViewController.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/24.
//

import UIKit
import Alamofire

class Covid19ViewController: UIViewController {
    var model: CovidData?
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var dateText: UILabel!
   
   
    @IBOutlet weak var view1: UIView!
    @IBOutlet weak var view2: UIView!
    @IBOutlet weak var view3: UIView!
    @IBOutlet weak var view4: UIView!
    
    @IBOutlet weak var sumPatient: UILabel!
    @IBOutlet weak var plusPatient: UILabel!
    
    @IBOutlet weak var sumChecking: UILabel!
    @IBOutlet weak var plusChecking: UILabel!
    
    @IBOutlet weak var sumRelease: UILabel!
    @IBOutlet weak var plusRelease: UILabel!
    
    @IBOutlet weak var sumDead: UILabel!
    @IBOutlet weak var plusDead: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view1.layer.cornerRadius = 10
        view2.layer.cornerRadius = 10
        view3.layer.cornerRadius = 10
        view4.layer.cornerRadius = 10
       
        apiCall()
        dataInput()
    }
    
    func apiCall() {
        let url = "http://121.152.10.41:4000/api/status"
        let encodingURL = url.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        AF.request(encodingURL).responseData(completionHandler: { data in
            guard let data = data.data else { return }
            self.model = try? JSONDecoder().decode(CovidData.self, from: data)
        })
    }
    
    func dataInput() {
        sumPatient.text = model?.patient
        plusPatient.text = model?.add_patient
        
        sumChecking.text = model?.care
        plusChecking.text = model?.add_care
        
        sumRelease.text = model?.release
        plusRelease.text = model?.add_release
        
        sumDead.text = model?.dead
        plusDead.text = model?.add_dead
    }
    
    
}
