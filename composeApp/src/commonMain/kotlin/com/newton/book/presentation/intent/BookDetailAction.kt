package com.newton.book.presentation.intent

import com.newton.book.domain.models.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavouriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}