//
//  Covid19CollectionViewCell.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/24.
//

import UIKit
import Alamofire



class Covid19CollectionViewCell: UICollectionViewCell {
    
    
    
    
    @IBOutlet weak var titleText: UILabel!
    @IBOutlet weak var sumText: UILabel!
    @IBOutlet weak var plusText: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func testJson(id: String, value: String) {
            let URL = "http://172.30.1.50:8080/api/status"
            
            
            let PARAM: Parameters = [
                "id":id,
                "value": value,
                
            ]
            //위의 URL와 파라미터를 담아서 POST 방식으로 통신하며, statusCode가 200번대(정상적인 통신) 인지 유효성 검사 진행
            let alamo = AF.request(URL, method: .post, parameters: PARAM).validate(statusCode: 200..<300)
            //결과값으로 문자열을 받을 때 사용
            alamo.responseString() { response in
                switch response.result
                {
                //통신성공
                case .success(let value):
                    print("value: \(value)")
    //                self.resultLabel.text = "\(value)"
    //                self.sendImage(value: value)
                    
                //통신실패
                case .failure(let error):
                    print("error: \(String(describing: error.errorDescription))")
    //                self.resultLabel.text = "\(error)"
                }
            }
            
        }
}
