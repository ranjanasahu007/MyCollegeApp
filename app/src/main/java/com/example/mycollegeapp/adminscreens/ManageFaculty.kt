package com.example.mycollegeapp.adminscreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.R
import com.example.mycollegeapp.itemview.FacultyItemView
import com.example.mycollegeapp.navigation.Screens
import com.example.mycollegeapp.ui.theme.Purple40
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.utils.Constant
import com.example.mycollegeapp.viewmodel.FacultyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFaculty(navController: NavController) {

    // faculty view model
    val facultyViewModel: FacultyViewModel = viewModel()

    facultyViewModel.getFacultyCategory()

    val isUploadedTeacher by facultyViewModel.isPostedTeacher.observeAsState(false)
    val isUploadedCategory by facultyViewModel.isPostedCategory.observeAsState(false)
    val isDeletedCategory by facultyViewModel.isDeletedCategory.observeAsState(false)
    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    val option = arrayListOf<String>()

    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var isExpended by remember {
        mutableStateOf(false)
    }

    var isCategory by remember {
        mutableStateOf(false)
    }

    var isTeacher by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var position by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploadedTeacher) {
        if (isUploadedTeacher) {
            Toast.makeText(context, "Faculty Uploaded Successfully", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false
            isTeacher = false
            name = ""
            email = ""
            position = ""
            category = ""
        }
    }

    LaunchedEffect(isUploadedCategory) {
        if (isUploadedCategory) {
            Toast.makeText(context, "Faculty Uploaded Successfully", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false
            isTeacher = false
            name = ""
            email = ""
            position = ""
            category = ""
        }
    }

    LaunchedEffect(isDeletedCategory) {
        if (isDeletedCategory) {
            Toast.makeText(context, "Teacher's Category Deleted Successfully", Toast.LENGTH_SHORT).show()
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
        Column(
            modifier = Modifier.padding(padding)
        ) {

            Row(
                modifier = Modifier.padding(8.dp)
            ) {

                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                        .clickable {
                            isCategory = true
                            isTeacher = false
                        }
                ) {
                    Text(text = "Add Category",
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_SIZE,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                        .clickable {
                            isCategory = false
                            isTeacher = true
                        }
                ) {
                    Text(text = "Add Teacher",
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_SIZE,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

            }

            if (isCategory) {
                ElevatedCard(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {
                            category = it
                        },
                        placeholder = {
                            Text(text = "Category")
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )

                    Row {
                        Button(
                            onClick = {

                                if (category == "") {
                                    Toast.makeText(
                                        context,
                                        "Please enter category name!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    facultyViewModel.saveFacultyCategory(category)
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = "Add Category")
                        }
                        OutlinedButton(
                            onClick = {
                                imageUri = null
                                isCategory = false
                                isTeacher = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }

            if (isTeacher) {
                ElevatedCard(
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(6.dp))

                        Image(painter =
                            if (imageUri == null)
                                painterResource(id = R.drawable.placeholder)
                            else
                                rememberAsyncImagePainter(model = imageUri),
                            contentDescription = Constant.FACULTY,
                            modifier = Modifier
                                .height(120.dp)
                                .width(120.dp)
                                .clip(CircleShape)
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentScale = if (imageUri == null) ContentScale.Fit else ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            placeholder = {
                                Text(text = "Name")
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            placeholder = {
                                Text(text = "Email")
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = position,
                            onValueChange = {
                                position = it
                            },
                            placeholder = {
                                Text(text = "Position")
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        )

                        Box(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopStart)
                        ) {
                            OutlinedTextField(
                                value = category,
                                onValueChange = {
                                    category = it
                                },
                                readOnly = true,
                                placeholder = {
                                    Text(text = "Select Department")
                                },
                                label = {
                                    Text(text = "Department Name")
                                },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpended)
                                }
                            )

                            DropdownMenu(
                                expanded = isExpended,
                                onDismissRequest = { isExpended = false }
                            ) {

                                if (categoryList != null && categoryList!!.isNotEmpty()) {
                                    option.clear()
                                    for (data in categoryList!!) {
                                        option.add(data)
                                    }
                                }

                                option.forEach { selectedOption ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = selectedOption)
                                        },
                                        onClick = {
                                            category = selectedOption
                                            isExpended = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                }

                            }

                            Spacer(
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(10.dp)
                                    .clickable {
                                        isExpended = !isExpended
                                    }
                            )
                        }


                        Row {
                            Button(
                                onClick = {

                                    if (imageUri == null) {
                                        Toast.makeText(
                                            context,
                                            "Please select image!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }else if (name == "") {
                                        Toast.makeText(
                                            context,
                                            "Please enter name!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }else if (email == "") {
                                        Toast.makeText(
                                            context,
                                            "Please enter email!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }else if (position == "") {
                                        Toast.makeText(
                                            context,
                                            "Please enter position!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }else if (category == "") {
                                        Toast.makeText(
                                            context,
                                            "Please select category!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        facultyViewModel.saveFaculty(imageUri!!, name, email, position, category)
                                    }

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text(text = "Add Teacher")
                            }
                            OutlinedButton(
                                onClick = {
                                    imageUri = null
                                    isCategory = false
                                    isTeacher = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(8.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }

                    }
                }
            }

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
    }
}