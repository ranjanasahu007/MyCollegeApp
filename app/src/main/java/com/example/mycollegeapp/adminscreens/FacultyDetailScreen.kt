package com.example.mycollegeapp.adminscreens

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mycollegeapp.itemview.TeacherItemView
import com.example.mycollegeapp.ui.theme.Purple40
import com.example.mycollegeapp.viewmodel.FacultyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(navController: NavController, catName : String){

    val context = LocalContext.current.applicationContext

    val  facultyViewModel : FacultyViewModel = viewModel()

    val facultyList by facultyViewModel.facultyList.observeAsState(null)

    val isDeletedTeacher by facultyViewModel.isDeletedTeacher.observeAsState(false)


    facultyViewModel.getFaculty(catName)

    LaunchedEffect(isDeletedTeacher) {
        if (isDeletedTeacher) {
            Toast.makeText(context, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Manage Faculty")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack , contentDescription = null)
                    }
                }
            )
        }
    ) {padding ->
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 138.dp),
            modifier = Modifier.padding(padding)){
            items(facultyList?: emptyList()){
                TeacherItemView(facultyModel = it, deleteImage = {facultyModel ->  
                    facultyViewModel.deleteFaculty(facultyModel)
                })
            }
        }
    }
}