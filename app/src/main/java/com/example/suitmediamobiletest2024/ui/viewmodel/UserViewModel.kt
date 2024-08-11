package com.example.suitmediamobiletest2024.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitmediamobiletest2024.data.model.User
import com.example.suitmediamobiletest2024.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private var currentPage = 1

    fun loadUsers() {
        viewModelScope.launch {
            _isLoadingMore.value = true
            val newUsers = repository.fetchUsers(currentPage, 15)
            _users.value += newUsers
            _isLoadingMore.value = false
            currentPage++
        }
    }

    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _users.value = emptyList()
            currentPage = 1
            loadUsers()
            _isRefreshing.value = false
        }
    }
}
