//
//  EditProfileView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/22/23.
//

import SwiftUI

struct EditProfileView: View {
    @EnvironmentObject var authService: AuthService
    @StateObject var viewModel = EditProfileViewModel()
    @Environment(\.dismiss) var dismiss
    let user: User
    
    var body: some View {
        NavigationStack {
            Form {
                // forename
                Section("Forename") {
                    TextField(user.forename, text: $viewModel.forename)
                        .autocorrectionDisabled()
                }
                // surname
                Section("Surname") {
                    TextField(user.surname, text: $viewModel.surname)
                        .autocorrectionDisabled()
                }
                // email
                Section("Email") {
                    TextField(user.email, text: $viewModel.email)
                        .textInputAutocapitalization(.never)
                }
                // Reset values back
                Section {
                    Button("Reset") {
                        viewModel.reset()                    }
                }
                // Save Changes
                Section {
                    Button(role: .destructive) {
                        print("DEBUG: User confirmed edit profile changes.")
                        Task {
                            await authService.editUser(
                                withForename: viewModel.forename,
                                withSurname: viewModel.surname,
                                withEmail: viewModel.email)
                        }
                        dismiss()
                    } label: {
                        Text("Save Changes")
                    }
                }
            }
            .navigationTitle("Edit profile")
        }
    }
}

#Preview {
    EditProfileView(user: DeveloperPreview.shared.user)
}
