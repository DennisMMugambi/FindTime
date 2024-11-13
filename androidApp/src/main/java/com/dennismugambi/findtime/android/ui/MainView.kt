package com.dennismugambi.findtime.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dennismugambi.findtime.android.theme.AppTheme

sealed class Screen(val title: String) {
    object TimeZoneScreen : Screen("Timezones")
    object FindTimeScreen : Screen("Find Time")
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        Screen.TimeZoneScreen.title,
        Icons.Filled.List,
        "Timezones"
    ),
    BottomNavigationItem(
        Screen.FindTimeScreen.title,
        Icons.Filled.Place,
        "Find Time"
    )
)

@Composable
fun MainView(actionBarFun: topBarFun = { EmptyComposable() }) {
    val showAddDialog = remember { mutableStateOf(false) }
    val currentTimeZoneStrings = remember {
        SnapshotStateList<String>()
    }

    val selectedIndex = remember { mutableStateOf(0) }

    AppTheme {
        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.value)
            },
            floatingActionButton = {
                if (selectedIndex.value == 0) {

                    FloatingActionButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            showAddDialog.value = true
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            },
            bottomBar = {

                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary // updated for Material 3 color scheme
                ) {
                    bottomNavigationItems.forEachIndexed { i, bottomNavigationItem ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black
                            ),
                            label = {
                                Text(
                                    bottomNavigationItem.route,
                                    style = MaterialTheme.typography.labelMedium // updated for Material 3 typography
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.iconContentDescription
                                )
                            },
                            selected = selectedIndex.value == i,
                            onClick = {
                                selectedIndex.value = i
                            }
                        )
                    }
                }

            }
        ) { innerPadding ->
            // TODO: Replace with Dialog

            Column(
                modifier = Modifier.padding(innerPadding)
            ) {

                when (selectedIndex.value) {
                    0 -> TimeZoneScreen(currentTimeZoneStrings)
                    //1 -> FindTimeScreen()
                }
            }
        }
    }
}

