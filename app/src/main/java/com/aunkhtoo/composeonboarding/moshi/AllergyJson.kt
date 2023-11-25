package com.aunkhtoo.composeonboarding.moshi


import com.aunkhtoo.composeonboarding.model.Allergy
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllergyJson(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String
) {
  fun mapToAllergy() = Allergy(id = id, name = name)
}