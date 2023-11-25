package com.aunkhtoo.composeonboarding.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunkhtoo.composeonboarding.SharedViewModel
import com.aunkhtoo.composeonboarding.screen.healthconcern.HealthConcernScreen
import com.aunkhtoo.composeonboarding.screen.welcome.WelcomeScreen
import kotlinx.coroutines.launch

/**
Created By Aunt Htoo Aung on 22/11/2023.
 **/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingMain() {

  Column(modifier = Modifier.fillMaxSize()) {

    val viewModel: SharedViewModel = viewModel()

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = {
      5
    })

    HorizontalPager(
      modifier = Modifier
        .weight(1f)
        .fillMaxSize(),
      state = pagerState,
      userScrollEnabled = false,
    ) { page ->

      when (page) {
        0 -> {
          WelcomeScreen(viewModel = viewModel, onGetStartedClick = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(1)
            }
          })
        }

        1 -> {
          HealthConcernScreen(onBackPressed = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(0)
            }
          })
        }

        else -> {
          HealthConcernScreen(onBackPressed = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(0)
            }
          })
        }
      }

    }

    CustomLinearProgressBar(progress = pagerState.currentPage / 4f)

  }

}

@Composable
fun CustomLinearProgressBar(modifier: Modifier = Modifier, progress: Float) {
  Box(
    modifier = modifier
      .height(14.dp)
      .clip(RoundedCornerShape(size = 10.dp))
      .fillMaxWidth(progress)
      .background(color = MaterialTheme.colorScheme.primary)
  )
}