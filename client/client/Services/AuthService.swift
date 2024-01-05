//
//  AuthService.swift
//  client
//
//  Created by Yazan Ghunaim on 12/22/23.
//

import Foundation

protocol AuthenticationFormProtocol {
    var formIsInvalid: Bool { get }
}

@MainActor
class AuthService: ObservableObject {
    @Published var user: User?
    
    func logout() {
        user = nil
    }
    
    func createUser(withForename forename: String, withSurname surname: String, withEmail email: String) async {
        do {
            guard let url = URL(string: "http://localhost:8080/customer") else { return }
            var request = URLRequest(url: url)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "POST"
            
            let data = try JSONEncoder().encode(
                User(forename: forename,
                     surname: surname,
                     email: email,
                     rentedMovies: [])
            )
            
            let (responseData, response) = try await URLSession.shared.upload(for: request, from: data)
            user = try? JSONDecoder().decode(User.self, from: responseData)
            
            // Debug messaged
            if let httpResponse = response as? HTTPURLResponse {
                print(httpResponse.statusCode)
            }
            if let responseDataString = String(data: responseData, encoding: .utf8) {
                print("Response Data: \(responseDataString)")
            }
        } catch {
            print("DEBUG: Error sending create user request.")
        }
    }
    
    func getUser(withForename forename: String, withEmail email: String) async {
        do {
            guard let url = URL(string: "http://localhost:8080/customer/\(forename)/\(email)") else { return }
            let (responseData, _) = try await URLSession.shared.data(from: url)
            let decoder = JSONDecoder()
            
            // to account for weird java date format
            let formatter = DateFormatter()
            formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
            decoder.dateDecodingStrategy = .formatted(formatter)
            
            user = try? decoder.decode(User.self, from: responseData)
            
            print(user?.email ?? "Nope")
            // Debug messaged
            if let responseDataString = String(data: responseData, encoding: .utf8) {
                print("Response Data: \(responseDataString)")
            }
        } catch {
            print("DEBUG: Error \(error)")
        }
    }
    
    func deleteUser() async {
        do {
            var id = 0
            if let user = user {
                id = user.id
            }
            
            guard let url = URL(string: "http://localhost:8080/customer/\(id)") else { return }
            var request = URLRequest(url: url)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "DELETE"
            
            let (_, _) = try await URLSession.shared.upload(for: request, from: Data())
            
            // UI logout
            user = nil
        } catch {
            print("DEBUG: Error sending delete request.")
        }
    }
    
    func editUser(withForename forename: String, withSurname surname: String, withEmail email: String) async {
        do {
            var id = 0
            if let user = user {
                id = user.id
            }
            
            guard let url = URL(string: "http://localhost:8080/customer/\(id)") else { return }
            var request = URLRequest(url: url)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "PUT"
            
            user = User(forename: forename, surname: surname,email: email, rentedMovies: [])
            let data = try JSONEncoder().encode(user)
            
            let (responseData, response) = try await URLSession.shared.upload(for: request, from: data)
            
            // Debug messaged
            if let httpResponse = response as? HTTPURLResponse {
                print(httpResponse.statusCode)
            }
            if let responseDataString = String(data: responseData, encoding: .utf8) {
                print("Response Data: \(responseDataString)")
            }
        } catch {
            print("DEBUG: Error sending edit user request.")
        }
    }
}
