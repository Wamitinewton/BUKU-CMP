package com.newton.di

import com.newton.book.data.network.KtorRemoteBookDataSource
import com.newton.book.data.network.RemoteBookDataSource
import com.newton.book.data.repository.BookRepositoryImpl
import com.newton.book.domain.repository.BookRepository
import com.newton.book.presentation.viewModel.BookDetailViewModel
import com.newton.book.presentation.viewModel.BookListViewModel
import com.newton.book.presentation.viewModel.BookSharedViewModel
import com.newton.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory().create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookSharedViewModel)
    viewModelOf(::BookDetailViewModel)
}