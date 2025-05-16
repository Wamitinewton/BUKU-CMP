package com.newton.book.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.newton.book.presentation.intent.BookListAction
import com.newton.book.presentation.state.BookListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel: ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClick -> {}
            is BookListAction.OnFavouriteClick -> {}
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabChange -> {
                _state.update {
                    it.copy(selectedTab = action.tab)
                }
            }
        }
    }
}