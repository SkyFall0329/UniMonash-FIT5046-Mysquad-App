# **PlaySquad**

A Social Event Organization App for Monash University Students**

📱 **Overview**

PlaySquad is a Jetpack Compose-based Android application designed specifically for Monash University students to discover, create, and participate in campus activities and events. The app provides a seamless platform for students to connect through shared interests and organize group activities.

✨ **Key Features**

**Authentication & User Management**

* **User Registration & Login**
* - Create account with email and password
* **Password Recovery**
* - Forgot password functionality
* **Profile Management**
* - Edit user profile (except userId and email)
* **Secure Logout**
* - Safe session termination
  
 🎨 **User Interface**
* **Theme Toggle**
* - Switch between light and dark modes
* **Weather Integration**
* - View current weather in dashboard
* **Intuitive Navigation**
* - User-friendly interface design

🎯 **Event Management**

* **Event Creation**
* - Create detailed event posts with type, datetime, location, and participant limits
* **Event Discovery**
* - Browse upcoming events in Activity Square
* **Search & Filter**
* - Find events by type and date
* **Event Details**
* - Comprehensive event information display
* **Application System**
* - Request to join events created by other users

### 👥 Social Features

* **User Profiles**
* - View other users' profiles by clicking usernames
* **Applicant Management**
* - Host can approve or reject event applications
* **Activity Tracking**
* - View events you host or join in the next 7 days

### 🗓️ Smart Integrations

* **Google Calendar API**
* - Date selection for event creation and profile editing
* **Google Maps API**
* - Location search and selection for events
* **Location Services**
* - Smart address autocomplete and mapping

## 🛠️ Technical Stack

### Core Technologies

* ​**Language**​: Kotlin
* ​**UI Framework**​: Jetpack Compose
* ​**Architecture**​: MVVM Pattern
* ​**Navigation**​: Navigation Compose

### Key Dependencies

```gradle
// UI & Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.material3:material3")
implementation("androidx.navigation:navigation-compose")

// Firebase Services
implementation(platform("com.google.firebase:firebase-bom"))
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
implementation("com.google.firebase:firebase-analytics")

// Google Services
implementation("com.google.android.gms:play-services-auth")
implementation("com.google.android.gms:play-services-maps")
implementation("com.google.android.gms:play-services-location")

// Local Database
implementation("androidx.room:room-runtime")
implementation("androidx.room:room-ktx")

// Network & API
implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-gson")
```

### System Requirements

* ​**Minimum SDK**​: API 24 (Android 7.0)
* ​**Target SDK**​: API 35
* ​**Compile SDK**​: API 35
* ​**Java Version**​: 11

## 📋 Feature Implementation Status

| Functionality
| --------------------------------- 
| **Authentication System** 
| User Login/Registration         
| Password Recovery               
| **Event Management**      
| Event Creation & Details        
| Event Search & Discovery       
| Application System              
| **Social Features**       
| Profile Management             
| Applicant Management            
| **Smart Integrations**    
| Google Calendar API             
| Google Maps API                 
| Weather Integration             
| **UI/UX Features**        
| Theme Toggle                    
| **Pending Features**      
| View User Profiles             
| Participant Limit Validation
| User Avatar Settings

## 🚀 Getting Started

### Prerequisites

* Android Studio Meerkat | 2024.3.1 Patch 1
* Android SDK API 24+
* Google Services JSON file for Firebase integration

### Installation

1. Clone the repository

```bash
git clone [repository-url]
cd PlaySquad
```

2. Open the project in Android Studio
3. Add your `google-services.json` file to the `app/` directory
4. Sync project with Gradle files
5. Build and run the application

### Configuration

* Ensure you have valid Google API keys for Maps and Calendar services
* Configure Firebase project settings
* Set up proper authentication providers in Firebase Console

## 🎯 Target Audience

​**Primary Users**​: Monash University Students

* Looking to discover campus events and activities
* Want to organize group activities with fellow students
* Seeking to build social connections through shared interests

## 📱 Screenshots
[‸](https://)


## 🔮 Future Enhancements

* Enhanced user profile viewing system
* Automatic participant limit validation
* User avatar upload and management
* Push notifications for event updates
* Advanced filtering and recommendation system

## 👥 Development Team

Letao Zhang
Jianhui Ling
Jianqin Zhu
Xueer Yao

## 📄 License

Educational use only

## 📞 Contact

| **jzhu0106@student.Monash.edu**
| **xyao0024@student.Monash.edu**
| **jlin0080@student.monash.edu**
| **lwan0243@student.monash.edu**

---

​**Note**​: This application is designed specifically for the Monash University community to enhance campus social activities and student engagement.

