package com.newton.book.data.repository

import com.newton.book.data.mappers.toDomainBook
import com.newton.book.data.network.RemoteBookDataSource
import com.newton.book.domain.models.Book
import com.newton.book.domain.repository.BookRepository
import com.newton.core.domain.DataError
import com.newton.core.domain.Result
import com.newton.core.domain.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource
): BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toDomainBook() }
            }
    }
}