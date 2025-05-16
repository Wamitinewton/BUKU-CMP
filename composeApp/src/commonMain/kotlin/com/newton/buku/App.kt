package com.newton.buku

import androidx.compose.runtime.*
import com.newton.book.data.network.*
import com.newton.book.data.repository.*
import com.newton.book.presentation.view.*
import com.newton.book.presentation.viewModel.*
import com.newton.core.data.*
import io.ktor.client.engine.*
import org.jetbrains.compose.ui.tooling.preview.*

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    BookListScreenRoute(
        viewModel = remember {
            BookListViewModel(
                repository = BookRepositoryImpl(
                    remoteBookDataSource = KtorRemoteBookDataSource(
                        httpClient = HttpClientFactory().create(engine)
                    )
                )
            )
        },
        onBookClick = {}
    )
}