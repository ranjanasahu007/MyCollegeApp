package com.example.mycollegeapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mycollegeapp.itemview.FacultyItemView
import com.example.mycollegeapp.navigation.Screens
import com.example.mycollegeapp.viewmodel.FacultyViewModel

@Composable
fun Faculty(navController: NavController){
    val facultyViewModel: FacultyViewModel = viewModel()
    val categoryList by facultyViewModel.categoryList.observeAsState(null)
    facultyViewModel.getFacultyCategory()

    LazyColumn {
        items(categoryList?: emptyList()) {
            FacultyItemView(it,
                delete = {category ->
                    facultyViewModel.deleteFacultyCategory(category)
                },
                onClick = {categoryName ->
                    val routes = Screens.FacultyDetailScreen.screen.replace("{catName}", categoryName)
                    navController.navigate(routes)
                }
            )
        }
    }
}