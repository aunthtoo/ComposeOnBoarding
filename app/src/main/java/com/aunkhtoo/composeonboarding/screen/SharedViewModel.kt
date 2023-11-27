package com.aunkhtoo.composeonboarding.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aunkhtoo.composeonboarding.JsonDataConstants
import com.aunkhtoo.composeonboarding.ext.removeWhen
import com.aunkhtoo.composeonboarding.screen.allergy.AllergyViewItem
import com.aunkhtoo.composeonboarding.screen.diet.DietViewItem
import com.aunkhtoo.composeonboarding.screen.healthconcern.HealthConcernViewItem
import com.aunkhtoo.composeonboarding.screen.healthconcern.component.move
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 22/11/2023.
 **/
class SharedViewModel : ViewModel() {

  sealed class HealthConcernViewCommand {
    object CannotSelectedMoreThanFiveItem : HealthConcernViewCommand()

  }

  val healthConcernViewCommand = MutableSharedFlow<HealthConcernViewCommand>()

  val healthConcernViewItems = mutableStateListOf<HealthConcernViewItem>()

  val selectedHealthConcernViewItems = mutableStateListOf<HealthConcernViewItem>()

  val enableNextButton = mutableStateOf(false)

  fun getHealthConcerns() {
    viewModelScope.launch {

      if (healthConcernViewItems.isEmpty()) {

        val healthConcerns = JsonDataConstants.healthConcerns.map {
          HealthConcernViewItem(id = it.id, name = it.name)
        }
        healthConcernViewItems.addAll(healthConcerns)
      }
    }
  }

  fun setSelectedHealthConcern(index: Int, selected: Boolean, item: HealthConcernViewItem) {
    viewModelScope.launch {


      if (selected && selectedHealthConcernViewItems.size >= 5) {

        healthConcernViewCommand.emit(HealthConcernViewCommand.CannotSelectedMoreThanFiveItem)
        return@launch
      }

      //see https://stackoverflow.com/a/69202858/8750016
      val temp = item.copy()
      temp.isSelected = selected
      healthConcernViewItems[index] = temp

      if (selected) {
        selectedHealthConcernViewItems.add(temp)
      } else {

        selectedHealthConcernViewItems.removeWhen {
          it.id == temp.id
        }
      }

      enableNextButton.value = selectedHealthConcernViewItems.isNotEmpty()

    }
  }

  fun moveSelectedHealthConcernItem(from: Int, to: Int) {
    viewModelScope.launch {
      selectedHealthConcernViewItems.move(from = from, to = to)
    }
  }

  //diet section
  val dietViewItems = mutableStateListOf<DietViewItem>()
  private val selectedDietViewItems = mutableListOf<DietViewItem>()

  var checkedNoneState = mutableStateOf(true)

  fun getDiets() {
    viewModelScope.launch {

      if (dietViewItems.isEmpty()) {

        val diets = JsonDataConstants.diets.map {
          DietViewItem(id = it.id, name = it.name, toolTip = it.toolTip)
        }
        dietViewItems.addAll(diets)
      }
    }
  }

  fun checkedNone(checked: Boolean) {
    viewModelScope.launch {

      checkedNoneState.value = checked

      if (checked) {
        selectedDietViewItems.clear()

        val tempList = dietViewItems.map {
          it.copy(isSelected = false)
        }

        dietViewItems.clear()
        dietViewItems.addAll(tempList)
      }

    }
  }

  fun setSelectedDiet(index: Int, selected: Boolean, item: DietViewItem) {

    viewModelScope.launch {

      val temp = item.copy()
      temp.isSelected = selected
      dietViewItems[index] = temp

      if (selected) {
        selectedDietViewItems.add(temp)
      } else {
        selectedDietViewItems.removeWhen {
          it.id == temp.id
        }
      }

      checkedNoneState.value = selectedDietViewItems.isEmpty()

      Timber.e("aha size of selected diets ${selectedDietViewItems.size}")

    }

  }

  //end of diet section

  //allergy section

  private val allergyViewItems = mutableListOf<AllergyViewItem>()
  val filteredAllergyViewItems = mutableStateListOf<AllergyViewItem>()
  val selectedAllergyViewItems = mutableStateListOf<AllergyViewItem>()
  val allergyText = mutableStateOf("")
  val suggestedAllergyText = mutableStateOf("")

  val allergyToastMessage = MutableSharedFlow<String>()

  fun getAllergies() {
    viewModelScope.launch {

      if (allergyViewItems.isEmpty()) {
        val allergies = JsonDataConstants.allergies.map {
          AllergyViewItem(id = it.id, name = it.name)
        }

        allergyViewItems.addAll(allergies)
      }

    }
  }

  fun enterAllergy(allergyNameValue: String) {
    viewModelScope.launch {

      allergyText.value = allergyNameValue

      if (allergyNameValue.isEmpty()) {
        filteredAllergyViewItems.clear()
        suggestedAllergyText.value = ""
        return@launch
      }

      filteredAllergyViewItems.clear()

      filteredAllergyViewItems.addAll(
        allergyViewItems.filter {
          it.name.contains(allergyNameValue, ignoreCase = true)
        }
      )

      val firstItem = filteredAllergyViewItems.firstOrNull()

      if (firstItem == null) {
        suggestedAllergyText.value = ""
      } else {
        suggestedAllergyText.value = firstItem.name

        if (firstItem.name.equals(allergyNameValue, ignoreCase = true)) {
          addAllergy(firstItem)
        }
      }


    }
  }

  fun addAllergy(item: AllergyViewItem) {
    viewModelScope.launch {
      if (selectedAllergyViewItems.contains(item).not()) {
        selectedAllergyViewItems.add(item)
        filteredAllergyViewItems.clear()
        suggestedAllergyText.value = ""
        allergyText.value = ""
      } else {
        allergyToastMessage.emit("You can\'t add same allergy!")
      }
    }
  }

  fun removeLastAllergy() {
    viewModelScope.launch {
      selectedAllergyViewItems.removeLastOrNull()
      filteredAllergyViewItems.clear()
      suggestedAllergyText.value = ""
    }
  }


  //end of allergy section
}