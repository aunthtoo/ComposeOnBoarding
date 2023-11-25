package com.aunkhtoo.composeonboarding

import android.app.Application
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 23/11/2023.
 **/
class OnBoardingApp : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    JsonDataConstants.init(this)

  }
}