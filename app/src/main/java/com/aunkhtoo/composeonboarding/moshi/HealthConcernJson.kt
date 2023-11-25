package com.aunkhtoo.composeonboarding.moshi


import com.aunkhtoo.composeonboarding.model.HealthConcern
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HealthConcernJson(
  @Json(name = "id")
  val id: Int,
  @Json(name = "name")
  val name: String
) {
  fun mapToHealthConcern() = HealthConcern(id = id, name = name)
}