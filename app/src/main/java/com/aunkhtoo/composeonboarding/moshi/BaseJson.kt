package com.aunkhtoo.composeonboarding.moshi


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseJson<T>(
  @Json(name = "data")
  val jsonData: List<T>
)