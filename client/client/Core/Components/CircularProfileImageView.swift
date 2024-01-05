//
//  CircularProfileImageView.swift
//  client
//
//  Created by Yazan Ghunaim on 12/20/23.
//

import SwiftUI

struct CircularProfileImageView: View {
    var body: some View {
        Image("profilepic")
                        .resizable()
                        .scaledToFill()
                        .frame(width: 40, height: 40)
                        .foregroundColor(Color(.systemGray5))
                        .clipShape(Circle())
    }
}

#Preview {
    CircularProfileImageView()
}
