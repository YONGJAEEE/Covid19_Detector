//
//  Data.swift
//  Covid19Detector
//
//  Created by 조주혁 on 2020/11/25.
//

import Foundation

//struct Data: Codable {
//    let covidData: [CovidData]
//}

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

//struct CovidData: Codable {
//    let patient, release, care, dead: String
//    let addPatient, addRelease, addCare, addDead: String
//
//    enum CodingKeys: String, CodingKey {
//        case patient, release, care, dead
//        case addPatient = "add_patient"
//        case addRelease = "add_release"
//        case addCare = "add_care"
//        case addDead = "add_dead"
//    }
//}

