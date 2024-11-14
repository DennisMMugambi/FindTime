package com.dennismugambi.findtime.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dennismugambi.findtime.TimeZoneHelper
import com.dennismugambi.findtime.TimeZoneHelperImpl
import com.dennismugambi.findtime.android.components.LocalTimeCard
import com.dennismugambi.findtime.android.components.TimeCard
import kotlinx.coroutines.delay

const val timeMillis = 1000 * 60L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeZoneScreen(
    currentTimeZoneStrings: SnapshotStateList<String>
) {
    val timeZoneHelper : TimeZoneHelper = TimeZoneHelperImpl()

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        var time by remember { mutableStateOf(timeZoneHelper.currentTime()) }

        LaunchedEffect(Unit) {
            while (true) {
                time = timeZoneHelper.currentTime()
                delay(timeMillis)
            }
        }

        LocalTimeCard(
            city = timeZoneHelper.currentTime(),
            time = time,
            date = timeZoneHelper.getDate(timeZoneHelper.currentTimeZone())
        )

        Spacer(modifier = Modifier.size(16.dp))

        LazyColumn(
            state = listState,
        ) {
            items(
                currentTimeZoneStrings.size,
                key = { timezone -> timezone }
            ) { index ->
                val timezoneString = currentTimeZoneStrings[index]
                SwipeToDismiss(
                    state = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                // Remove item if dismissed
                                currentTimeZoneStrings.removeAt(index)
                                true
                            } else {
                                false
                            }
                        }
                    ),
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(horizontal = 20.dp)
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = 1f)
                            )
                        }
                    },
                    dismissContent = {
                        TimeCard(
                            timezone = timezoneString,
                            hours = timeZoneHelper.hoursFromTimeZone(timezoneString),
                            time = timeZoneHelper.getTime(timezoneString),
                            date = timeZoneHelper.getDate(timezoneString)
                        )
                    }
                )
            }
        }

    }
}