package com.aunkhtoo.composeonboarding.screen.allergy.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aunkhtoo.composeonboarding.ui.theme.TransparentDark
import timber.log.Timber

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
  fontSize: TextUnit = 18.sp
) {

  val focusRequester = remember { FocusRequester() }
  val localSoftKeyboard = LocalSoftwareKeyboardController.current

  DisposableEffect(key1 = Unit) {
    focusRequester.requestFocus()

    onDispose {
      Timber.e("aha on dispose")
      localSoftKeyboard?.hide()
    }
  }

  val interactionSource = remember { MutableInteractionSource() }
  val enabled = true
  val singleLine = true

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    interactionSource = interactionSource,
    enabled = enabled,
    singleLine = singleLine,
    modifier = modifier.focusRequester(focusRequester)
  ) {
    TextFieldDefaults.DecorationBox(
      value = value,
      innerTextField = it,
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