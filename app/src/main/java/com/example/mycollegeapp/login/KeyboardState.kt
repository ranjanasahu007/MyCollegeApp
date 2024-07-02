package com.example.mycollegeapp.login

import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun  KeyboardState() : State<Boolean>{
    val isKeyState = remember {
        mutableStateOf(false)
    }
    val view = LocalView.current

    DisposableEffect(view){
        val listner = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            isKeyState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listner)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listner)
        }
    }
    return isKeyState
}