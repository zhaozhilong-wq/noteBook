package com.example.notebook.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSelectDialog(
    selectGroup: String,
    groupList: List<String>,
    onDismiss: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null
            ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Text(
                        text = "选择分组",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 15.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 15.dp)
                        .size(40.dp),
                        contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onDismiss()
                                }
                        )
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)){
                    LazyColumn(modifier = Modifier
                        .padding(all = 15.dp)
                        .fillMaxSize()) {
                        items(groupList) { group ->
                            GroupListItem(group, selectGroup, onItemClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GroupListItem(group: String, selectGroup: String, onItemClick: (String) -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(group)
            }
            .padding(bottom = 10.dp)
    ) {
        Text(
            text = group,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = if (group == selectGroup) Color.Blue else Color.Black
        )
        if (group == selectGroup) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Check",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    .size(30.dp),
                colorFilter = ColorFilter.tint(Color.Blue)
            )
        }
    }
}
