package com.example.notebook.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DeleteDialog(DissMiss: () -> Unit,onDeleteClick: () -> Unit){
    Dialog(
        onDismissRequest = { DissMiss() }
    ) {
        Box(modifier = Modifier
            .width(280.dp)
            .height(260.dp)
            .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
        ){
            Text(
                text = "删除笔记",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 20.dp, start = 20.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "确定要删除这条笔记吗？删除后无法恢复。",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding( start = 20.dp, end = 20.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 20.dp)
                    .width(60.dp)
                    .height(40.dp)
                    .background(Color.Red.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                    .clickable { onDeleteClick() }
            ){
                Text(
                    text = "删除",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 100.dp)
                    .width(60.dp)
                    .height(40.dp)
                    .background(Color.Gray.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                    .clickable { DissMiss() }
            ){
                Text(
                    text = "取消",
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}