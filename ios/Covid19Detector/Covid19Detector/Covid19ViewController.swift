//
//  Covid19ViewController.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/24.
//

import UIKit

public let titleItems = ["확진환자","격리해제","치료중","사망자"]
public let contentItems = ["1","2","3","4"]
public let subItems = ["5","6","7","8"]


class Covid19ViewController: UIViewController {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var dateText: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        collectionView.delegate = self
        collectionView.dataSource = self
        
        // 닙파일을 가져온다
        let CollectionViewCellNib = UINib(nibName: String(describing: Covid19CollectionViewCell.self), bundle: .main)
        
        // 가져온 닙파일로 콜렉션뷰에 쎌로 등록한다
        self.collectionView.register(CollectionViewCellNib, forCellWithReuseIdentifier: "CollectionCell")
        // 콜렉션뷰의 콜렉션뷰 레이아웃을 설정한다.
        self.collectionView.collectionViewLayout = createCompositionalLayout()
        
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}

extension Covid19ViewController {
    
    // 콤포지셔널 레이아웃 설정
    func createCompositionalLayout() -> UICollectionViewLayout {
        print("createCompositionalLayoutForFirst() called")
        // 콤포지셔널 레이아웃 생성
        let layout = UICollectionViewCompositionalLayout{
            // 만들게 되면 튜플 (키: 값, 키: 값) 의 묶음으로 들어옴 반환 하는 것은 NSCollectionLayoutSection 콜렉션 레이아웃 섹션을 반환해야함
            (sectionIndex: Int, layoutEnvironment: NSCollectionLayoutEnvironment) -> NSCollectionLayoutSection? in
            
            // 아이템에 대한 사이즈 - absolute 는 고정값, estimated 는 추측, fraction 퍼센트
            let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1.0), heightDimension: .fractionalHeight(1.0))
            
            // 위에서 만든 아이템 사이즈로 아이템 만들기
            let item = NSCollectionLayoutItem(layoutSize: itemSize)
            
            // 아이템 간의 간격 설정
            item.contentInsets = NSDirectionalEdgeInsets(top: 2, leading: 2, bottom: 2, trailing: 2)
            
            // 변경할 부분
            let groupHeight =  NSCollectionLayoutDimension.fractionalWidth(1/3)
            
            // 그룹사이즈
            let grouSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1.0), heightDimension: groupHeight)
            
            // 그룹사이즈로 그룹 만들기
            //            let group = NSCollectionLayoutGroup.horizontal(layoutSize: grouSize, subitems: [item, item, item])
            
            // 변경할 부분
            let group = NSCollectionLayoutGroup.horizontal(layoutSize: grouSize, subitem: item, count: 2)
            
            // 그룹으로 섹션 만들기
            let section = NSCollectionLayoutSection(group: group)
            //            section.orthogonalScrollingBehavior = .groupPaging
            
            // 섹션에 대한 간격 설정
            section.contentInsets = NSDirectionalEdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20)
            return section
        }
        return layout
    }
}

extension Covid19ViewController: UICollectionViewDelegate {
    
}

extension Covid19ViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 4
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CollectionCell", for: indexPath) as? Covid19CollectionViewCell else {
            return UICollectionViewCell()
        }
        
        cell.titleText.text = titleItems[indexPath.row]
        cell.sumText.text = contentItems[indexPath.row]
        cell.plusText.text = subItems[indexPath.row]
        
        return cell
    }
    
    
}
