//
//  RegistrationView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct RegistrationView: View {
    @StateObject var viewModel = RegistrationViewModel()
    @EnvironmentObject var authService: AuthService
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        NavigationStack {
            VStack {
                Spacer()
                
                // App Logo
                AppLogo()
                
                // Text input Fields
                VStack {
                    TextField("Enter your forename", text: $viewModel.forename)
                        .modifier(TextFieldModifier())
                        .autocorrectionDisabled()
                    
                    TextField("Enter your surname", text: $viewModel.surname)
                        .modifier(TextFieldModifier())
                        .autocorrectionDisabled()
                    
                    TextField("Enter your email", text: $viewModel.email)
                        .textInputAutocapitalization(.never)
                        .modifier(TextFieldModifier())
                }
                
                // SignUp Button
                Button {
                    print("DEBUG: SignUp button pressed")
                    Task { await authService.createUser(withForename: viewModel.forename, withSurname: viewModel.surname, withEmail: viewModel.email) }
                } label: {
                    HStack {
                        Text("Sign Up")
                            .modifier(LoginButtonViewModifier())
                    }
                }
                .disabled(formIsInvalid)
                .padding()
                
                Spacer()
                
                Divider()
                
                // Dont have an account -> Registration View
                Button {
                    dismiss()
                } label: {
                    HStack(spacing: 3) {
                        Text("Already have an account?")
                        Text("Login")
                            .fontWeight(.semibold)
                    }
                    .foregroundStyle(.black)
                    .font(.footnote)
                }
                .padding(.vertical)
            }
        }
    }
}

extension RegistrationView: AuthenticationFormProtocol {
    var formIsInvalid: Bool {
        return viewModel.email.isEmpty || viewModel.forename.isEmpty || viewModel.surname.isEmpty || (viewModel.email.range(of:"^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", options: .regularExpression) == nil)
    }
}

#Preview {
    RegistrationView()
}

struct AppLogo: View {
    var body: some View {
        Image("logo")
            .resizable()
            .frame(width: 125, height: 150)
            .scaledToFit()
            .padding()
    }
}
