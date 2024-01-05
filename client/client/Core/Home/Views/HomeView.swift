//
//  HomeView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct HomeView: View {
    @StateObject var viewModel = HomeViewModel()
    @State private var editMode: EditMode = EditMode.inactive
    
    @EnvironmentObject var authService: AuthService
    
    var body: some View {
        NavigationStack {
            VStack {
                // Scrollable list of available movies
                List(viewModel.movies, id: \.id, selection: $viewModel.selectedMovies) { movie in
                    MovieCellView(movie: movie)
                        .padding(.horizontal)
                        .padding(.vertical, 5)
                }
                .navigationTitle("Movies")
                .listStyle(InsetListStyle())
                .scrollIndicators(.hidden)
                .environment(\.editMode, $editMode)
                .animation(.default, value: editMode)
                .refreshable {
                    // re-fetch movies from API
                    print("DEBUG: Movie scroll view refreshed")
                    Task { await viewModel.fetchMovies() }
                }
                .toolbar {
                    // Edit Button
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button(editMode.isEditing ? "Done" : "Rent") {
                            editMode = editMode.isEditing ? EditMode.inactive : EditMode.active
                        }
                    }
                    
                    // Profile Button
                    ToolbarItem(placement: .topBarLeading) {
                        NavigationLink {
                            ProfileView()
                        } label: {
                            CircularProfileImageView()
                        }
                    }
                }
                .onChange(of: viewModel.selectedMovies) { oldValue, newValue in
                    if !oldValue.isEmpty && newValue.isEmpty && !editMode.isEditing {
                        print("DEBUG: User has finished selecting desired movies")
                        for movie in viewModel.movies {
                            if oldValue.contains(movie.id) {
                                Task { await MovieService.shared.rentMovie(forCustomer: authService.user!.id , movieID: movie.id) }
                            }
                        }
                    }
                }
            }
        }
    }
}

#Preview {
    HomeView()
}
