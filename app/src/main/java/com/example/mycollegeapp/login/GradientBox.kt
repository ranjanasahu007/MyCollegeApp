package com.example.mycollegeapp.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.mycollegeapp.ui.theme.LightVoilet
import com.example.mycollegeapp.ui.theme.Voilet

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    content : @Composable BoxScope.() -> Unit
){
    Box(modifier = Modifier.background(brush = Brush.linearGradient(
        listOf(
            Voilet, LightVoilet
        )
    ))) {
            content()
    }
}