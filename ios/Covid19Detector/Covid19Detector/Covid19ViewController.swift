//
//  Covid19ViewController.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/24.
//

import UIKit
import Alamofire

let titleItems = ["확진환자","격리해제","치료중","사망자"]
var contentItems = ["","","",""]
var subItems = ["","","",""]




class Covid19ViewController: UIViewController {
    var model: CovidData?
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var dateText: UILabel!
    //    @IBOutlet weak var titleText1: UILabel!
    //    @IBOutlet weak var sumText1: UILabel!
    //    @IBOutlet weak var plusText1: UILabel!
    
   
    @IBOutlet weak var view1: UIView!
    @IBOutlet weak var view2: UIView!
    @IBOutlet weak var view3: UIView!
    @IBOutlet weak var view4: UIView!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view1.layer.cornerRadius = 10
        view2.layer.cornerRadius = 10
        view3.layer.cornerRadius = 10
        view4.layer.cornerRadius = 10
        
        // Do any additional setup after loading the view.
//        collectionView.delegate = self
//        collectionView.dataSource = self
//        collectionView.reloadData()
        
        //        // 닙파일을 가져온다
        //        let CollectionViewCellNib = UINib(nibName: String(describing: Covid19CollectionViewCell.self), bundle: nil)
        //
        //        // 가져온 닙파일로 콜렉션뷰에 쎌로 등록한다
        //        self.collectionView.register(CollectionViewCellNib, forCellWithReuseIdentifier: String(describing: Covid19CollectionViewCell.self))
        //        // 콜렉션뷰의 콜렉션뷰 레이아웃을 설정한다.
//        self.collectionView.collectionViewLayout = createCompositionalLayout()
        
//        apiCall()
        
        
//        testJson(patient: contentItems[0], release: contentItems[1], care: contentItems[2], dead: contentItems[3], add_patient: subItems[0], add_release: subItems[1], add_care: subItems[2], add_dead: subItems[3])
        
    }
//
//    func apiCall() {
//        let url = "http://121.152.10.41:4000/api/status"
//        let encodingURL = url.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
//        AF.request(encodingURL).responseData(completionHandler: { data in
//            guard let data = data.data else { return }
//            self.model = try? JSONDecoder().decode(CovidData.self, from: data)
//            self.collectionView.reloadData()
//        })
//    }
//
//    func append(){
//        contentItems[0] = model?.patient ?? "1"
//        contentItems[1] = model?.release ?? "2"
//        contentItems[2] = model?.care ?? "3"
//        contentItems[3] = model?.dead ?? "4"
//    }
//
//    func testJson(patient: String, release: String, care: String, dead: String, add_patient: String, add_release: String, add_care: String, add_dead: String) {
//
////        //접근하고자 하는 URL 정보
////        let URL = "http://121.152.10.41:4000/api/status"
////        //전송할 파라미터 정보
////        let PARAM:Parameters = [
////            //"축구선수" 혹은 "영화배우"를 넣어주세요.
////            "patient": "patient",
////            "release": "release",
////            "care": "care",
////            "dead": "dead",
////            "add_patient": "add_patient",
////            "add_release": "add_release",
////            "add_care": "add_care",
////            "add_dead": "add_dead"
////
////        ]
////        //위의 URL와 파라미터를 담아서 POST 방식으로 통신하며, statusCode가 200번대(정상적인 통신) 인지 유효성 검사 진행
////        let alamo = AF.request(URL, method: .get, parameters: PARAM).validate(statusCode: 200..<300)
////        //결과값으로 JSON을 받을 때 사용
////        alamo.responseJSON() { response in
////            switch response.result
////            {
////            //통신성공
////            case .success(let value):
////
////                if let jsonObj = value as? [Dictionary<String, Any>]
////                {
////                    //JSON 배열의 갯수
////                    print("데이터의 갯수: \(jsonObj.count)")
////
////                    for item in jsonObj
////                    {
////                        print("\n--------------------------------------------")
////                        //JSON 배열의 n번째 값
////                        print("item: \(item)")
////
////                        //JSON의 단일 값 접근
////                        let patient = item["patient"]! as? String ?? ""
////                        let release = item["release"]! as? String ?? ""
////                        let care = item["care"]! as? String ?? ""
////                        let dead = item["dead"]! as? String ?? ""
////                        let add_patient = item["add_patient"]! as? String ?? ""
////                        let add_release = item["add_release"]! as? String ?? ""
////                        let add_care = item["add_care"]! as? String ?? ""
////                        let add_dead = item["add_dead"]! as? String ?? ""
////
////                        print("patient: \(patient)")
////                        print("release: \(release)")
////                        print("care: \(care)")
////                        print("dead: \(dead)")
////                        print("add_patient: \(add_patient)")
////                        print("add_release: \(add_release)")
////                        print("add_care: \(add_care)")
////                        print("add_dead: \(add_dead)")
////                    }
////                }
////
////            //통신실패
////            case .failure(let error):
////                print("error: \(String(describing: error.errorDescription))")
////            }
////        }
//
//                    let URL = "http://121.152.10.41:4000/api/status"
//
//
//                    let PARAM: Parameters = [
//                        "patient": "patient",
//                        "release": "release",
//                        "care": "care",
//                        "dead": "dead",
//                        "add_patient": "add_patient",
//                        "add_release": "add_release",
//                        "add_care": "add_care",
//                        "add_dead": "add_dead"
//
//                    ]
//                    //위의 URL와 파라미터를 담아서 POST 방식으로 통신하며, statusCode가 200번대(정상적인 통신) 인지 유효성 검사 진행
//                    let alamo = AF.request(URL, method: .get, parameters: PARAM).validate(statusCode: 200..<300)
//                    //결과값으로 문자열을 받을 때 사용
//                    alamo.responseString() { response in
//                        switch response.result
//                        {
//                        //통신성공
//                        case .success(let value):
//                            print("value: \(value)")
//            //                self.resultLabel.text = "\(value)"
//            //                self.sendImage(value: value)
//
//                        //통신실패
//                        case .failure(let error):
//                            print("error: \(String(describing: error.errorDescription))")
//            //                self.resultLabel.text = "\(error)"
//                        }
//                    }
////
//    }
//    /*
//     // MARK: - Navigation
//
//     // In a storyboard-based application, you will often want to do a little preparation before navigation
//     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//     // Get the new view controller using segue.destination.
//     // Pass the selected object to the new view controller.
//     }
//     */
//
//}
//
//extension Covid19ViewController {
//
//    // 콤포지셔널 레이아웃 설정
//    func createCompositionalLayout() -> UICollectionViewLayout {
//        print("createCompositionalLayoutForFirst() called")
//        // 콤포지셔널 레이아웃 생성
//        let layout = UICollectionViewCompositionalLayout{
//            // 만들게 되면 튜플 (키: 값, 키: 값) 의 묶음으로 들어옴 반환 하는 것은 NSCollectionLayoutSection 콜렉션 레이아웃 섹션을 반환해야함
//            (sectionIndex: Int, layoutEnvironment: NSCollectionLayoutEnvironment) -> NSCollectionLayoutSection? in
//
//            // 아이템에 대한 사이즈 - absolute 는 고정값, estimated 는 추측, fraction 퍼센트
//            let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1.0), heightDimension: .fractionalHeight(1.0))
//
//            // 위에서 만든 아이템 사이즈로 아이템 만들기
//            let item = NSCollectionLayoutItem(layoutSize: itemSize)
//
//            // 아이템 간의 간격 설정
//            item.contentInsets = NSDirectionalEdgeInsets(top: 2, leading: 2, bottom: 2, trailing: 2)
//
//            // 변경할 부분
//            let groupHeight =  NSCollectionLayoutDimension.fractionalWidth(1/3)
//
//            // 그룹사이즈
//            let grouSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1.0), heightDimension: groupHeight)
//
//            // 그룹사이즈로 그룹 만들기
//            //            let group = NSCollectionLayoutGroup.horizontal(layoutSize: grouSize, subitems: [item, item, item])
//
//            // 변경할 부분
//            let group = NSCollectionLayoutGroup.horizontal(layoutSize: grouSize, subitem: item, count: 2)
//
//            // 그룹으로 섹션 만들기
//            let section = NSCollectionLayoutSection(group: group)
//            //            section.orthogonalScrollingBehavior = .groupPaging
//
//            // 섹션에 대한 간격 설정
//            section.contentInsets = NSDirectionalEdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20)
//            return section
//        }
//        return layout
//    }
//}
//
////extension Covid19ViewController: UICollectionViewDelegate {
////
////}
////
////extension Covid19ViewController: UICollectionViewDataSource {
////    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
////        return titleItems.count
////    }
////
////    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
////        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CollectionCell", for: indexPath) as! Covid19CollectionViewCell
////
////        cell.layer.cornerRadius = 15
////
////
//////       cell.titleLabel.text = titleItems[indexPath.row]
/////        cell.sumLabel.text = model?.patient
/////        cell.plusLabel.text = model?.add_patient
////        return cell
////    }
////
////
////}
}
