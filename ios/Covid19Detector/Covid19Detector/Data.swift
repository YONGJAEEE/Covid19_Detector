//
//  Data.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/25.
//

import Foundation



struct CovidData: Codable {
    let patient: String
    let release: String
    let care: String
    let dead: String
    let add_patient: String
    let add_release: String
    let add_care: String
    let add_dead: String
}
