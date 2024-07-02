package com.example.mycollegeapp.navigation

sealed class Screens (val screen: String){
    data object Home : Screens("home")
    data object Gallery : Screens("gallery")
    data object Faculty : Screens("faculty")
    data object AboutUs : Screens("about_us")
    data object ContactUs : Screens("contact_us")
    data object BottomNav : Screens("bottom_nav")
    data object AdminDashboard : Screens("admin_dashboard")
    data object ManageBanner : Screens("manage_banner")
    data object ManageCollegeInfo : Screens("manage_college_info")
    data object ManageFaculty : Screens("manage_faculty")
    data object ManageGallery : Screens("manage_gallery")
    data object ManageNotice : Screens("manage_notice")
    data object FacultyDetailScreen : Screens("faculty_detail_screen/{catName}")
    data object Website : Screens("website")
    data object Notice : Screens("notice")
    data object LogInAdmin : Screens("log_in_admin")
}