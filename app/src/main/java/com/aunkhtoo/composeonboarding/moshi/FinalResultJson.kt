package com.aunkhtoo.composeonboarding.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
Created By Aunt Htoo Aung on 27/11/2023.
 **/
@JsonClass(generateAdapter = true)
data class FinalResultJson(
  @Json(name = "health_concerns") val healthConcerns: List<HealthConcernJson>,
  @Json(name = "diets") val diets: List<DietJson>,
  @Json(name = "is_daily_exposure") val isDailyExposure: Boolean,
  @Json(name = "is_smoke") val isSmoke: Boolean,
  @Json(name = "alcohol") val alcohol: String,
  @Json(name = "allergies") val allergies: List<AllergyJson>
) {
  fun toJson(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val adapter: JsonAdapter<FinalResultJson> =
      moshi.adapter(FinalResultJson::class.java).indent("    ")

    return adapter.toJson(this)
  }
}
