package com.aunkhtoo.composeonboarding.ext

/**
Created By Aunt Htoo Aung on 26/11/2023.
 **/

fun <T> MutableList<T>.removeWhen(isSatisfied: (T) -> Boolean): Boolean {

  this.forEach {
    if (isSatisfied.invoke(it)) {
      this.remove(it)
      return true
    }
  }

  return false
}