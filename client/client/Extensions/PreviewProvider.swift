//
//  PreviewProvider.swift
//  client
//
//  Created by Yazan Ghunaim on 12/20/23.
//

import SwiftUI

class DeveloperPreview {
    static let shared = DeveloperPreview()
    
    let movies = [
        Movie(title: "Interstellar", releaseDate: Date(), genre: Genre(movieGenre: "Thriller"), available: true),
        Movie(title: "Spiderman", releaseDate: Date(), genre: Genre(movieGenre: "Action"), available: true),
        Movie(title: "The Nun", releaseDate: Date(), genre: Genre(movieGenre: "Horror"), available: false),
        Movie(title: "Central Intelligence", releaseDate: Date(), genre: Genre(movieGenre: "Comedy"), available: true),
        Movie(title: "Kungfu panda", releaseDate: Date(), genre: Genre(movieGenre: "Comedy"), available: false)
    ]
    
    let user = User(forename: "Yazan", surname: "Ghunaim", email: "yazanghunaim@gmail.com", rentedMovies: [
        Movie(title: "Interstellar", releaseDate: Date(), genre: Genre(movieGenre: "Thriller"), available: true),
        Movie(title: "Spiderman", releaseDate: Date(), genre: Genre(movieGenre: "Action"), available: true),
        Movie(title: "The Nun", releaseDate: Date(), genre: Genre(movieGenre: "Horror"), available: false),
        Movie(title: "Central Intelligence", releaseDate: Date(), genre: Genre(movieGenre: "Comedy"), available: true),
        Movie(title: "Kungfu panda", releaseDate: Date(), genre: Genre(movieGenre: "Comedy"), available: false)
    ])
}

extension PreviewProvider {
    static var dev: DeveloperPreview {
        return DeveloperPreview.shared
    }
}
