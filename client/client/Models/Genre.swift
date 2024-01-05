//
//  Genre.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import Foundation

struct Genre: Identifiable, Codable, Hashable {
    var id = Int.random(in: 1 ..< 1000)
    let movieGenre: String
    
    enum CodingKeys: String, CodingKey {
        case id = "genreID"
        case movieGenre
    }
}
