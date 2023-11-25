package com.aunkhtoo.composeonboarding

import android.content.Context
import com.aunkhtoo.composeonboarding.model.Allergy
import com.aunkhtoo.composeonboarding.model.Diet
import com.aunkhtoo.composeonboarding.model.HealthConcern
import com.aunkhtoo.composeonboarding.moshi.AllergyJson
import com.aunkhtoo.composeonboarding.moshi.BaseJson
import com.aunkhtoo.composeonboarding.moshi.DietJson
import com.aunkhtoo.composeonboarding.moshi.HealthConcernJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

/**
Created By Aunt Htoo Aung on 23/11/2023.
 **/
object JsonDataConstants {

  val healthConcerns: MutableList<HealthConcern> = mutableListOf()

  val diets: MutableList<Diet> = mutableListOf()

  val allergies: MutableList<Allergy> = mutableListOf()

  fun init(context: Context) {

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    //health concern
    val healthConcernType = Types.newParameterizedType(
      BaseJson::class.java,
      HealthConcernJson::class.java
    )
    val healthConcernAdapter: JsonAdapter<BaseJson<HealthConcernJson>> =
      moshi.adapter(healthConcernType)
    val healthConcernJson =
      context.assets.open("healthconcerns.json").bufferedReader().use { it.readText() }
    healthConcernAdapter.fromJson(healthConcernJson)?.let {
      healthConcerns.addAll(it.jsonData.map(HealthConcernJson::mapToHealthConcern))
    }

    //diet
    val dietType = Types.newParameterizedType(BaseJson::class.java, DietJson::class.java)
    val dietAdapter: JsonAdapter<BaseJson<DietJson>> = moshi.adapter(dietType)
    val dietJson = context.assets.open("diets.json").bufferedReader().use { it.readText() }
    dietAdapter.fromJson(dietJson)?.let {
      diets.addAll(it.jsonData.map(DietJson::mapToDiet))
    }

    //allergy
    val allergyType = Types.newParameterizedType(BaseJson::class.java, AllergyJson::class.java)
    val allergyAdapter: JsonAdapter<BaseJson<AllergyJson>> = moshi.adapter(allergyType)
    val allergyJson = context.assets.open("allergies.json").bufferedReader().use { it.readText() }
    allergyAdapter.fromJson(allergyJson)?.let {
      allergies.addAll(it.jsonData.map(AllergyJson::mapToAllergy))
    }

  }

}