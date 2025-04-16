package com.example.mysquad.ui.screens.mainScreen.AddScreen

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.mysquad.R
import com.example.mysquad.componets.ashley.*
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.DisplayDatePicker

@RequiresApi(64)
@Composable
fun AddScreen(modifier: Modifier = Modifier) {

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFAFDDFF)) //让 Surface 占满整个屏幕
    ) {
        val context = LocalContext.current
        val verticalScrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(top = 35.dp)
                .verticalScroll(verticalScrollState),
        ) {

            //标题
            Surface(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Create event",
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = Color(0xFF60B5FF),
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            //选择活动类型
            Row() {
                Box(modifier = Modifier.weight(1.0f)){
                    Downregulate(
                        labelText = "select a type of event",
                        states = listOf("Basketball", "Football", "Volleyball", "Badminton", "Table Tennis", "Tennis", "Swimming", "Aerobics")
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1.0f)){
                    DisplayDatePicker()
                }

            }

            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Please enter the title of the event"  //
            )



            Box(
                modifier = Modifier
                    .fillMaxWidth()  // 使输入框占满整个宽度
                    .padding(2.dp)   // 设置内边距，避免背景色超出边框
                    .background(Color(0xFFF4EEFF)) // 设置背景色为浅紫色
            ) {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = {
                        Text(
                            text = "Please enter address of the event",
                            fontSize = 15.sp,  // 设置提示语的字体大小
                            fontWeight = FontWeight.Bold // 设置字体加粗
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()  // 输入框宽度占满
                        .height(100.dp)  // 设置输入框高度
                        .padding(top = 8.dp, bottom = 8.dp)  // 设置输入框内的上下内边距
                )
            }

            Row() {
                Box(modifier = Modifier.weight(1.0f)){
                    Downregulate(
                        labelText = "start time",
                        states = listOf("10:00", "12:00", "13:00", "15:00", "16:00")
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1.0f)){
                    Downregulate(
                        labelText = "end time",
                        states = listOf("14:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }

            }

            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Number of people scheduled for the event"  // 只传递label的文字
            )


            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Please enter address of the event"  // 只传递label的文字
            )

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.googlemap),
                    contentDescription = "111",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(vertical = 20.dp) // Adjust the size of the image
                )
            }

            //分界线，以下是两个buton的
            Row(
                modifier = Modifier.fillMaxWidth(), // 让 Row 撑满宽度
                horizontalArrangement = Arrangement.SpaceEvenly // 两个按钮分开，左边和右边对齐
            ) {
                Button(
                    onClick = { /* 处理 Cancel 按钮点击事件 */ },
                    modifier = Modifier.width(150.dp), //固定宽度
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9149) // 设置按钮背景色为橙色
                    )
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = { /* 处理 Join 按钮点击事件 */ },
                    modifier = Modifier.width(150.dp), //固定宽度
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9149) // 设置按钮背景色为橙色
                    )
                ) {
                    Text(
                        text = "Post",
                        color = Color.White, // 设置文字颜色为白色
                        style = TextStyle(
                            fontSize = 15.sp, // 设置字体大小
                            fontWeight = FontWeight.Bold // 设置文字加粗
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String  // 只传递label的文字
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)  // 避免背景色超出边框
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EEFF))  // 固定背景色为浅紫色
        )
    }
}