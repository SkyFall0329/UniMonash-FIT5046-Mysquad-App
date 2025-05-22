package com.example.mysquad.ui.screens.mainScreens.AddScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.mysquad.componets.ashley.*
import com.example.mysquad.ui.screens.mainScreens.SquareScreen.DisplayDatePicker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.example.mysquad.componets.util.await
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview
@RequiresApi(64)
@Composable
fun AddScreen(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var coordinate by remember { mutableStateOf<LatLng?>(null) }

    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        val verticalScrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(top = 35.dp)
                .verticalScroll(verticalScrollState),
        ) {

            //标题
            Surface(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = "Create event",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row {
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "select a type of event",
                        states = listOf("Basketball", "Football", "Volleyball", "Badminton", "Table Tennis", "Tennis", "Swimming", "Aerobics")
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    DisplayDatePicker()
                }
            }

            CustomTextField(
                value = title,
                onValueChange = { title = it },
                label = "Please enter the title of the event"
            )

            // 地址输入框
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {

                AddressAutocompleteFieldWithLatLng(
                    initialValue = address,
                    onAddressSelected = { selectedAddress, latLng ->
                        address = selectedAddress
                        coordinate = latLng
                        Log.d("Address", "Selected: $selectedAddress, LatLng: $latLng")
                    }
                )
            }

            Row {
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "start time",
                        states = listOf("10:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "end time",
                        states = listOf("14:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
            }

            CustomTextField(
                value = num,
                onValueChange = { num = it },
                label = "Number of people scheduled for the event"
            )

//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
////                Image(
////                    painter = painterResource(id = R.drawable.googlemap),
////                    contentDescription = "Map preview",
////                    contentScale = ContentScale.Crop,
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(250.dp)
////                        .padding(vertical = 20.dp)
////                )
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp)
//                    .padding(vertical = 20.dp)
//            ) {
//                GoogleMap(
//                    modifier = Modifier.fillMaxSize(),
//                    cameraPositionState = cameraPositionState
//                )
//            }
            coordinate?.let {
                MapGraph(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    latLng = it
                )
            }


            //按钮区域
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Cancel */ },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Button(
                    onClick = { /* Post */ },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Post",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapGraph(modifier: Modifier = Modifier, latLng: LatLng) {
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(latLng) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = latLng),
            title = "Selected Location"
        )
    }
}


@Composable
fun AddressAutocompleteFieldWithLatLng(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    labelText: String = "Enter event address",
    onAddressSelected: (address: String, latLng: LatLng?) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var input by remember { mutableStateOf(initialValue) }
    var suggestions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    val queryFlow = remember { MutableStateFlow("") }

    LaunchedEffect(Unit) {
        queryFlow
            .debounce(300)
            .collectLatest { query ->
                if (query.isBlank()) {
                    suggestions = emptyList()
                    return@collectLatest
                }

                val placesClient = Places.createClient(context)
                val request = FindAutocompletePredictionsRequest.builder()
                    .setQuery(query)
                    .build()

                try {
                    val response = placesClient.findAutocompletePredictions(request).await()
                    suggestions = response.autocompletePredictions
                } catch (e: Exception) {
                    suggestions = emptyList()
                }
            }
    }

    Box(modifier) {
        Column {
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    queryFlow.value = it
                },
                label = { Text(labelText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            if (suggestions.isNotEmpty()) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .padding(horizontal = 4.dp)
                ) {
                    LazyColumn {
                        items(suggestions) { prediction ->
                            Text(
                                text = prediction.getFullText(null).toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val selectedAddress = prediction.getFullText(null).toString()
                                        val placeId = prediction.placeId
                                        input = selectedAddress
                                        suggestions = emptyList()
                                        focusManager.clearFocus()

                                        // Fetch LatLng
                                        val placesClient = Places.createClient(context)
                                        val placeRequest = FetchPlaceRequest.builder(
                                            placeId,
                                            listOf(Place.Field.LAT_LNG)
                                        ).build()

                                        CoroutineScope(Dispatchers.IO).launch {
                                            val latLng = try {
                                                val response = placesClient.fetchPlace(placeRequest).await()
                                                response.place.latLng
                                            } catch (e: Exception) {
                                                null
                                            }

                                            withContext(Dispatchers.Main) {
                                                onAddressSelected(selectedAddress, latLng)
                                            }
                                        }
                                    }
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

