package com.aunkhtoo.composeonboarding.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
Created By Aunt Htoo Aung on 24/11/2023.
 **/

/**
 * Transforms a [LiveData] into [MutableLiveData]
 *
 * @param T type
 * @return [MutableLiveData] emitting the same values
 */
fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
  val mediatorLiveData = MediatorLiveData<T>()
  mediatorLiveData.addSource(this) {
    mediatorLiveData.value = it
  }
  return mediatorLiveData
}