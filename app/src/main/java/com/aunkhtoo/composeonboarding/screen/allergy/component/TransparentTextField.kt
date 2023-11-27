package com.aunkhtoo.composeonboarding.screen.allergy.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunkhtoo.composeonboarding.ui.theme.TransparentDark

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TransparentTextField(
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit,
  value: String,
  suffixValue: String = "",
  placeholder: String,
  padding: Dp = 10.dp,
  fontSize: TextUnit = 18.sp,
  onBackspaceInEmptyValue: () -> Unit
) {

  val interactionSource = remember { MutableInteractionSource() }
  val enabled = true
  val singleLine = true

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    interactionSource = interactionSource,
    enabled = enabled,
    singleLine = singleLine,
    maxLines = 1,
    modifier = modifier
      .width(IntrinsicSize.Max)
      .onKeyEvent {
        if (it.key == Key.Backspace) {
          if (value.isEmpty()) {
            onBackspaceInEmptyValue.invoke()
          }
          true
        } else {
          false
        }
      },
    textStyle = LocalTextStyle.current.copy(fontSize = fontSize)
  ) {
    TextFieldDefaults.DecorationBox(
      value = value,
      innerTextField = {
        Box {
          Text(text = suffixValue, fontSize = fontSize, color = TransparentDark)
          it.invoke()
        }
      },
      placeholder = { Text(text = placeholder, fontSize = fontSize, color = TransparentDark) },
      enabled = enabled,
      singleLine = singleLine,
      visualTransformation = VisualTransformation.None,
      interactionSource = interactionSource,
      contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
        start = padding, end = padding, top = padding, bottom = padding
      ),
      colors = TextFieldDefaults.colors(
        disabledTextColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
      )
    )
  }

}