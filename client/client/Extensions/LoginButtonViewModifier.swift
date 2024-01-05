//
//  LoginButtonViewModifier.swift
//  client
//
//  Created by Yazan Ghunaim on 12/19/23.
//

import SwiftUI

struct LoginButtonViewModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.subheadline)
            .fontWeight(.semibold)
            .foregroundStyle(.white)
            .frame(width: 350, height: 50)
            .background(.black)
            .clipShape(.rect(cornerRadius: 10))
    }
}
