package com.example.aairastation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// This class is needed for Dagger Hilt library to work
@HiltAndroidApp
class MainApp : Application()