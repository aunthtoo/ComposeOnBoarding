package com.aunkhtoo.composeonboarding.screen.allergy

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.ext.noRippleClickable
import com.aunkhtoo.composeonboarding.screen.SharedViewModel
import com.aunkhtoo.composeonboarding.screen.allergy.component.TransparentTextField
import com.aunkhtoo.composeonboarding.ui.theme.TransparentDark

/**
Created By Aunt Htoo Aung on 26/11/2023.
 **/

@Composable
fun AddedAllergyItem(modifier: Modifier = Modifier, name: String, onClickDelete: () -> Unit) {

  Box(
    modifier = modifier
      .clip(shape = RoundedCornerShape(22.dp))
      .background(MaterialTheme.colorScheme.primary)
      .padding(10.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(text = name, color = MaterialTheme.colorScheme.surface, fontSize = 18.sp)

      Icon(
        modifier = Modifier
          .size(20.dp)
          .noRippleClickable {
            onClickDelete()
          },
        imageVector = Icons.Outlined.Close,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.surface
      )
    }

  }

}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AllergyContent(modifier: Modifier = Modifier) {

  Column(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = TransparentDark,
        shape = RoundedCornerShape(size = 4.dp)
      )
  ) {

    FlowRow(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .padding(10.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

      AddedAllergyItem(name = "shit", onClickDelete = {

      })

      AddedAllergyItem(name = "shit", onClickDelete = {

      })


      var textField by remember {
        mutableStateOf("")
      }

      TransparentTextField(
        onValueChange = { textField = it },
        value = textField,
        placeholder = "Enter Allergy"
      )
    }
  }
}

@Composable
fun AllergyScreen(
  viewModel: SharedViewModel,
  onBackPressed: () -> Unit,
  onClickNext: () -> Unit = {}
) {

  val context = LocalContext.current

  LaunchedEffect(context) {

  }

  Column {

    ConstraintLayout(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
        .padding(all = 16.dp)
        .verticalScroll(state = rememberScrollState())
    ) {

      val (tvAllergyTitle, allergyContent) = createRefs()

      Text(
        modifier = Modifier.constrainAs(tvAllergyTitle) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(parent.top, margin = 20.dp)
        },
        text = "Write any specific allergies or sensitivity towards specific things. (Optional)",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      AllergyContent(modifier = Modifier.constrainAs(allergyContent) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(tvAllergyTitle.bottom, margin = 16.dp)
        width = Dimension.fillToConstraints
      })

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
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
      ) {
        Text(text = "Next", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
      }
    }
  }

}