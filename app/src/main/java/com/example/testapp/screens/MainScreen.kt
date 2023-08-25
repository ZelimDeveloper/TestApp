package com.example.testapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.models.Camera
import com.example.testapp.models.Door
import com.example.testapp.repositories.CameraRepository
import com.example.testapp.repositories.DoorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel:MainScreenVM = hiltViewModel()) {

    val pages = listOf("Камеры", "Двери")
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var door by remember { mutableStateOf(Door()) }
    var isLoading by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            viewModel.getCameras(loadingStart = { isLoading = true }, loadingEnd = { isLoading = false })
            viewModel.getDoors(loadingStart = { isLoading = true }, loadingEnd = { isLoading = false })
        }
    )

    LaunchedEffect(Unit) {
        if (viewModel.getCamerasFromDB().isEmpty()) {
            viewModel.getCameras(loadingStart = { isLoading = true }, loadingEnd = { isLoading = false })
        }
        if (viewModel.getDoorsFromDB().isEmpty()) {
            viewModel.getDoors(loadingStart = { isLoading = true }, loadingEnd = { isLoading = false })
        }
    }
    
    Box {
        Column(modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)) {
            Text("Мой дом",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 21.sp)

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent)
            {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
            HorizontalPager(
                pageCount = pages.size,
                modifier = Modifier,
                state = pagerState,
                pageSpacing = 0.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(0.dp),
                key = null,
                pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(Orientation.Horizontal),
                pageContent = { page ->
                    when (page) {
                        0 -> CameraPageScreen(viewModel.camerasList)
                        1 -> DoorsPageScreen(viewModel.doorsList) { item ->
                            name = item.name
                            door = item
                            openDialog = true
                        }
                    }
                }
            )
        }
        if (openDialog) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
                title = {
                    TextField(value = name, onValueChange = { name = it })
                },
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Button(onClick = {
                            scope.launch {
                                door.name = name
                                viewModel.updateDoor(door)
                            }
                            openDialog = false
                        }) { Text(text = "Сохранить") }
                        Button(onClick = { openDialog = false }) { Text(text = "Отмена") }
                    }
                })
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.Green,
        )
    }
}

@HiltViewModel
class MainScreenVM @Inject constructor(private val cameraRepo:CameraRepository, private val doorsRepo:DoorsRepository):ViewModel() {

    val camerasList = mutableStateListOf<Camera>()
    val doorsList = mutableStateListOf<Door>()


     fun getCameras(loadingStart:() -> Unit, loadingEnd:() -> Unit) {
        cameraRepo.getCameras {
            viewModelScope.launch {
                loadingStart()
                cameraRepo.saveCamerasToDB(it.data.cameras)
                camerasList.clear()
                camerasList.addAll(it.data.cameras)
                loadingEnd()
            }
        }
    }

     suspend fun getCamerasFromDB():List<Camera> {
         camerasList.clear()
         camerasList.addAll(cameraRepo.getCamerasFromDB())
         return  cameraRepo.getCamerasFromDB()
    }

     fun getDoors(loadingStart:() -> Unit, loadingEnd:() -> Unit) {
        doorsRepo.getDoors {
            viewModelScope.launch {
                loadingStart()
                doorsRepo.saveDoorsToDB(it.data)
                doorsList.clear()
                doorsList.addAll(it.data)
                loadingEnd()
            }
        }
    }

    suspend fun getDoorsFromDB():List<Door> {
        doorsList.clear()
        doorsList.addAll(doorsRepo.getDoorsFromDB())
        return  doorsRepo.getDoorsFromDB()
    }
    
    suspend fun updateDoor(item:Door) {
        doorsRepo.updateDoor(item)
    }
}