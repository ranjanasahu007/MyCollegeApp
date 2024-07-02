import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mycollegeapp.MainActivity
import com.example.mycollegeapp.login.GradientBox
import com.example.mycollegeapp.login.KeyboardState
import com.example.mycollegeapp.login.MyTextField
import com.example.mycollegeapp.login.isSmallScreenHeight
import com.example.mycollegeapp.utils.Constant.isAdmin
import com.example.mycollegeapp.widget.LoadingDialog
import com.google.firebase.auth.FirebaseAuth

@Preview(showSystemUi = true)
@Composable
fun LoginAdminPreview() {
    LogInAdmin(rememberNavController())
}

@Composable
fun LogInAdmin(navController: NavController) {

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    // for loading dialog
    val showLoader = remember {
        mutableStateOf(false)
    }

    if (showLoader.value) {
        LoadingDialog(onDismissRequest = {
            showLoader.value = false
        })
    }

    val context = LocalContext.current

    val isImeVisible by KeyboardState()
    GradientBox( modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val animateUpperSection by animateFloatAsState(
                targetValue = if (isImeVisible) 0.15f else 0.45f,
                label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animateUpperSection),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Welcome to Admin Panel",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSmallScreenHeight()){
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                }else {
                    Spacer(modifier = Modifier.fillMaxSize(0.01f))
                }
                Text(
                    text = "Log In as Admin",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "UserName" ,
                    value = username,
                    onNameChange = { username = it },
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions(),
                    trailingIcon = Icons.Default.AccountCircle
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Password" ,
                    value = password,
                    onNameChange = {password = it },
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions(),
                    trailingIcon = Icons.Default.Lock
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (username.isEmpty() || password.isEmpty()) {
                                Toast.makeText(context, "Please Enter Email and Password!", Toast.LENGTH_SHORT).show()
                            }else{

                                if (username == "adminpanel@gmail.com" && password == "admin123") {

                                    showLoader.value = true

                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                                        .addOnCompleteListener {task ->
                                            if (task.isSuccessful) {

                                                showLoader.value = false
                                                isAdmin = true

                                                val intent = Intent(context, MainActivity::class.java).apply {
                                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                }
                                                context.startActivity(intent)
                                                navController.popBackStack()

                                            } else {
                                                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                }else {
                                    Toast.makeText(context, "Oops! You are not an Admin.", Toast.LENGTH_SHORT).show()
                                }

                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D4C92),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Submit",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(500)
                            )
                        )
                    }
                }
            }
        }
    }
}