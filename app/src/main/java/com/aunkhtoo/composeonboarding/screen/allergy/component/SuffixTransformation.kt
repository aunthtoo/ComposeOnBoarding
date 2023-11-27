package com.aunkhtoo.composeonboarding.screen.allergy.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.aunkhtoo.composeonboarding.ui.theme.TransparentDark

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/
class SuffixTransformation(val suffix: String) : VisualTransformation {
  override fun filter(text: AnnotatedString): TransformedText {

    val result = text + buildAnnotatedString {
      withStyle(SpanStyle(TransparentDark)) {
        append(suffix)
      }
    }

    //AnnotatedString(suffix) 

    val textWithSuffixMapping = object : OffsetMapping {
      override fun originalToTransformed(offset: Int): Int {
        return offset
      }

      override fun transformedToOriginal(offset: Int): Int {
        if (text.isEmpty()) return 0
        if (offset >= text.length) return text.length

        return offset
      }
    }

    return TransformedText(result, textWithSuffixMapping)
  }
}