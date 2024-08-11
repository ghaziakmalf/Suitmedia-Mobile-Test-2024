package com.example.suitmediamobiletest2024.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.suitmediamobiletest2024.R
import com.example.suitmediamobiletest2024.data.model.User
import com.example.suitmediamobiletest2024.ui.theme.SuitmediaMobileTest2024Theme
import com.example.suitmediamobiletest2024.ui.viewmodel.UserViewModel
import com.example.suitmediamobiletest2024.ui.widget.SwipeRefresh

@Preview(showBackground = true)
@Composable
fun ThirdScreenPreview() {
    val navController = rememberNavController()
    SuitmediaMobileTest2024Theme {
        ThirdScreen(navController) {}
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserListItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = user.avatar,
            contentDescription = "User avatar",
            loading = placeholder(R.drawable.ic_photo_default),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("${user.firstName} ${user.lastName}", style = MaterialTheme.typography.displayMedium)
            Text(user.email, style = MaterialTheme.typography.displaySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(navController: NavController, onUserSelected: (String) -> Unit) {
    val userViewModel: UserViewModel = viewModel()
    val users by userViewModel.users.collectAsState()
    val isRefreshing by userViewModel.isRefreshing.collectAsState()
    val isLoading by userViewModel.isLoadingMore.collectAsState()

    val listState = rememberLazyListState()

    // Load more users when scrolled to the end
    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == users.size - 1) {
            userViewModel.loadUsers()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Third Screen",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.padding(start = 16.dp, end = 48.dp, top = 16.dp)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    // Draw a line from very left to very right
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 4f
                    )
                }
                SwipeRefresh(
                    state = isRefreshing,
                    onRefresh = { userViewModel.refreshUsers() }
                ) {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        items(users) { user ->
                            UserListItem(user = user) {
                                onUserSelected("${user.firstName} ${user.lastName}")
                                navController.popBackStack()
                            }
                        }
                        if (isLoading) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        userViewModel.loadUsers()
    }
}