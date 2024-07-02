package com.example.mycollegeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.mycollegeapp.itemview.NoticeItemView
import com.example.mycollegeapp.ui.theme.SMALL_TEXT
import com.example.mycollegeapp.ui.theme.SkyBlue
import com.example.mycollegeapp.ui.theme.TEXT_SIZE
import com.example.mycollegeapp.ui.theme.TITLE_SIZE
import com.example.mycollegeapp.viewmodel.BannerViewModel
import com.example.mycollegeapp.viewmodel.CollegeInfoViewModel
import com.example.mycollegeapp.viewmodel.NoticeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(){
    val bannerViewModel : BannerViewModel = viewModel()
    val bannerList by bannerViewModel.bannerList.observeAsState(null)
    bannerViewModel.getBanner()

    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val collegeList by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel : NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState(null)
    noticeViewModel.getNotice()

    val pagerState = rememberPagerState(initialPage = 0)

    val imageSlider = ArrayList<AsyncImagePainter>()

    if (bannerList != null){
        bannerList!!.forEach {
            imageSlider.add(rememberAsyncImagePainter(model = it.url))
        }
    }

    LaunchedEffect(Unit){
        try {
            while (true){
                yield()
                delay(2600)
                pagerState.animateScrollToPage(page = (pagerState.currentPage+1) % pagerState.pageCount)
            }
        }catch (e: Exception){

        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
    ){
        item {
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState
            ) {pager ->

                Card(
                    modifier = Modifier
                        .height(220.dp)
                ) {
                    Image(
                        painter = imageSlider[pager],
                        contentDescription = "banner",
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

        item {
            if (collegeList != null){
                Text(
                    text = collegeList!!.name!!,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.desc!!,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TEXT_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = collegeList!!.websiteLink!!,
                    color = SkyBlue,
                    fontWeight = FontWeight.Thin,
                    fontSize = SMALL_TEXT
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "NOTICES",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_SIZE
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        items(noticeList?: emptyList()){
            NoticeItemView(
                it,
                deleteImage = {docId ->
                    noticeViewModel.deleteNotice(docId)
                }
            )
        }
    }

}