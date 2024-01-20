package dev.galiev.countryserviceapp.service.model

import kotlinx.serialization.Serializable

@Serializable
data class Countries(val name: String, val country_code: String)