package com.example.mysquad.ui.screens.mainScreens.AddScreen

import EventRepository
import android.widget.Toast
import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.componets.ashley.DisplayDatePicker
import com.example.mysquad.componets.ashley.Downregulate
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
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
import com.google.firebase.auth.FirebaseAuth
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
import com.google.maps.android.compose.rememberCameraPositionState
import java.time.Instant
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(64)
@Preview
@Composable
fun AddScreen(modifier: Modifier = Modifier) {

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var type = remember { mutableStateOf("") }
    var eventstarttime = remember { mutableStateOf("") }
    var eventendtime = remember { mutableStateOf("") }
    var coordinate by remember { mutableStateOf<LatLng?>(null) }

    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val context = LocalContext.current
    // 手动构造依赖
    val db = remember { AppDatabase.getInstance(context) }
    val eventDao = db.eventDao()
    val remote = EventRemoteDataSource()
    val eventRepository = EventRepository(eventDao, remote)

    val viewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(eventRepository)
    )
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

            CustomTextField(
                value = desc,
                onValueChange = { desc = it },
                label = "please enter the description of the event",
                modifier = Modifier.height(200.dp)
            )

            Row {
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "start time",
                        selectedState = eventstarttime,
                        states = listOf("10:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "end time",
                        selectedState = eventendtime,
                        states = listOf("14:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
            }

            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "please enter the address of the event",
            )
            Spacer(modifier = Modifier.height(20.dp))


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
            MelbourneMap(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp))


            Spacer(modifier = Modifier.height(13.dp))
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
                    onClick = {
                        viewModel.createEvent(EventEntity(
                            eventId = UUID.randomUUID().toString(),
                            eventType = type.value,
                            eventDate = datePickerState.selectedDateMillis?:0,
                            eventTitle = title,
                            eventDescription = desc,
                            eventAddress = address ,
                            eventCoordinates = listOf(singapore.latitude, singapore.longitude),
                            eventStartTime = eventstarttime.value,
                            eventEndTime = eventendtime.value,
                            eventHostUserId = FirebaseAuth.getInstance().currentUser?.uid?:"",
                            eventJoinList = listOf(FirebaseAuth.getInstance().currentUser?.uid?:""),
                            eventPendingList = emptyList()
                        ))
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    },
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
    label: String,
    modifier: Modifier = Modifier
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

