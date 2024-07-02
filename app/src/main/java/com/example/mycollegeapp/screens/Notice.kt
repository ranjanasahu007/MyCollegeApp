package com.example.mycollegeapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mycollegeapp.itemview.NoticeItemView
import com.example.mycollegeapp.navigation.Screens
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.viewmodel.NoticeViewModel

@Composable
fun Notice(navController: NavController) {


    val noticeViewModel : NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState(null)
    noticeViewModel.getNotice()

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
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .padding(top = 60.dp)
        ) {
            items(noticeList ?: emptyList()) {
                NoticeItemView(
                    it,
                    deleteImage = { docId ->
                        noticeViewModel.deleteNotice(docId)
                    }
                )
            }
        }
    }

}