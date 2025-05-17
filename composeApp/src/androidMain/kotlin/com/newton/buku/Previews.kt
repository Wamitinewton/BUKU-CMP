package com.newton.buku

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import com.newton.book.presentation.state.*
import com.newton.book.presentation.view.book_list.BookListScreen
import com.newton.book.presentation.view.book_list.components.BookSearchBar

@Preview(backgroundColor = 0xFFF)
@Composable
fun BookSearchBarPreview() {
    MaterialTheme {
        BookSearchBar(
            searchQuery = "Kotlin",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}


@Preview
@Composable
fun BookListScreenPreview(modifier: Modifier = Modifier) {
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}

    )
}