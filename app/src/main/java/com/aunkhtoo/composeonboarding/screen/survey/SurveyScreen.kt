package com.aunkhtoo.composeonboarding.screen.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.ext.noRippleClickable
import com.aunkhtoo.composeonboarding.screen.SharedViewModel

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/

@Composable
fun RadioGroup(
  modifier: Modifier = Modifier,
  items: List<String>,
  selected: String,
  setSelected: (selected: String) -> Unit
) {

  Column(modifier = modifier.fillMaxWidth()) {
    items.forEach { item ->

      Row(
        modifier = Modifier.noRippleClickable {
          setSelected(item)
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        RadioButton(selected = selected == item, onClick = { setSelected(item) }, enabled = true)
        Text(text = item, fontSize = 20.sp)
      }
    }
  }
}

@Composable
fun RadioGroupWithTitle(
  modifier: Modifier,
  title: String,
  items: List<String>,
  selected: String,
  setSelected: (selected: String) -> Unit
) {

  Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = buildAnnotatedString {
          withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
            append(title)
          }
          withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
            append(" *")
          }
        },
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
    }

    RadioGroup(
      modifier = Modifier.fillMaxWidth(),
      items = items,
      selected = selected,
      setSelected = setSelected
    )
  }

}

@Composable
fun SurveyScreen(viewModel: SharedViewModel, onClickGetMyVitamin: () -> Unit) {
  Column(modifier = Modifier.padding(bottom = 25.dp, start = 16.dp, top = 16.dp, end = 16.dp)) {

    ConstraintLayout(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
        .verticalScroll(state = rememberScrollState())
    ) {

      val (dailyExposureToSun, smoke, alcoholic) = createRefs()

      RadioGroupWithTitle(
        modifier = Modifier
          .fillMaxWidth()
          .constrainAs(dailyExposureToSun) {
            linkTo(start = parent.start, end = parent.end)
            top.linkTo(parent.top, margin = 20.dp)
            width = Dimension.fillToConstraints
          },
        title = "Is your daily exposure to sun is limited?",
        items = viewModel.dailyExposureItems,
        selected = viewModel.selectedDailyExposure.value,
        setSelected = viewModel::setSelectedDailyExposure
      )

      RadioGroupWithTitle(
        modifier = Modifier
          .fillMaxWidth()
          .constrainAs(smoke) {
            linkTo(start = parent.start, end = parent.end)
            top.linkTo(dailyExposureToSun.bottom, margin = 20.dp)
            width = Dimension.fillToConstraints
          },
        title = "Do you current smoke (tobacco or marijuana)?",
        items = viewModel.smokeItems,
        selected = viewModel.selectedSmoke.value,
        setSelected = viewModel::setSelectedSmoke
      )

      RadioGroupWithTitle(
        modifier = Modifier
          .fillMaxWidth()
          .constrainAs(alcoholic) {
            linkTo(start = parent.start, end = parent.end)
            top.linkTo(smoke.bottom, margin = 20.dp)
            width = Dimension.fillToConstraints
          },
        title = "On average, how many alcoholic beverages do you have in a week?",
        items = viewModel.alcoholicItems,
        selected = viewModel.selectedAlcoholic.value,
        setSelected = viewModel::setSelectedAlcoholic
      )


    }

    Button(
      onClick = onClickGetMyVitamin,
      modifier = Modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
      shape = RoundedCornerShape(size = 10.dp),
      elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
      Text(
        text = "Get my personalized vitamin",
        fontSize = 20.sp,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}