package com.thesua.notex

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteXApplication:Application()
//    override fun onCreate() {
//        super.onCreate()
//        Firebase.initialize(this)
//    }
