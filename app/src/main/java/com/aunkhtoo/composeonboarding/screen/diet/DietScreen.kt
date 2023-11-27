package com.aunkhtoo.composeonboarding.screen.diet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.screen.SharedViewModel
import com.aunkhtoo.composeonboarding.screen.diet.component.LabelCheckbox

/**
Created By Aunt Htoo Aung on 25/11/2023.
 **/
@Composable
fun DietScreen(
  viewModel: SharedViewModel,
  onBackPressed: () -> Unit,
  onClickNext: () -> Unit = {}
) {

  val context = LocalContext.current

  LaunchedEffect(context) {
    viewModel.getDiets()
  }


  Column {

    ConstraintLayout(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
        .padding(all = 16.dp)
        .verticalScroll(state = rememberScrollState())
    ) {

      val (sectionTitle, dietList) = createRefs()

      Row(modifier = Modifier
        .fillMaxWidth()
        .constrainAs(sectionTitle) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(parent.top, margin = 20.dp)
        }) {
        Text(
          text = "Select the diets you follow.",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "*",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.error,
          modifier = Modifier.weight(1f)
        )
      }

      LazyColumn(modifier = Modifier
        .constrainAs(dietList) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(sectionTitle.bottom, margin = 16.dp)
          width = Dimension.fillToConstraints

        }
        .heightIn(max = 1000.dp)) {

        item {
          LabelCheckbox(
            checked = viewModel.checkedNoneState.value,
            onCheckedChange = viewModel::checkedNone,
            label = "None"
          )
        }

        itemsIndexed(viewModel.dietViewItems, key = { _, item -> item.id }) { index, item ->

          LabelCheckbox(checked = item.isSelected, onCheckedChange = { checked ->

            viewModel.setSelectedDiet(index = index, selected = checked, item = item)

          }, label = item.name, enabledTooltip = true, tooltipText = item.toolTip)
        }
      }

    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 25.dp)
    ) {

      TextButton(
        onClick = onBackPressed,
        shape = RoundedCornerShape(size = 10.dp)
      ) {
        Text(
          text = "Back",
          fontSize = 20.sp,
          modifier = Modifier.padding(8.dp),
          color = MaterialTheme.colorScheme.tertiary
        )
      }

      Button(
        onClick = onClickNext,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(size = 10.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
      ) {
        Text(text = "Next", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
      }
    }
  }
}