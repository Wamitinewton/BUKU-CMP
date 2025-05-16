package com.newton.buku

import androidx.compose.runtime.*
import com.newton.book.presentation.view.BookListScreenRoute
import com.newton.book.presentation.viewModel.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.*

@Composable
@Preview
fun App() {
    BookListScreenRoute(
        viewModel = remember { BookListViewModel()  },
        onBookClick = {}
    )
}