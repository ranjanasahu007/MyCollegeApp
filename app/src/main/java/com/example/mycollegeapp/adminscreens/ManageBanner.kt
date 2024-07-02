package com.example.mycollegeapp.adminscreens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.itemview.BannerItemView
import com.example.mycollegeapp.models.BannerModel
import com.example.mycollegeapp.ui.theme.Purple40
import com.example.mycollegeapp.utils.Constant.BANNER
import com.example.mycollegeapp.viewmodel.BannerViewModel
import com.example.mycollegeapp.widget.LoadingDialog
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ManageBanner(navController: NavHostController) {

    val context = LocalContext.current.applicationContext
    val bannerViewModel : BannerViewModel = viewModel()
    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        imageUri = it
    }
    val isUploaded by bannerViewModel.isPosted.observeAsState(false)
    val isDeleted by bannerViewModel.isDeleted.observeAsState(false)
    val bannerList by bannerViewModel.bannerList.observeAsState(null)

    bannerViewModel.getBanner()

    val showLoader = remember {
        mutableStateOf(false)
    }

    if (showLoader.value){
        LoadingDialog(onDismissRequest = {
            showLoader.value = false
        })
    }

    LaunchedEffect(isUploaded){
        if (isUploaded){
            showLoader.value = false
            Toast.makeText(context,"Image Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    LaunchedEffect(isDeleted){
        if (isDeleted){
            Toast.makeText(context,"Image Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Banner")
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(imageVector = Icons.Default.ArrowBack , contentDescription = null)
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch("image/*")
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) {paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            if (imageUri != null)
            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = BANNER,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Row {
                    Button(
                        onClick = {
                            showLoader.value =true
                            bannerViewModel.saveImage(imageUri!!)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Add Image")
                    }
                    OutlinedButton(
                        onClick = { imageUri = null },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }

            LazyColumn{
                items(bannerList?: emptyList()){
                    BannerItemView(
                        bannerModel = it,
                        deleteImage = {docId ->  
                            bannerViewModel.deleteBanner(docId)
                        }
                    )
                }
            }
        }

    }
}
