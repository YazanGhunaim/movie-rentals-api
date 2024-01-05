//
//  User.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import Foundation

struct User: Identifiable, Codable, Hashable {
    var id = Int.random(in: 1 ..< 1000)
    
    let forename, surname, email: String
    let rentedMovies: [Movie]
    
    var fullname: String {
        forename + " " + surname
    }
    
    enum CodingKeys: String, CodingKey {
        case id = "customerID"
        case forename, surname, email, rentedMovies
    }
}
