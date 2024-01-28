package dev.galiev.countryserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.galiev.countryserviceapp.service.getCountries
import dev.galiev.countryserviceapp.service.model.Countries
import dev.galiev.countryserviceapp.ui.screens.item.CountryItem
import dev.galiev.countryserviceapp.ui.theme.TestTheme
import dev.galiev.countryserviceapp.ui.theme.White
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val countries = remember {
                mutableStateListOf<Countries>()
            }

            var queryString by remember {
                mutableStateOf("")
            }

            var isActive by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(key1 = "") {
                countries.clear()
                countries.addAll(getCountries().countries)
            }

            TestTheme {
                Column {
                    MainText()
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        colors = SearchBarDefaults.colors(
                            containerColor = White
                        ),
                        query = queryString,
                        onQueryChange = {
                            queryString = it
                        },
                        onSearch = {
                            isActive = false
                        },
                        active = true,
                        onActiveChange = {
                            isActive = it
                        },
                        placeholder = {
                            Text(text = "Search")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    ) {
                        LazyColumn (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            countries.forEach {
                                item {
                                    if (it.name.equals(queryString, ignoreCase = true) || it.country_code.equals(queryString, ignoreCase = true)) {
                                        CountryItem(item = it)
                                    } else if (queryString.isEmpty()) {
                                        CountryItem(item = it)
                                    }
                                }
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
        modifier = Modifier.padding(start = 100.dp),
    )
}
