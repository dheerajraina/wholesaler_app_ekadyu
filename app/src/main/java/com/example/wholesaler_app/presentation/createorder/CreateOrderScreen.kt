package com.example.wholesaler_app.presentation.createorder



import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrderScreen(navController: NavController,screenWidth:Dp,screenHeight:Dp){
    Scaffold(
        topBar = {
            TopAppBar(
               title ={
                     Box(
                       modifier = Modifier.fillMaxSize(),
                       contentAlignment = Alignment.Center
                   ) {
                       Text(
                           modifier = Modifier
                               .padding(0.dp,0.dp,screenWidth*0.15f,0.dp),
                           text = "For Sale",
                           fontWeight = FontWeight.W900,

                       )
                   }
               },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew, // Use a "Back" icon here
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) { innerPadding->
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Create new order",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                CreateOrderForm()

                Spacer(
                    modifier = Modifier.size(20.dp)
                )
                // Create Order button at the bottom
                CreateOrderButton({},)
            }
        }

    }
}



@Composable
fun CreateOrderForm() {
    // Long card containing sub-cards
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, bottom = 16.dp, end = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CustomSubCard(Icons.Outlined.EnergySavingsLeaf,"Enter Item Name",KeyboardType.Text)
            CustomSubCard(Icons.Outlined.StarOutline,"Select Quality",KeyboardType.Number)
            CustomSubCard(Icons.Outlined.Warning,"Select Wastage Percentage",KeyboardType.Number)
            CustomSubCard(Icons.Outlined.CheckBoxOutlineBlank,"Select Quantity (Kg)",KeyboardType.Number)
            CustomSubCard(Icons.Outlined.Money,"Enter Price per Kg (Rs.)",KeyboardType.Number)
            DateEntryCard()
            CustomSubCard(Icons.Outlined.LocationOn,"Enter Pickup Location",KeyboardType.Text)
        }
    }
}


@Composable
fun CustomSubCard(icon:ImageVector,placeholderText:String,keyboardType: KeyboardType) {
    var textValue by remember { mutableStateOf<String>("") }
     Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation =   CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(end = 16.dp))
            TextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                value = textValue,
                onValueChange = {textValue=it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = placeholderText) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor =Color.White ,
                    focusedTextColor =Color.Black ,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black

                )
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateEntryCard() {
    val context = LocalContext.current

    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedDateTimeText by remember { mutableStateOf("") }

    val openDatePickerModal = remember { mutableStateOf(false) }
    val openTimePickerDialog = remember { mutableStateOf(false) }

    val now = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = now.get(Calendar.HOUR_OF_DAY),
        initialMinute = now.get(Calendar.MINUTE),
        is24Hour = true,
    )

    fun resetSelection() {
        selectedDateMillis = null
        selectedDateTimeText = ""
    }

    fun formatDateTime(dateMillis: Long?, hour: Int, minute: Int): String {
        if (dateMillis == null) return ""
        val calendar = Calendar.getInstance().apply { timeInMillis = dateMillis }
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        val month = (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val year = calendar.get(Calendar.YEAR)
        val hr = hour.toString().padStart(2, '0')
        val min = minute.toString().padStart(2, '0')
        return "$day/$month/$year $hr:$min"
    }

    if (openDatePickerModal.value) {
        DatePickerModal(
            onDateSelected = { millis ->
                val todayStart = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                if (millis != null && millis >= todayStart) {
                    selectedDateMillis = millis
                    openDatePickerModal.value = false
                    openTimePickerDialog.value = true
                } else {
                    Toast.makeText(context, "Cannot select a past date.", Toast.LENGTH_SHORT).show()
                    resetSelection()
                    openDatePickerModal.value = false
                }
            },
            onDismiss = {
                openDatePickerModal.value = false
                resetSelection()
            }
        )
    }

    if (openTimePickerDialog.value) {
        TimePickerDialog(
            onDismiss = {
                openTimePickerDialog.value = false
                resetSelection()
            },
            onConfirm = {
                val selectedDateCal = Calendar.getInstance().apply {
                    timeInMillis = selectedDateMillis ?: 0
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val nowTime = Calendar.getInstance()
                if (selectedDateCal.after(nowTime)) {
                    selectedDateTimeText = formatDateTime(
                        selectedDateMillis,
                        timePickerState.hour,
                        timePickerState.minute
                    )
                    openTimePickerDialog.value = false
                } else {
                    Toast.makeText(context, "Selected date and time is in the past.", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null, modifier = Modifier.padding(end = 16.dp))
            Text(
                text = if (selectedDateTimeText.isNotEmpty()) selectedDateTimeText else "Select Date",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {
                openDatePickerModal.value = true
            }) {
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val today = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today,
        selectableDates = object: SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }

//
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}


@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        },
        text = { content() }
    )
}


@Composable
fun CreateOrderButton(onClick:()->Unit,){
    Button(
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(134,174,95)),
        onClick = onClick ,
        modifier = Modifier
                    .fillMaxWidth()
//            .padding(vertical = screenHeight * 0.05f, horizontal = screenWidth * 0.3f)
            .size(width = 150.dp, height = 70.dp) // Smaller and more square-like

    ) {
        Row {
            Icon(
                imageVector = Icons.Outlined.AddCircle,"Create Order")
            Spacer(
                modifier =Modifier
                    .padding(end = 2.dp),
            )
            Text(
//                    textAlign = TextAlign.Center,
                text = "Create Order",
                color = Color.White,
                fontWeight = FontWeight.W900
            )
        }
    }
}