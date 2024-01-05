//
//  TextFieldModifier.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct TextFieldModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.subheadline)
            .padding(12)
            .background(Color(.systemGray6))
            .clipShape(.rect(cornerRadius: 10))
            .padding(.horizontal, 25)
    }
}
