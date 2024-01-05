//
//  MovieCardView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/20/23.
//

import SwiftUI

struct MovieCardView: View {
    let movie: Movie
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(movie.title)
                .font(.headline)
            
            Text(movie.genre.movieGenre)
                .font(.subheadline)
            
            
            Text(movie.releaseDate, style: .date)
                .font(.caption)
        }
        .padding()
        .background(.regularMaterial)
        .clipShape(.rect(cornerRadius: 10))
    }
}
