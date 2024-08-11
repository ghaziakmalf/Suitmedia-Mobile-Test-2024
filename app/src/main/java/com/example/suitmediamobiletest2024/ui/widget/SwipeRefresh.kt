package com.example.suitmediamobiletest2024.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SwipeRefresh(
    state: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    @Suppress("DEPRECATION")
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state),
        onRefresh = onRefresh
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
            if (state) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
