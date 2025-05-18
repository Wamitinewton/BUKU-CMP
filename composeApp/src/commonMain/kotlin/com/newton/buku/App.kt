package com.newton.buku

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation
import com.newton.book.presentation.intent.*
import com.newton.book.presentation.view.book_details.*
import com.newton.book.presentation.view.book_list.*
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
                composable<Routes.BookList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
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

                composable<Routes.BookDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val selectedBokViewModel =
                        it.sharedKoinViewModel<BookSharedViewModel>(navController)
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBook by selectedBokViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                        }
                    }

                    BookDetailScreenRoute(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }

    }
}


@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: throw IllegalStateException("No parent navigation graph found")
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}