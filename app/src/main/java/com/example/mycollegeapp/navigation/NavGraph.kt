package com.example.mycollegeapp.navigation

import LogInAdmin
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycollegeapp.adminscreens.AdminDashboard
import com.example.mycollegeapp.adminscreens.FacultyDetailScreen
import com.example.mycollegeapp.adminscreens.ManageBanner
import com.example.mycollegeapp.adminscreens.ManageCollegeInfo
import com.example.mycollegeapp.adminscreens.ManageFaculty
import com.example.mycollegeapp.adminscreens.ManageGallery
import com.example.mycollegeapp.adminscreens.ManageNotice
import com.example.mycollegeapp.screens.AboutUs
import com.example.mycollegeapp.screens.BottomnNav
import com.example.mycollegeapp.screens.ContactUs
import com.example.mycollegeapp.screens.Faculty
import com.example.mycollegeapp.screens.Gallery
import com.example.mycollegeapp.screens.Home
import com.example.mycollegeapp.screens.Notice
import com.example.mycollegeapp.screens.Website
import com.example.mycollegeapp.utils.Constant.isAdmin

@Composable
fun NavGraph ( navController : NavHostController ){

    
    NavHost(
        navController = navController,
        startDestination = if (isAdmin) Screens.AdminDashboard.screen else Screens.BottomNav.screen
    ){
        composable(Screens.BottomNav.screen){
            BottomnNav(navController)
        }
        composable(Screens.Home.screen){
            Home()
        }
        composable(Screens.ContactUs.screen){
            ContactUs(navController)
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
        composable(Screens.AdminDashboard.screen){
            AdminDashboard(navController)
        }
        composable(Screens.ManageBanner.screen){
            ManageBanner(navController)
        }
        composable(Screens.ManageFaculty.screen){
            ManageFaculty(navController)
        }
        composable(Screens.ManageCollegeInfo.screen){
            ManageCollegeInfo(navController)
        }
        composable(Screens.ManageGallery.screen){
            ManageGallery(navController)
        }
        composable(Screens.ManageNotice.screen){
            ManageNotice(navController)
        }
        composable(Screens.FacultyDetailScreen.screen){
            val data = it.arguments!!.getString("catName")
            FacultyDetailScreen(navController, data!!)
        }
        composable(Screens.Website.screen){
            Website()
        }
        composable(Screens.Notice.screen){
            Notice(navController)
        }
        composable(Screens.LogInAdmin.screen){
            LogInAdmin(navController)
        }
    }
}


