import SwiftUI
import shared

@main
struct iOSApp: App {

	var body: some Scene {
		WindowGroup {
		    ZStack {
                
                Color(red: 198 / 255, green: 243 / 255, blue: 254 / 255).ignoresSafeArea(.all) // status bar color
			    ContentView()
			}.preferredColorScheme(.light)
		}
	}
}
