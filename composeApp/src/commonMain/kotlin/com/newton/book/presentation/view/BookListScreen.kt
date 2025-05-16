package com.newton.book.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import buku_app.composeapp.generated.resources.Res
import buku_app.composeapp.generated.resources.favorites
import buku_app.composeapp.generated.resources.no_favorites
import buku_app.composeapp.generated.resources.no_search_results
import buku_app.composeapp.generated.resources.search_results
import com.newton.book.presentation.intent.*
import com.newton.book.presentation.state.*
import com.newton.book.presentation.view.components.BookList
import com.newton.book.presentation.view.components.BookSearchBar
import com.newton.core.presentation.DarkBlue
import com.newton.core.presentation.DesertWhite
import com.newton.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource


@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val searchBooksScrollState = rememberLazyListState()
    val favoritesScrollState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchBooksScrollState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTab) {
        pagerState.animateScrollToPage(state.selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
            onAction(BookListAction.OnTabChange(pagerState.currentPage))
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange ={
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTab,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = SandYellow,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTab])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTab == 0,
                        onClick = {
                            onAction(BookListAction.OnTabChange(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = state.selectedTab == 1,
                        onClick = {
                            onAction(BookListAction.OnTabChange(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex){
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage !=null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        else -> {
                                            BookList(
                                                modifier = Modifier.fillMaxSize(),
                                                books = state.searchResults,
                                                onBookClick = {
                                                    onAction(BookListAction.OnBookClick(it))
                                                },
                                                scrollState = searchBooksScrollState
                                            )

                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favouriteBooks.isEmpty()){
                                    Text(
                                        text = stringResource(Res.string.no_favorites),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                } else {
                                    BookList(
                                        modifier = Modifier.fillMaxSize(),
                                        books = state.favouriteBooks,
                                        onBookClick = {
                                            onAction(BookListAction.OnBookClick(it))
                                        },
                                        scrollState = favoritesScrollState
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}