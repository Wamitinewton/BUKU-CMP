package com.newton.book.domain.repository

import com.newton.book.domain.models.Book
import com.newton.core.domain.DataError
import com.newton.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDetails(bookWorkId: String): Result<String?, DataError>
}