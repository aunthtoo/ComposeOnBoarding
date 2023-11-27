package com.aunkhtoo.composeonboarding.screen.healthconcern

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.R
import com.aunkhtoo.composeonboarding.ext.showShortToast
import com.aunkhtoo.composeonboarding.screen.SharedViewModel
import com.aunkhtoo.composeonboarding.screen.healthconcern.component.ReorderableList
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 22/11/2023.
 **/

@Composable
fun SelectableHealthConcernItem(modifier: Modifier = Modifier, item: HealthConcernViewItem) {

  if (item.isSelected) {

    Box(
      modifier = modifier
        .clip(shape = RoundedCornerShape(22.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(10.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(text = item.name, color = MaterialTheme.colorScheme.surface, fontSize = 18.sp)
    }

  } else {
    Box(
      modifier = modifier
        .border(
          width = 1.dp,
          color = MaterialTheme.colorScheme.primary,
          shape = RoundedCornerShape(22.dp)
        )
        .padding(10.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(text = item.name, color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
    }
  }

}

@Composable
fun DraggableHealthConcern(modifier: Modifier = Modifier, item: HealthConcernViewItem) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
      .fillMaxWidth()
      .clickable(true) {}
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(size = 12.dp)
      )
      .background(Color.White)
      .padding(10.dp)
  ) {
    Box(
      modifier = modifier
        .clip(shape = RoundedCornerShape(22.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(10.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(text = item.name, color = MaterialTheme.colorScheme.surface, fontSize = 18.sp)
    }

    Icon(
      painter = painterResource(id = R.drawable.baseline_drag_indicator_24),
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HealthConcernScreen(
  viewModel: SharedViewModel,
  onBackPressed: () -> Unit,
  onClickNext: () -> Unit = {}
) {

  val context = LocalContext.current
  val haptic = LocalHapticFeedback.current

  LaunchedEffect(context) {

    viewModel.getHealthConcerns()

    viewModel.viewCommand.collect {
      when (it) {
        SharedViewModel.HealthConcernViewCommand.CannotSelectedMoreThanFiveItem -> {
          context.showShortToast("You can\'t select more than five")
        }
      }
    }
  }

  Column {

    ConstraintLayout(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
        .padding(all = 16.dp)
        .verticalScroll(state = rememberScrollState())
    ) {

      val (sectionTitle, selectableHealthConcern, tvPrioritize, prioritizeHealthConcern) = createRefs()

      Row(modifier = Modifier
        .fillMaxWidth()
        .constrainAs(sectionTitle) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(parent.top, margin = 20.dp)
        }) {
        Text(
          text = "Select the top health concerns.\n(upto 5)",
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

      FlowRow(
        modifier = Modifier.constrainAs(selectableHealthConcern) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(sectionTitle.bottom, margin = 16.dp)
          width = Dimension.fillToConstraints
        },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {

        viewModel.healthConcernViewItems.forEachIndexed { index, healthConcernViewItem ->
          SelectableHealthConcernItem(
            modifier = Modifier.selectable(
              selected = healthConcernViewItem.isSelected,
              onClick = {
                viewModel.setSelectedHealthConcern(
                  index = index,
                  selected = healthConcernViewItem.isSelected.not(),
                  item = healthConcernViewItem
                )
              }), item = healthConcernViewItem
          )
        }

      }

      Text(
        text = "Prioritize",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.constrainAs(tvPrioritize) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(selectableHealthConcern.bottom, margin = 20.dp)
          width = Dimension.fillToConstraints
          height = Dimension.wrapContent
        }
      )


      ReorderableList(
        modifier = Modifier
          .constrainAs(prioritizeHealthConcern) {
            linkTo(start = parent.start, end = parent.end)
            top.linkTo(tvPrioritize.bottom, margin = 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
          }
          .heightIn(max = 1000.dp),
        items = viewModel.selectedHealthConcernViewItems,
        onMove = viewModel::moveSelectedHealthConcernItem,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        onDragStart = {
          haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
      ) { _, item ->
        DraggableHealthConcern(item = item)
      }

    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 25.dp)
    ) {

      TextButton(
        onClick = { onBackPressed() },
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
        onClick = { onClickNext() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(size = 10.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        enabled = viewModel.enableNextButton.value
      ) {
        Text(text = "Next", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
      }
    }
  }
}