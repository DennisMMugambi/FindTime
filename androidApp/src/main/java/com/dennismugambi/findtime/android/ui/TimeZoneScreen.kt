package com.dennismugambi.findtime.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dennismugambi.findtime.TimeZoneHelper
import com.dennismugambi.findtime.TimeZoneHelperImpl
import kotlinx.coroutines.delay

const val timeMillis = 1000 * 60L

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
    }
}