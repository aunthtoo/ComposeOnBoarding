package com.aunkhtoo.composeonboarding.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/

@Composable
fun VitaminResultDialog(jsonString: String, setShowDialog: (Boolean) -> Unit) {

  Dialog(onDismissRequest = { setShowDialog(false) }) {

    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
      shape = RoundedCornerShape(size = 15.dp),
      color = Color.White
    ) {
      Text(modifier = Modifier.padding(16.dp), text = jsonString, fontSize = 18.sp)
    }

  }
}