package com.aunkhtoo.composeonboarding.screen.allergy.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/
class SuffixTransformation(val suffix: String) : VisualTransformation {
  override fun filter(text: AnnotatedString): TransformedText {

    val result = text + AnnotatedString(suffix)

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