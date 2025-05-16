package com.newton.book.presentation.intent

import com.newton.book.domain.models.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String): BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class OnFavouriteClick(val book: Book): BookListAction
    data class OnTabChange(val tab: Int): BookListAction
}