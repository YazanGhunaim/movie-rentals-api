//
//  MovieCellView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct MovieCellView: View {
    let movie: Movie
    
    var body: some View {
        HStack {
            Image("movie")
                .resizable()
                .frame(width: 100, height: 100)
            
            Divider()
            
            VStack(alignment: .leading, spacing: 5) {
                Text(movie.title)
                    .font(.title)
                
                Text(movie.genre.movieGenre)
                    .font(.subheadline)
                
                HStack {
                    Text(movie.releaseDate, style: .date)
                        .font(.caption)
                    
                    Spacer()
                    
                    Text(movie.available ? "available" : "out of stock")
                        .font(.caption)
                        .foregroundStyle(movie.available ? .green : .red)
                }
            }
            .padding(5)
        }
    }
}
