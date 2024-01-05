//
//  Movie.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import Foundation

typealias MovieResponse = [Movie]

struct Movie: Identifiable, Codable, Hashable {
    var id = Int.random(in: 1 ..< 1000)
    let title: String
    let releaseDate: Date
    let genre: Genre
    let available: Bool
    
    enum CodingKeys: String, CodingKey {
        case id = "movieID"
        case title, releaseDate, genre, available
    }
}
