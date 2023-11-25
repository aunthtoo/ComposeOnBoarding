package com.aunkhtoo.composeonboarding.moshi


import com.aunkhtoo.composeonboarding.model.Diet
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DietJson(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String,
  @Json(name = "tool_tip")
  val toolTip: String
) {
  fun mapToDiet() = Diet(id = id, name = name, toolTip = toolTip)
}