package com.example.mycollegeapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.R
import com.example.mycollegeapp.models.BannerModel
import com.example.mycollegeapp.models.NoticeModel
import com.example.mycollegeapp.ui.theme.SMALL_TEXT
import com.example.mycollegeapp.ui.theme.SkyBlue
import com.example.mycollegeapp.ui.theme.TEXT_SIZE
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.utils.Constant.isAdmin

@Composable
fun NoticeItemView(noticeModel: NoticeModel, deleteImage : (docId : NoticeModel) -> Unit){
    OutlinedCard(
        modifier = Modifier
            .padding(5.dp)
    ) {
        ConstraintLayout {
            val (image, delete) = createRefs()

            Column {
                Text(
                    text = noticeModel.title!!,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE
                )
                if (noticeModel.link != ""){
                Text(
                    text = noticeModel.link!!,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Thin,
                    fontSize = SMALL_TEXT,
                    color = SkyBlue
                )}
                Image(
                    painter = rememberAsyncImagePainter(model = noticeModel.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }

            if (isAdmin)
            Card(
                modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp)
                    .clickable {
                        deleteImage(noticeModel)
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