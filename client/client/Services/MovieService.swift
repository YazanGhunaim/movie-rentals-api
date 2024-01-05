//
//  MovieService.swift
//  client
//
//  Created by Yazan Ghunaim on 12/22/23.
//

import Foundation

class MovieService {
    // shared instance so all components access the same instance
    static let shared = MovieService()
    
    @MainActor
    func getMovies() async -> [Movie] {
        guard let url = URL(string: "http://localhost:8080/movie") else { return [] }
        
        do {
            let (data, _) = try await URLSession.shared.data(from: url)
            let decoder = JSONDecoder()
            
            // to account for weird java date format
            let formatter = DateFormatter()
            formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
            decoder.dateDecodingStrategy = .formatted(formatter)
            
            let response = try decoder.decode(MovieResponse.self, from: data)
            return response
        } catch {
            print("DEBUG: Error \(error)")
            return []
        }
    }
    
    // Buisness Logic
    @MainActor
    func rentMovie(forCustomer id: Int, movieID: Int) async {
        guard let url = URL(string: "http://localhost:8080/movie/\(id)/\(movieID)") else { return }
        
        do {
            var request = URLRequest(url: url)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "PUT"
            
            let (responseData, response) = try await URLSession.shared.upload(for: request, from: Data())
            
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
