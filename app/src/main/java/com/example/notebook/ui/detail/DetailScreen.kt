package com.example.notebook.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.R

@Composable
fun DetailScreen(detailUiState: DetailUiState,dispatch : (DetailEvent) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        var showGroupSelectDialog by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DetailTopBar(backClick = { dispatch(DetailEvent.back) })
            DetailTitle(detailUiState.title,dispatch)
            Box(modifier = Modifier
                .padding( start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray.copy(alpha = 0.2f)))
            DetailContent(detailUiState.content,dispatch)
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray.copy(alpha = 0.2f)))
            DetailBottom(
                group = detailUiState.group,
                onClick = { showGroupSelectDialog = true },
                modifier = Modifier
                .fillMaxWidth()
                .weight(1f))

        }
        if (showGroupSelectDialog)
        {
            GroupSelectDialog(
                selectGroup = detailUiState.group,
                groupList = detailUiState.groupList,
                onItemClick = { group ->
                    dispatch(DetailEvent.selectGroup(group))
                    showGroupSelectDialog = false
                },
                onDismiss = { showGroupSelectDialog = false }
            )

        }
    }

    BackHandler {
        dispatch(DetailEvent.back)
    }
}

@Composable
fun DetailTopBar(backClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
    ){
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f))
                .clickable(
                    onClick = backClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back"
            )
        }

        Text(
            text = "编辑笔记",
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun DetailTitle(title: String, dispatch: (DetailEvent) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)

    ) {

        TextField(
            value = title,
            onValueChange = {
                dispatch(DetailEvent.onTitleChange(it)) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
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
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 5.dp),

        )
    }
}

@Composable
fun DetailContent(content: String,dispatch: (DetailEvent) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(580.dp)
    ) {

        TextField(
            value = content,
            onValueChange = {
                dispatch(DetailEvent.onContentChange(it)) },
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
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(start = 5.dp),

            )
    }
}
@Composable
fun DetailBottom(group: String,modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(
        modifier = modifier
            .background(Color(0xFFEAEAEE))
    ) {

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "分组：",
                fontSize = 18.sp,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = group,
                fontSize = 18.sp,
                color = Color.Blue,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                contentDescription = "Edit",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 15.dp)
            )
        }
    }
}
