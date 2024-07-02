package com.example.mycollegeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.R
import com.example.mycollegeapp.ui.theme.SMALL_TEXT
import com.example.mycollegeapp.ui.theme.SkyBlue
import com.example.mycollegeapp.ui.theme.TEXT_SIZE
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.viewmodel.CollegeInfoViewModel

@Composable
fun ContactUs(navController: NavController) {
    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val collegeList by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "My College App",
                    fontSize = TITLE_SIZE,
                    fontWeight = FontWeight.SemiBold
                ) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                contentColor = Color.White,
                backgroundColor = Color.White
            )
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .padding(8.dp)
                .padding(top = 60.dp)
        ) {
            if (collegeList != null) {

                Image(
                    painter = rememberAsyncImagePainter(model = collegeList!!.imageUrl),
                    contentDescription = "College Image",
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.name!!,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.desc!!,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TEXT_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.address!!,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TEXT_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.websiteLink!!,
                    color = SkyBlue,
                    fontWeight = FontWeight.Thin,
                    fontSize = SMALL_TEXT
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {

                    Image(
                        painter = painterResource(id = R.drawable.phn),
                        contentDescription = "Phone",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Phone No.",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_SIZE
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "6204393511",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TEXT_SIZE,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        }
    }
}