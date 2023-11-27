package com.aunkhtoo.composeonboarding.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aunkhtoo.composeonboarding.screen.allergy.AllergyScreen
import com.aunkhtoo.composeonboarding.screen.diet.DietScreen
import com.aunkhtoo.composeonboarding.screen.healthconcern.HealthConcernScreen
import com.aunkhtoo.composeonboarding.screen.survey.SurveyScreen
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

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = {
      5
    })

    BackHandler {
      when (pagerState.currentPage) {
        0 -> {
          (context as? Activity)?.finish()
        }

        1 -> {
          coroutineScope.launch {
            pagerState.animateScrollToPage(0)
          }
        }

        2 -> {
          coroutineScope.launch {
            pagerState.animateScrollToPage(1)
          }
        }

        3 -> {
          coroutineScope.launch {
            pagerState.animateScrollToPage(2)
          }

        }

        4 -> {
          coroutineScope.launch {
            pagerState.animateScrollToPage(3)
          }

        }
      }
    }

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
          HealthConcernScreen(viewModel = viewModel, onBackPressed = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(0)
            }
          }, onClickNext = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(2)
            }
          })
        }

        2 -> {
          DietScreen(viewModel = viewModel, onBackPressed = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(1)
            }
          }, onClickNext = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(3)
            }
          })
        }

        3 -> {
          AllergyScreen(viewModel = viewModel, onBackPressed = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(2)
            }
          }, onClickNext = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(4)
            }

          })
        }

        4 -> {
          SurveyScreen(viewModel = viewModel) {
            //get my vitamin click
            viewModel.showFinalResult()
          }
        }
      }

    }


    CustomLinearProgressBar(progress = { pagerState.currentPage / 4f })

    if (viewModel.showResultDialog.value)
      VitaminResultDialog(jsonString = viewModel.finalResultJson, setShowDialog = {
        viewModel.showResultDialog.value = it
      })

  }

}

@Composable
fun CustomLinearProgressBar(modifier: Modifier = Modifier, progress: () -> Float) {
  Box(
    modifier = modifier
      .height(14.dp)
      .clip(
        if (progress() < 1f)
          RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 10.dp
          )
        else
          RoundedCornerShape(
            size = 0.dp
          )
      )
      .fillMaxWidth(progress())
      .background(color = MaterialTheme.colorScheme.primary)
  )
}