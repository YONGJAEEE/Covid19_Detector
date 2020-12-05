//
//  HistoryViewController.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/12/03.
//

import UIKit

class HistoryViewController: UIViewController {

    @IBOutlet weak var historyTableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()

        historyTableView.delegate = self
        historyTableView.dataSource = self
        historyTableView.tableFooterView = UIView()
        
        print(searchHistory)
    }
    
}

extension HistoryViewController: UITableViewDelegate {
    
}

extension HistoryViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return searchHistory.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = historyTableView.dequeueReusableCell(withIdentifier: "HistoryTableViewCell", for: indexPath) as! HistoryTableViewCell
        
        cell.historyText.text = searchHistory[indexPath.row]
        
        return cell
    }
    
    
}
