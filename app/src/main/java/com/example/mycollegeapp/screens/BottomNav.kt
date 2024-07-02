package com.example.mycollegeapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycollegeapp.R
import com.example.mycollegeapp.models.BottomNavItem
import com.example.mycollegeapp.models.NavItem
import com.example.mycollegeapp.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomnNav(navController: NavController){

    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current.applicationContext
    val selected by rememberSaveable {
        mutableIntStateOf(-1)
    }

    val list = listOf(
        NavItem(
            "Website",
            R.drawable.web
        ),
        NavItem(
            "Notice",
             R.drawable.bell
        ),
        NavItem(
            "Log in as Admin",
            R.drawable.notes
        ),
        NavItem(
            "Contact Us",
            R.drawable.phone
        )
    )
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.meh),
                    contentDescription = null,
                    modifier = Modifier.height(220.dp),
                    contentScale = ContentScale.Crop
                )
                Divider()
                Text(text = "")

                list.forEachIndexed{ index, items ->
                    NavigationDrawerItem(
                        label = {Text(text = items.title)},
                        selected = index == selected,
                        onClick = {
                            when(items.title){
                                "Contact Us" -> navController.navigate(Screens.ContactUs.screen)
                                "Website" -> navController.navigate(Screens.Website.screen)
                                "Notice" -> navController.navigate(Screens.Notice.screen)
                                "Log in as Admin" -> navController.navigate(Screens.LogInAdmin.screen)
                                else -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        coroutineScope.launch {
                            drawerState.close()
                        }},
                        icon = {
                            Icon(
                                painter = painterResource(id = items.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "My College App") },
                        navigationIcon = {
                            IconButton(onClick = {coroutineScope.launch { drawerState.open() }}) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                            }
                        }
                    )
                },
                
                bottomBar = {
                    MyBottomNav(navController = navigationController)
                }
                
            ) {padding ->

                NavHost(
                    navController = navigationController,
                    startDestination = Screens.Home.screen,
                    modifier = Modifier.padding(padding)
                ){
                    composable(route = Screens.Home.screen){
                        Home()
                    }
                    composable(Screens.Gallery.screen){
                        Gallery()
                    }
                    composable(Screens.Faculty.screen){
                        Faculty(navController)
                    }
                    composable(Screens.AboutUs.screen){
                        AboutUs()
                    }
                }
            }
        }
    )
}

@Composable
fun MyBottomNav(navController: NavController){
    val backStackEntry = navController.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem(
            "Home",
            R.drawable.homee,
            Screens.Home.screen
        ),
        BottomNavItem(
            "Faculty",
            R.drawable.faculty,
            Screens.Faculty.screen
        ),
        BottomNavItem(
            "Gallery",
            R.drawable.gallery,
            Screens.Gallery.screen
        ),
        BottomNavItem(
            "About Us",
            R.drawable.aboutus,
            Screens.AboutUs.screen
        )
    )

    BottomAppBar {
        list.forEach {
            val cureentScreen = it.route
            val otherScreen =
                try {
                    backStackEntry.value!!.destination.route
                }catch (e : Exception){
                    Screens.Home.screen
                }

            val selectedScreen = cureentScreen == otherScreen

            NavigationBarItem(
                selected = selectedScreen,
                onClick = { navController.navigate(it.route){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                } },
                icon = { Icon(
                    painter = painterResource(id = it.icon),
                    contentDescription = it.title,
                    modifier = Modifier.size(30.dp),
                    tint = Color.DarkGray
                ) }
            )
        }
    }
}