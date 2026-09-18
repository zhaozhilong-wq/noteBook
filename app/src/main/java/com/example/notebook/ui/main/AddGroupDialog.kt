package com.example.notebook.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddGroupDialog(DissMiss: () -> Unit, onConfirm: (String) -> Unit) {
    Dialog(
        onDismissRequest = DissMiss
    )
    {
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(300.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(18.dp)
                )
        ) {
            var text by remember { mutableStateOf("") }
            Text(
                text = "添加分组",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
            )

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(260.dp)
                    .height(56.dp)
                    .border(1.dp, Color.Red, RoundedCornerShape(18.dp))
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp)
                    .width(60.dp)
                    .height(40.dp)
                    .background(color = Color.Blue, shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "确定",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onConfirm(text)
                                DissMiss()
                            }
                        )
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 100.dp, bottom = 20.dp)
                    .width(60.dp)
                    .height(40.dp)
                    .background(color = Color.Gray.copy(alpha = 0.2f), shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "取消",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                DissMiss()
                            }
                        )
                )
            }
        }

        }
    }

