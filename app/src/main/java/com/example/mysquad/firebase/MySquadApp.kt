package com.example.mysquad.firebase

import android.app.Application
import com.google.firebase.FirebaseApp

class MySquadApp:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}