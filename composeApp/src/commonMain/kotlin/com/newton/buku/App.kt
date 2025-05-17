package com.newton.buku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.newton.book.presentation.view.book_list.BookListScreenRoute
import com.newton.book.presentation.viewModel.*
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.compose.viewmodel.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.BookGraph
        ) {
            navigation<Routes.BookGraph>(
                startDestination = Routes.BookList
            ) {
                composable<Routes.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBokViewModel =
                        it.sharedKoinViewModel<BookSharedViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBokViewModel.onSelectBook(null)
                    }

                    BookListScreenRoute(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBokViewModel.onSelectBook(book)
                            navController.navigate(Routes.BookDetail(book.id))
                        }
                    )
                }

                composable<Routes.BookDetail> {
                    val selectedBokViewModel =
                        it.sharedKoinViewModel<BookSharedViewModel>(navController)
                    val selectedBook by selectedBokViewModel.selectedBook.collectAsStateWithLifecycle()
                    Box(
                       modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Book Detail: $selectedBook")
                    }
                }
            }
        }

    }
}


@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}