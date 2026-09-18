package com.example.notebook.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Delete
import com.example.notebook.R
import com.example.notebook.data.entity.note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainScreen(
    mainUiState: MainUiState,
    dispatch:(MainEvent)->Unit
)
{
    var showDeleteDialog by remember { mutableStateOf(false) }

    var showSearchDialog by remember { mutableStateOf(false) }

    var showAddGroupDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(mainUiState.isgrid,onSearchClick={},onShowTypeBarClick={dispatch(MainEvent.toggleGrid)},onShowSearchClick={showSearchDialog = true})
            Spacer(modifier = Modifier.height(20.dp))
            GroupList(mainUiState.groups, mainUiState.selectedGroup, onGroupClick = {dispatch(MainEvent.selectGroup(it))},onAddGroupClick = {showAddGroupDialog = true})
            NoteList(dispatch,mainUiState.isgrid, mainUiState.notes,onNoteLongpress = {showDeleteDialog = true},openDetail = {dispatch(MainEvent.openDetail(it))})
        }
    }

    if(showDeleteDialog)
    {
        DeleteDialog(DissMiss = {
            showDeleteDialog = false
        }, onDeleteClick = {
            mainUiState.selectNote?.let { dispatch(MainEvent.deleteNote(it)) }
            showDeleteDialog = false
        })
    }

    if (showSearchDialog)
    {
        SearchDialog(DissMiss = {
            showSearchDialog = false
        }, onValueChanged = {
            dispatch(MainEvent.search(mainUiState.selectedGroup,it))
        })
    }

    if (showAddGroupDialog)
    {
        AddGroupDialog(DissMiss = {
            showAddGroupDialog = false
        }, onConfirm = {
            dispatch(MainEvent.addGroup(it))
        })
    }
}

@Composable
fun TopBar(
    isgrid: Boolean,
    onSearchClick: () -> Unit,
    onShowTypeBarClick: () -> Unit,
    onShowSearchClick: () -> Unit
    ){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
    ){
        Text(
            text = "记事本",
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterStart)
        )
        Box(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 20.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable(
                onClick = onShowTypeBarClick
            )
        ) {
            Image(
                painter = painterResource(if(isgrid) R.drawable.ic_view_grid else R.drawable.ic_view_list),
                contentDescription = "View List",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            )
        }

        Box(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 80.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable(
                onClick = onSearchClick
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .clickable(
                        onClick = onShowSearchClick
                    )
            )
        }

    }
}
@Composable
fun GroupList(
    groups: List<String>,
    selectGroup: String,
    onGroupClick: (String) -> Unit,
    onAddGroupClick: () -> Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(groups) { group ->
                GroupItem(group, selectGroup,onGroupClick)
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .clickable(
                            onClick ={}
                        )
                ){
                    Image(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
                            .clickable(
                                onClick = onAddGroupClick
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun GroupItem(group: String, selectGroup: String, onGroupClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(45.dp)
            .padding(start = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if(selectGroup == group) Color.Blue else Color.Gray.copy(alpha = 0.5f))
            .clickable(
                onClick = { onGroupClick(group) }
            )
    ){
        Text(
            text = group,
            color = if (selectGroup == group) Color.White else Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun NoteList(dispatch: (MainEvent) -> Unit, isgrid: Boolean, notes: List<note>, onNoteLongpress: () -> Unit,openDetail: (Long?) -> Unit){
    if (isgrid)
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAEAEE))
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                content = {
                    items(notes) { note ->
                        NoteItem(dispatch,isgrid,note, onNoteLongpress,openDetail)
                    }
                }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 30.dp, bottom = 30.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Red.copy(alpha = 0.5f))
                    .clickable(
                        onClick = {openDetail(null)}
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAEAEE))
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding( start = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxSize()
            ) {
                items(notes) { note ->
                    NoteItem(dispatch,isgrid,note,onNoteLongpress,openDetail)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 30.dp, bottom = 30.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Red.copy(alpha = 0.5f))
                    .clickable(
                        onClick = { openDetail(null) }
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}
@Composable
fun NoteItem(dispatch: (MainEvent) -> Unit,isgrid: Boolean,note: note,onNoteLongpress: () -> Unit,openDetail: (Long?) -> Unit){
    if (isgrid){
        Box(
            modifier = Modifier
                .width(90.dp)
                .height(150.dp)
                .padding(start = 15.dp, bottom = 15.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .combinedClickable(
                    onLongClick = {
                        onNoteLongpress()
                        dispatch(MainEvent.selectNote(note))
                    },
                    onClick = {
                        dispatch(MainEvent.selectNote(note))
                        openDetail(note.id)
                    }
                )
        ) {
            Text(
                text = if (note.title=="") {
                    note.content.take(10)
                } else {
                    note.title
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
            )
            Text(
                text = formatTime(note.createdAt),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.dp, bottom = 60.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.dp, bottom = 20.dp)
                    .height(30.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Blue.copy(alpha = 0.5f))
            ) {
                Text(
                    text = note.group,
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
    else {
        Box(
            modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .combinedClickable(
                onLongClick = {
                    onNoteLongpress()
                    dispatch(MainEvent.selectNote(note))
                },
                onClick = {
                    dispatch(MainEvent.selectNote(note))
                    openDetail(note.id)
                }
            )
        ) {
            Text(
                text = if (note.title=="") {
                    note.content.take(10)
                } else {
                    note.title
                },
                color = Color.Black,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 100.dp, bottom = 20.dp)
            )
            Text(
                text = formatTime(note.createdAt),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.dp, bottom = 20.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 15.dp, bottom = 20.dp)
                    .height(30.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Blue.copy(alpha = 0.5f))
            ) {
                Text(
                    text = note.group,
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}
fun formatTime(timeMillis: Long): String {
    val format = SimpleDateFormat(
        "yyyy-MM-dd HH:mm",
        Locale.getDefault()
    )
    return format.format(Date(timeMillis))
}
