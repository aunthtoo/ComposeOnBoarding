package com.aunkhtoo.composeonboarding.screen.healthconcern

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aunkhtoo.composeonboarding.JsonDataConstants
import com.aunkhtoo.composeonboarding.screen.healthconcern.component.move
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
Created By Aunt Htoo Aung on 23/11/2023.
 **/
class HealthConcernViewModel : ViewModel() {

  var isStart = true

  sealed class HealthConcernViewCommand {
    object CannotSelectedMoreThanFiveItem : HealthConcernViewCommand()

  }

  val viewCommand = MutableSharedFlow<HealthConcernViewCommand>()

  val healthConcernsLiveData = MutableLiveData<SnapshotStateList<HealthConcernViewItem>>()

  private val healthConcernViewItems = mutableStateListOf<HealthConcernViewItem>()

  val selectedHealthConcernViewItems = mutableStateListOf<HealthConcernViewItem>()

  fun getHealthConcerns() {
    viewModelScope.launch {

      val healthConcerns = JsonDataConstants.healthConcerns.map {
        HealthConcernViewItem(id = it.id, name = it.name)
      }

      healthConcernViewItems.addAll(healthConcerns)

      healthConcernsLiveData.postValue(healthConcernViewItems)

    }
  }

  fun setSelectedHealthConcern(index: Int, selected: Boolean, item: HealthConcernViewItem) {
    viewModelScope.launch {


      if (selected && selectedHealthConcernViewItems.size >= 5) {

        viewCommand.emit(HealthConcernViewCommand.CannotSelectedMoreThanFiveItem)
        return@launch
      }

      //see https://stackoverflow.com/a/69202858/8750016
      val temp = healthConcernViewItems[index].copy()
      temp.isSelected = selected
      healthConcernViewItems[index] = temp

      if (selected) {
        selectedHealthConcernViewItems.add(temp)
      } else {

        var removeAt: HealthConcernViewItem? = null
        selectedHealthConcernViewItems.forEach {
          if (it.id == temp.id) {
            removeAt = it
            return@forEach
          }
        }

        selectedHealthConcernViewItems.remove(removeAt)
      }

      healthConcernsLiveData.postValue(healthConcernViewItems)
    }
  }

  fun clearHealthConcern() {
    viewModelScope.launch {
      healthConcernViewItems.clear()
      selectedHealthConcernViewItems.clear()
    }
  }

  fun moveSelectedHealthConcernItem(from: Int, to: Int) {
    viewModelScope.launch {
      selectedHealthConcernViewItems.move(from = from, to = to)
    }
  }
}