package com.example.mycollegeapp.adminscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycollegeapp.models.DashboardItemModel
import com.example.mycollegeapp.navigation.Screens
import com.example.mycollegeapp.ui.theme.Purple40
import com.example.mycollegeapp.ui.theme.Purple80
import com.example.mycollegeapp.ui.theme.TITLE_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavHostController) {

    val list = listOf(
        DashboardItemModel(
            "Manage Banner",
            Screens.ManageBanner.screen
        ),
        DashboardItemModel(
            "Manage Notice",
            Screens.ManageNotice.screen
        ),
        DashboardItemModel(
            "Manage College Info",
            Screens.ManageCollegeInfo.screen
        ),
        DashboardItemModel(
            "Manage Faculty",
            Screens.ManageFaculty.screen
        ),
        DashboardItemModel(
            "Manage Gallery",
            Screens.ManageGallery.screen
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Admin Dashboard")
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40
                )
            )
        },

        content = {padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(items = list, itemContent = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(it.route)
                            }
                    ) {
                        Text(
                            text = it.title,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = TITLE_SIZE
                        )
                    }
                })
            }
        }
    )
}

@Preview
@Composable
fun  AdmindashboardPreview(){
    AdminDashboard(navController = rememberNavController())
}