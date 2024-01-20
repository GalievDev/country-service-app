package dev.galiev.countryserviceapp.service.model

import kotlinx.serialization.Serializable

@Serializable
data class CountriesList(
    val countries: List<Countries>
)