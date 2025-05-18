package com.newton.book.data.repository

import androidx.sqlite.SQLiteException
import com.newton.book.data.database.FavoriteBookDao
import com.newton.book.data.mappers.toBook
import com.newton.book.data.mappers.toBookEntity
import com.newton.book.data.mappers.toDomainBook
import com.newton.book.data.network.RemoteBookDataSource
import com.newton.book.domain.models.Book
import com.newton.book.domain.repository.BookRepository
import com.newton.core.domain.DataError
import com.newton.core.domain.EmptyResult
import com.newton.core.domain.Result
import com.newton.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val localBookDataSource: FavoriteBookDao
): BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toDomainBook() }
            }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<String?, DataError> {
        val localResult = localBookDataSource.getFavoriteBook(bookWorkId)
        return if (localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookWorkId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return localBookDataSource
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return localBookDataSource
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            localBookDataSource.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFavoriteBook(id: String) {
        localBookDataSource.deleteFavoriteBook(id)
    }
}