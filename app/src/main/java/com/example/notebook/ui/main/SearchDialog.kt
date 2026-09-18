package com.example.notebook.ui.main

import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.notebook.R

@Composable
fun SearchDialog(DissMiss: () -> Unit,onValueChanged: (String) -> Unit) {
    Dialog(onDismissRequest = DissMiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        dialogWindowProvider?.window?.let{
            window ->
            window.setGravity(Gravity.TOP)
            window.attributes?.windowAnimations = android.R.style.Animation_Dialog
        }

        var text by remember { mutableStateOf("") }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        )
        {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, top = 70.dp)
                    .width(300.dp)
                    .height(56.dp)
                    .background(Color(0xFFEAEAEE), shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)),
                     contentAlignment = Alignment.CenterStart
                    )
             {
                Image(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                )


                 TextField(
                     modifier = Modifier
                         .padding(start = 30.dp)
                         .width(250.dp)
                         .height(56.dp),
                     value = text,
                     onValueChange = {
                         text = it
                         onValueChanged(it)
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
                     )
                 )
                 Box(
                     modifier = Modifier
                         .align(Alignment.CenterEnd)
                         .padding(end = 20.dp)
                         .width(20.dp)
                         .height(20.dp)
                         .clip(CircleShape)
                         .background(Color.Gray.copy(alpha = 0.3f)),
                     contentAlignment = Alignment.CenterEnd
                 ) {
                     Image(
                         modifier = Modifier
                             .width(20.dp)
                             .height(20.dp)
                             .clickable {
                                 text = ""
                             },
                         painter = painterResource(id = R.drawable.ic_close),
                         contentDescription = "Close",

                     )
                 }



            }
            Text(
                text = "取消",
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp, top = 60.dp)
                    .clickable {
                        DissMiss()
                    },
            )
        }
    }
}