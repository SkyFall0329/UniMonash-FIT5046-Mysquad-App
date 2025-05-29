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
// Firebase Dependencies (BOM + Modules)
implementation(platform(libs.firebase.bom))
implementation(libs.firebase.analytics)
implementation(libs.google.firebase.analytics)
implementation(libs.firebase.auth)
implementation(libs.google.firebase.auth)
implementation(libs.firebase.auth.ktx)
implementation(libs.firebase.firestore)
implementation(libs.firebase.firestore.ktx)
implementation(libs.firebase.firestore.ktx.v2451)
implementation(libs.firebase.firestore.ktx.v24100)
implementation(libs.firebase.crashlytics)

// Google Authentication & Credentials Management
implementation(libs.play.services.auth)
implementation(libs.play.services.auth.v2110)
implementation(libs.androidx.credentials)
implementation(libs.androidx.credentials.play.services.auth)
implementation(libs.googleid)

// Maps & Location Services
implementation(libs.play.services.location)
implementation(libs.play.services.maps)
implementation(libs.maps.compose)
implementation(libs.places)
implementation(libs.places.compose)

// Background Tasks (WorkManager)
implementation(libs.androidx.work.runtime.ktx)

// Network & API
implementation(libs.retrofit)
implementation(libs.converter.gson)
implementation(libs.gson)

//Coroutines & Asynchronous Tasks
implementation(libs.kotlinx.coroutines.play.services)
implementation(libs.kotlinx.coroutines.play.services.v164)

//Jetpack Compose & Material Design
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.ui)
implementation(libs.androidx.ui.graphics)
implementation(libs.androidx.ui.tooling.preview)
implementation(libs.androidx.material3)
implementation(libs.material3)
implementation(libs.androidx.navigation.compose)
implementation(libs.androidx.material.icons.extended)
debugImplementation(libs.androidx.ui.tooling)
debugImplementation(libs.androidx.ui.test.manifest)

//Core AndroidX Libraries
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.activity.compose)
implementation(libs.androidx.runtime.livedata)

//Testing Libraries
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
androidTestImplementation(platform(libs.androidx.compose.bom))
androidTestImplementation(libs.androidx.ui.test.junit4)
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

Letao Wang
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

