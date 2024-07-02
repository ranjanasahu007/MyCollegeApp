package com.example.mycollegeapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycollegeapp.itemview.GalleryItemView
import com.example.mycollegeapp.viewmodel.GalleryViewModel

@Composable
fun Gallery(){
    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(null)
    galleryViewModel.getGallery()

    LazyColumn {
        items(galleryList?: emptyList()) {
            GalleryItemView(it,
                delete = {category ->
                    galleryViewModel.deleteGallery(category)
                },
                deleteImage = {cat, imageUrl ->
                    galleryViewModel.deleteImage(cat, imageUrl)
                }
            )
        }
    }
}