package dev.galiev.countryserviceapp.ui.screens.item


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import dev.galiev.countryserviceapp.service.getCountryData
import dev.galiev.countryserviceapp.service.model.CountryData
import dev.galiev.countryserviceapp.ui.theme.LightlyGray

@Composable
fun CountryInfo(name: String, showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        val countryInfo = remember {
            mutableStateOf(CountryData(
                "No info",
                "null",
                "null",
                0)
            )
        }
        LaunchedEffect(key1 = "") {
            countryInfo.value = getCountryData(name)
        }
        Dialog (
            onDismissRequest = onDismiss,
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightlyGray
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        modifier = Modifier.height(190.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = countryInfo.value.flag_file_url,
                            contentDescription = "Country Flag",
                            imageLoader = ImageLoader.Builder(LocalContext.current)
                                .components {
                                    add(SvgDecoder.Factory())
                                }
                                .build(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Log.d("Logger", "Country flag: ${countryInfo.value.flag_file_url}")
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Name: ${countryInfo.value.name}"
                        )
                        Text(
                            text = "Capital: ${countryInfo.value.capital}"
                        )
                        Text(
                            text = "Country code: ${countryInfo.value.country_code}"
                        )
                        Text(
                            text = "Population: ${countryInfo.value.population}"
                        )
                    }
                }
            }
        }
    }
}