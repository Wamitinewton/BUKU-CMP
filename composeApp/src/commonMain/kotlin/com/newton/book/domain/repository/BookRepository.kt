package com.newton.book.domain.repository

import com.newton.book.domain.models.*
import com.newton.core.domain.*
import kotlinx.coroutines.flow.*

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDetails(bookWorkId: String): Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFavoriteBook(id: String)
}