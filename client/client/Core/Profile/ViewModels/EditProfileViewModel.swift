//
//  EditProfileViewModel.swift
//  client
//
//  Created by Yazan Ghunaim on 12/22/23.
//

import Foundation

class EditProfileViewModel: ObservableObject {
    @Published var forename = ""
    @Published var surname = ""
    @Published var email = ""
    
    func reset() { 
        forename = ""
        surname = ""
        email = ""
    }
}
