package dev.galiev.countryserviceapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.galiev.countryserviceapp.service.getCountries
import dev.galiev.countryserviceapp.service.model.Countries
import dev.galiev.countryserviceapp.ui.screens.item.CountryItem
import dev.galiev.countryserviceapp.ui.theme.TestTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val countries = remember {
                mutableStateListOf<Countries>()
            }

            LaunchedEffect(key1 = "") {
                countries.clear()
                countries.addAll(getCountries().countries)
            }
            Log.d("Logger", "Responses: ${countries.toList()}")

            TestTheme {
                Column {
                    MainText()

                    LazyColumn (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        countries.forEach {
                            item {
                                CountryItem(item = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainText() {
    Text(
        text = "Country Service",
        fontSize = 30.sp,
        modifier = Modifier.padding(start = 100.dp)
    )
}
