//
//  clientApp.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

@main
struct clientApp: App {
    @StateObject var authService = AuthService()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(authService)
        }
    }
}
