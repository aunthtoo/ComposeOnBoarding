package com.aunkhtoo.composeonboarding.ext

import android.content.Context
import android.widget.Toast

/**
Created By Aunt Htoo Aung on 23/11/2023.
 **/

fun Context.showShortToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}