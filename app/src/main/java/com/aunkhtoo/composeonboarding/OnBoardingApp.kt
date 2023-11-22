package com.aunkhtoo.composeonboarding

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
import com.aunkhtoo.composeonboarding.screen.WelcomeScreen
import kotlinx.coroutines.launch

/**
Created By Aunt Htoo Aung on 22/11/2023.
 **/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingApp() {

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
      userScrollEnabled = false
    ) { page ->

      WelcomeScreen(viewModel = viewModel, onGetStartedClick = {
        coroutineScope.launch {
          pagerState.animateScrollToPage(if (page < 4) page + 1 else 0)
        }
      })
    }

    CustomLinearProgressBar(progress = pagerState.currentPage / 4f)

  }

}

@Composable
fun CustomLinearProgressBar(modifier: Modifier = Modifier, progress: Float) {
  Box(
    modifier = modifier
      .height(12.dp)
      .clip(RoundedCornerShape(size = 10.dp))
      .fillMaxWidth(progress)
      .background(color = MaterialTheme.colorScheme.primary)
  )
}