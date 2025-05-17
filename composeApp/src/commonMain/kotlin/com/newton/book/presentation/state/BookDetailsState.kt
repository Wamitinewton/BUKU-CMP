package com.newton.book.presentation.state

import com.newton.book.domain.models.Book

data class BookDetailsState(
    val isLoading: Boolean = true,
    val isFavourite: Boolean = false,
    val book: Book? = null,
)
