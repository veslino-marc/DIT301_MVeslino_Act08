# Simple Location Tracker App

A mobile application that displays the user's live GPS location on a map using the device's location services.

## 📱 App Description

This Android application provides real-time GPS location tracking with an interactive map interface. The app displays the user's current location on Google Maps with a marker and shows precise coordinates including latitude, longitude, and accuracy. The location updates automatically every 3 seconds to reflect any movement, making it perfect for demonstrating GPS tracking functionality.

**Key Features:**
- Real-time GPS location tracking
- Interactive Google Maps display
- Location marker with coordinates
- Automatic location updates
- Clean, single-screen interface
- Location accuracy indicator

## 🔐 Permissions Used

The app requires the following permissions:

### 1. **ACCESS_FINE_LOCATION**
- **Purpose:** Allows the app to access precise location from GPS
- **Usage:** Required to get accurate latitude and longitude coordinates
- **When requested:** On first app launch

### 2. **ACCESS_COARSE_LOCATION**
- **Purpose:** Allows the app to access approximate location from network sources
- **Usage:** Provides fallback location data when GPS is unavailable
- **When requested:** On first app launch

### 3. **INTERNET**
- **Purpose:** Required for loading Google Maps tiles
- **Usage:** Downloads map data and displays the interactive map
- **When requested:** Automatically granted (no user prompt needed)

## 📍 How GPS Location is Obtained

The app uses Google Play Services' **FusedLocationProviderClient** to obtain GPS location data. Here's how it works:

### 1. **Permission Request**
When the app first launches, it checks if location permissions are granted. If not, it displays a system dialog requesting permission from the user.

### 2. **Location Provider Initialization**
Once permission is granted, the app initializes the `FusedLocationProviderClient`, which is Google's recommended API for location services. This provider intelligently combines data from:
- GPS satellites (most accurate)
- Wi-Fi networks
- Cell towers
- Device sensors

### 3. **Location Request Configuration**
The app creates a `LocationRequest` with the following settings:
- **Priority:** `PRIORITY_HIGH_ACCURACY` - Uses GPS for maximum precision
- **Update Interval:** 3000ms (3 seconds) - Requests new location every 3 seconds
- **Minimum Update Interval:** 1000ms (1 second) - Can receive updates as fast as every second

### 4. **Location Updates**
The app registers a `LocationCallback` that receives location updates:
```kotlin
locationCallback = object : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult) {
        // Process new location data
        // Update map marker
        // Display coordinates
    }
}
```

### 5. **Data Processing**
When a location update is received, the app:
- Extracts latitude, longitude, and accuracy from the `Location` object
- Updates the text display with formatted coordinates
- Moves the map marker to the new position
- Animates the camera to center on the current location

### 6. **Continuous Tracking**
The location updates continue automatically while the app is in the foreground, providing real-time tracking of device movement.

## 🛠️ Technical Implementation

- **Language:** Kotlin
- **Minimum SDK:** API 24 (Android 7.0)
- **Target SDK:** API 36
- **Location API:** Google Play Services Location API
- **Map API:** Google Maps SDK for Android

## 📋 Requirements Met

✅ Location permission request and handling  
✅ Map displayed on screen  
✅ Marker showing current user location  
✅ Location updates when the device moves  
✅ Basic UI (single screen)

## 🚀 How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Add your Google Maps API key to `local.properties`:
   ```
   MAPS_API_KEY=your_api_key_here
   ```
4. Build and run the app on a physical device or emulator
5. Grant location permission when prompted
6. View your location on the map!

## 📸 Screenshots

- **Permission Request:** Shows the location permission dialog
- **Map with Location:** Displays the map with current location marker
- **Location Updates:** Demonstrates real-time location tracking

## 👨‍💻 Author

Marc Veslino - DIT301 Activity 8

## 📄 License

This project is created for educational purposes.
