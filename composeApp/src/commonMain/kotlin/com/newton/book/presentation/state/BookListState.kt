package com.newton.book.presentation.state

import com.newton.book.domain.models.Book
import com.newton.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favouriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTab: Int = 0,
    val errorMessage: UiText? = null
)


 val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "http://fastly.picsum.photos/id/3/5000/3333.jpg?hmac=GDjZ2uNWE3V59PkdDaOzTOuV3tPWWxJSf4fNcxu4S2g",
        authors = listOf("Newton", "Wamiti", "Android", "Developer"),
        description = "Random description",
        languages = listOf("Newton", "Wamiti", "Android", "Developer"),
        firstPublishYear = "2021",
        averageRating = 3.4,
        ratingCount = 40,
        numPages = 100,
        numEditions = 20
    )
}