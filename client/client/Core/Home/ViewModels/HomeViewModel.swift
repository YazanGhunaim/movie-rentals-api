//
//  HomeViewModel.swift
//  client
//
//  Created by Yazan Ghunaim on 12/22/23.
//

import Foundation

@MainActor
class HomeViewModel: ObservableObject {
    @Published var movies = [Movie]()
    @Published var selectedMovies = Set<Int>()
    
    init() {
        Task { await fetchMovies() }
    }
    
    func fetchMovies() async {
        movies = await MovieService.shared.getMovies()
    }
}
