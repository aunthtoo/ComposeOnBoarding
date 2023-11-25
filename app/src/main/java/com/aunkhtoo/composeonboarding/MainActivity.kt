package com.aunkhtoo.composeonboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aunkhtoo.composeonboarding.screen.OnBoardingMain
import com.aunkhtoo.composeonboarding.ui.theme.ComposeOnBoardingTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeOnBoardingTheme {
        OnBoardingMain()
      }
    }
  }
}