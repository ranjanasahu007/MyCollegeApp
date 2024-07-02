package com.example.mycollegeapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.R
import com.example.mycollegeapp.models.BannerModel
import com.example.mycollegeapp.models.FacultyModel
import com.example.mycollegeapp.models.NoticeModel
import com.example.mycollegeapp.ui.theme.SMALL_TEXT
import com.example.mycollegeapp.ui.theme.SkyBlue
import com.example.mycollegeapp.ui.theme.TEXT_SIZE
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.ui.theme.pink
import com.example.mycollegeapp.utils.Constant
import com.example.mycollegeapp.utils.Constant.isAdmin

@Composable
fun TeacherItemView(facultyModel: FacultyModel, deleteImage : (facultyModel: FacultyModel) -> Unit){
    OutlinedCard(
        modifier = Modifier
            .padding(5.dp)
    ) {
        ConstraintLayout {
            val (image, delete) = createRefs()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 25.dp)
            ) {

                Spacer(modifier = Modifier.height(6.dp))

                Image(
                    painter = rememberAsyncImagePainter(model = facultyModel.imageUrl),
                    contentDescription = Constant.FACULTY,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = facultyModel.name!!,
                    modifier = Modifier
                        .size(height = 30.dp, width = 100.dp)
                        .align(alignment = Alignment.Start),
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE
                )
                Text(
                    text = facultyModel.email!!,
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    SkyBlue,
                                    pink
                                )))
                        .size(height = 50.dp, width = 120.dp)
                        .align(alignment = Alignment.Start),
                    fontWeight = FontWeight.Thin,
                    fontSize = SMALL_TEXT,
                    color = Color.Black
                )
                Text(
                    text = facultyModel.position!!,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .size(height = 30.dp, width = 100.dp)
                        .align(alignment = Alignment.Start),
                    fontWeight = FontWeight.Thin,
                    fontSize = SMALL_TEXT,
                    color = SkyBlue
                )
            }

            if (isAdmin)
            Card(
                modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, margin = -45.dp)
                    }
                    .padding(8.dp)
                    .clickable {
                        deleteImage(facultyModel)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "delete",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}