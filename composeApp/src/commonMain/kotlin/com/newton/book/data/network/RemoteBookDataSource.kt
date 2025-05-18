package com.newton.book.data.network

import com.newton.book.data.dto.BookWorkDto
import com.newton.book.data.dto.SearchResponseDto
import com.newton.core.domain.DataError
import com.newton.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}