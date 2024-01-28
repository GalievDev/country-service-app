package dev.galiev.countryserviceapp.service.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryData(
    val name: String,
    val country_code: String,
    val capital: String,
    val population: Long,
    val flag_file_url: String? = "https://upload.wikimedia.org/wikipedia/commons/a/a0/Empty_flag.svg")
