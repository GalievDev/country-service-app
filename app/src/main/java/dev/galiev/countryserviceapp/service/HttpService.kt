package dev.galiev.countryserviceapp.service

import dev.galiev.countryserviceapp.service.model.CountriesList
import dev.galiev.countryserviceapp.service.model.CountryData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}
const val url = "http://10.0.2.2:8080/countries/"

suspend fun getCountries(): CountriesList {
    return client.get(url).body<CountriesList>()
}

suspend fun getCountryData(name: String): CountryData {
    return client.get("$url$name").body<CountryData>()
}