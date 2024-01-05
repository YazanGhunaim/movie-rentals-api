//
//  ProfileView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var authService: AuthService
    @State private var showSheet = false
    @State private var showingAlert = false
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading, spacing: 5) {
                // User fullname
                VStack(alignment: .leading) {
                    HStack {
                        Text("Customer name: ")
                        
                        Text(authService.user?.fullname ?? "Saif Ghunaim")
                            .bold()
                    }
                    
                    // User email
                    HStack {
                        Text("Customer email: ")
                        
                        Text(authService.user?.email ?? "saif@gmail.com")
                            .bold()
                    }
                }
                .padding()
                
                // Rented Movies
                RentedMoviesView(movies: authService.user?.rentedMovies ?? [])
                    .padding(.horizontal)
                
                // Edit + Delete Buttons
                HStack {
                    // Edit functionality
                    Button {
                        showSheet.toggle()
                    } label: {
                        editProfileButton
                    }
                    
                    Spacer()
                    
                    // Delete functionality
                    Button {
                        // ask for confirmation
                        showingAlert.toggle()
                    } label: {
                        deleteProfileButton
                    }
                }
                .padding()
                
                // App logo
                Image("logo")
                    .resizable()
                    .padding()
                
                Spacer()
            }
            .navigationBarTitleDisplayMode(.inline)
            .onAppear {
                // Refetch rented movies
                if let user = authService.user {
                    Task { await authService.getUser(withForename: user.forename, withEmail: user.email) }
                }
            }
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    // Log Out Button
                    Button("Logout") { authService.logout() }
                }
            }
            .alert("Are you sure?", isPresented: $showingAlert) {
                Button(role: .destructive) {
                    // Deletion API Request
                    print("DEBUG: User requested to delete accout.")
                    Task { await authService.deleteUser() }
                } label: {
                    Text("Yes")
                }
            }
            .sheet(isPresented: $showSheet) {
                EditProfileView(user: authService.user!)
            }
        }
    }
}

extension ProfileView {
    var editProfileButton: some View {
        HStack {
            Image(systemName: "gear")
            Text("Edit profile")
        }
        .modifier(EditProfileButtonModifier())
    }
    
    var deleteProfileButton: some View {
        HStack {
            Image(systemName: "exclamationmark.triangle")
            Text("Delete profile")
        }
        .modifier(DeleteProfileButtonModifier())
    }
}

#Preview {
    ProfileView()
}
