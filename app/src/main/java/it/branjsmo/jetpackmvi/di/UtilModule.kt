package it.branjsmo.jetpackmvi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.branjsmo.jetpackmvi.data.repository.RepositoryImageProviderImpl
import it.branjsmo.jetpackmvi.domain.repository.RepositoryImageProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindImageProvider(
        repositoryImageProvider: RepositoryImageProviderImpl
    ): RepositoryImageProvider
}
