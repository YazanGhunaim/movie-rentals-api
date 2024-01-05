//
//  DeleteProfileButtonModifier.swift
//  client
//
//  Created by Yazan Ghunaim on 12/20/23.
//

import SwiftUI

struct DeleteProfileButtonModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .foregroundStyle(.red.opacity(0.9))
            .font(.headline)
            .frame(width: 150, height: 50)
            .background(.black.opacity(0.9))
            .clipShape(.rect(cornerRadius: 5))
    }
}
