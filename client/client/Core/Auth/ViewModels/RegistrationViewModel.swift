//
//  RegistrationViewModel.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import Foundation

class RegistrationViewModel : ObservableObject {
    @Published var email = ""
    @Published var forename = ""
    @Published var surname = ""
}
