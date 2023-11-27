package com.aunkhtoo.composeonboarding.screen.diet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunkhtoo.composeonboarding.ext.noRippleClickable
import com.aunkhtoo.composeonboarding.ui.theme.InfoColor
import kotlinx.coroutines.launch
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 25/11/2023.
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelCheckbox(
  modifier: Modifier = Modifier,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  label: String,
  labelColor: Color = MaterialTheme.colorScheme.onSurface,
  labelFontSize: TextUnit = 18.sp,
  padding: Dp = 0.dp,
  enabledTooltip: Boolean = false,
  tooltipText: String = ""
) {

  var checkBoxState = checked

  Row(
    modifier = modifier
      .padding(all = padding)
      .noRippleClickable {
        checkBoxState = checkBoxState.not()
        onCheckedChange(checkBoxState)
      },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Checkbox(checked = checkBoxState, onCheckedChange = {
      checkBoxState = it
      onCheckedChange(checkBoxState)
    })

    Text(text = label, color = labelColor, fontSize = labelFontSize)

    if (enabledTooltip) {
      val tooltipState = rememberPlainTooltipState()
      val scope = rememberCoroutineScope()

      PlainTooltipBox(tooltip = { Text(text = tooltipText) }, tooltipState = tooltipState) {
        Icon(
          modifier = Modifier.noRippleClickable {
            if (tooltipState.isVisible) {
              scope.launch {
                tooltipState.dismiss()
              }
            } else {
              scope.launch {
                tooltipState.show()
              }
            }
          },
          imageVector = Icons.Outlined.Info,
          contentDescription = null,
          tint = InfoColor
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPlainTooltipState() = remember {
  PlainTooltipState()
}