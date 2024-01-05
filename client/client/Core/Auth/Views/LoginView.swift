//
//  LoginView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct LoginView: View {
    @StateObject var viewModel = LoginViewModel()
    @EnvironmentObject var authService: AuthService
    
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
                    
                    TextField("Enter your email", text: $viewModel.email)
                        .textInputAutocapitalization(.never)
                        .modifier(TextFieldModifier())
                }
                
                // Login Button
                Button {
                    print("DEBUG: Login button pressed")
                    Task { await authService.getUser(withForename: viewModel.forename, withEmail: viewModel.email) }
                } label: {
                    HStack {
                        Text("Login")
                            .modifier(LoginButtonViewModifier())
                    }
                }
                .disabled(formIsInvalid)
                .padding()
                
                Spacer()
                
                Divider()
                
                // Dont have an account -> Registration View
                NavigationLink {
                    RegistrationView()
                        .navigationBarBackButtonHidden(true)
                } label: {
                    HStack(spacing: 3) {
                        Text("Don't have an account?")
                        Text("Sign Up")
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

extension LoginView: AuthenticationFormProtocol {
    var formIsInvalid: Bool {
        return viewModel.email.isEmpty || viewModel.forename.isEmpty || (viewModel.email.range(of:"^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", options: .regularExpression) == nil)
    }
}

#Preview {
    LoginView()
}
