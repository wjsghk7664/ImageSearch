@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.imagesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imagesearch.presentation.UiState
import com.example.imagesearch.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(
    viewModel: SearchViewModel = hiltViewModel(),
){
    val state by viewModel.results.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val tabs = listOf("검색 결과", "내 보관함")
    var curPage by remember{ mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            TabRow(selectedTabIndex = curPage, indicator = {tabPositions->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[curPage])
                        .fillMaxWidth()
                        .align(Alignment.TopEnd)
                        .height(4.dp)
                )
            }) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = curPage == index,
                        onClick = {
                            curPage = index
                            navController.navigate(title){
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop=true
                            }

                        }
                    )
                }
            }
        },
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = tabs[curPage], modifier = Modifier.padding(paddingValues)) {
                composable("검색 결과"){ SearchScreen(state)}
                composable("내 보관함"){ StorageScreen(state)}
            }
        }
    )
}


@Composable
fun SearchScreen(state: UiState){
    Text("Page1")
}

@Composable
fun StorageScreen(state: UiState){
    Text("Page2")
}