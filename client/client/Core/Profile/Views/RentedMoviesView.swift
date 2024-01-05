//
//  RentedMoviesView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/20/23.
//

import SwiftUI

struct RentedMoviesView: View {
    let movies: [Movie]
    
    var body: some View {
        VStack(alignment: .leading) {
            // Rented movies title
            Text("Rented movies:")
                .font(.headline.bold())
            
            // Customer rented movies scroll view
            ScrollView(.horizontal) {
                HStack {
                    ForEach(movies) { movie in
                        // Movie card
                        MovieCardView(movie: movie)
                            .scrollTransition { content, phase in
                                content
                                    .opacity(phase.isIdentity ? 1.0 : 0.3)
                                    .scaleEffect(
                                        x: phase.isIdentity ? 1.0 : 0.7,
                                        y: phase.isIdentity ? 1.0 : 0.7
                                    )
                                    .offset(y: phase.isIdentity ? 0 : 10)
                            }
                    }
                }
                .scrollTargetLayout()
            }
            .scrollIndicators(.hidden)
            .safeAreaPadding(.horizontal)
            .scrollTargetBehavior(.viewAligned)
        }
        .ignoresSafeArea()
    }
}
