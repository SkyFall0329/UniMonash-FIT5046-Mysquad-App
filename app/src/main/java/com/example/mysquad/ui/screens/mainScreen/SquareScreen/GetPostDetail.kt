package com.example.mysquad.ui.screens.mainScreen.SquareScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysquad.R

@Composable
fun GetPostDetail(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val verticalScrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(top = 40.dp)
            .padding(horizontal = 24.dp)
            .verticalScroll(verticalScrollState),
        horizontalAlignment = Alignment.Start, // 改为左对齐
        verticalArrangement = Arrangement.spacedBy(16.dp) // 设置文本之间的间距
    ) {


        //页面标题
        Surface(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            Text(
                text = "xxytfootballtime~",
                fontWeight = FontWeight.Bold, // 设置字体加粗
                fontSize = 35.sp, // 设置字体大小
                color = Color(0xFFFF6F00), // 设置字体颜色为橙色
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp, bottom = 10.dp, end = 10.dp)
            )
        }
        //分界线，以上是页面的标题

// 活动时间
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp)) // 设置背景色
                .padding(8.dp),

            ) {
            Text(text = "Start Time：", modifier = Modifier.weight(1f),//左对齐
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp)
            Text(text = "2025.05.25 15:00", modifier = Modifier.weight(2f)) // 右侧内容
        }
        HorizontalDivider(thickness = 2.dp)

        // 结束时间
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp)) // 设置背景色
                .padding(8.dp)
        ) {
            Text(text = "End Time：", modifier = Modifier.weight(1f),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp)
            Text(text = "2025.05.25 17:00", modifier = Modifier.weight(2f))
        }
        HorizontalDivider(thickness = 2.dp)

        // 活动描述
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp)) // 设置背景色
                .padding(8.dp)
        ) {
            Text(text = "Activity brief：", modifier = Modifier.weight(1f),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp)
            Text(text = "Join us forand make n – come play, stay active, and make new friends!", modifier = Modifier.weight(2f))
        }
        HorizontalDivider(thickness = 2.dp)

        // 人数
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp)) // 设置背景色
                .padding(8.dp)
        ) {
            Text(text = "headcount：", modifier = Modifier.weight(1f),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp)
            Text(text = "7/12", modifier = Modifier.weight(2f))
        }
        HorizontalDivider(thickness = 2.dp)

        // 地点
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp)) // 设置背景色
                .padding(8.dp)
        ) {
            Text(text = "Address：", modifier = Modifier.weight(1f),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp)
            Text(text = "Wellington Rd, Clayton VIC 3800", modifier = Modifier.weight(2f))
        }

        //----


        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.googlemap),
                contentDescription = "111",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust the size of the image
            )
        }



//        Spacer(modifier = Modifier.height(20.dp)) // 添加空白间隔
        ElevatedButton(onClick = {
            Toast.makeText(context, "This is a event", Toast.LENGTH_SHORT).show()
        },
            modifier = Modifier
                .fillMaxWidth() // 使按钮宽度填满
                .padding(16.dp) // 增加按钮的内边距
                .align(Alignment.CenterHorizontally), // 水平居中
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFFF6F00)) // 设置按钮背景色为橙色

        ) {
            Text(
                text = "Apply to join",
                color = Color.White,
                style = TextStyle(fontSize = 24.sp), // 设置字体大小为24sp
                fontWeight = FontWeight.Bold // 设置加粗
            )
        }
    }
}
