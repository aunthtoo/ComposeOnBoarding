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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aunkhtoo.composeonboarding.ext.noRippleClickable
import com.aunkhtoo.composeonboarding.ext.showShortToast
import com.aunkhtoo.composeonboarding.screen.SharedViewModel
import com.aunkhtoo.composeonboarding.screen.allergy.component.TransparentTextField
import com.aunkhtoo.composeonboarding.ui.theme.TransparentDark
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 26/11/2023.
 **/

@Composable
fun AddedAllergyItem(modifier: Modifier = Modifier, name: String) {

  Box(
    modifier = modifier
      .clip(shape = RoundedCornerShape(22.dp))
      .background(MaterialTheme.colorScheme.primary)
      .padding(10.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(text = name, color = MaterialTheme.colorScheme.surface, fontSize = 18.sp)

  }

}

@OptIn(
  ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
  ExperimentalComposeUiApi::class
)
@Composable
fun AllergyContent(
  modifier: Modifier = Modifier,
  selectedAllergies: List<AllergyViewItem>,
  filteredAllergies: List<AllergyViewItem>,
  allergyTextValue: String,
  suggestedAllergy: String,
  enterAllergyText: (String) -> Unit,
  addAllergy: (AllergyViewItem) -> Unit,
  removeLastAllergy: () -> Unit
) {

  val context = LocalContext.current

  val focusRequester = remember { FocusRequester() }
  val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = TransparentDark,
        shape = RoundedCornerShape(size = 4.dp)
      )
      .noRippleClickable {
        focusRequester.requestFocus()
        localSoftwareKeyboardController?.show()
      }
  ) {

    FlowRow(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .padding(10.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

      selectedAllergies.forEach { item ->
        AddedAllergyItem(name = item.name)
      }

      TransparentTextField(
        modifier = Modifier.focusRequester(focusRequester),
        onValueChange = {
          enterAllergyText(it)
        },
        value = allergyTextValue,
        placeholder = "Enter Allergy",
        suffixValue = suggestedAllergy,
        onBackspaceInEmptyValue = {
          Timber.e("aha on backspace in empty")
          removeLastAllergy.invoke()
        }
      )
    }

    if (filteredAllergies.isNotEmpty())
      Divider(color = TransparentDark)

    LazyColumn(modifier = Modifier.heightIn(max = 1000.dp)) {
      items(filteredAllergies) { item ->
        Text(modifier = Modifier
          .fillMaxWidth()
          .noRippleClickable {
            addAllergy.invoke(item)
          }
          .padding(10.dp), text = item.name, fontSize = 18.sp)
      }
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
    viewModel.getAllergies()

    viewModel.allergyToastMessage.collect { message ->
      context.showShortToast(message)
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

      AllergyContent(
        modifier = Modifier.constrainAs(allergyContent) {
          linkTo(start = parent.start, end = parent.end)
          top.linkTo(tvAllergyTitle.bottom, margin = 16.dp)
          width = Dimension.fillToConstraints
        },
        selectedAllergies = viewModel.selectedAllergyViewItems.toList(),
        filteredAllergies = viewModel.filteredAllergyViewItems.toList(),
        allergyTextValue = viewModel.allergyText.value,
        suggestedAllergy = viewModel.suggestedAllergyText.value,
        enterAllergyText = viewModel::enterAllergy,
        addAllergy = viewModel::addAllergy,
        removeLastAllergy = viewModel::removeLastAllergy
      )

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