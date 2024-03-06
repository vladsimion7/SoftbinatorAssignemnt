package ro.vlad.simion.softbinatior.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepository
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepositoryImpl
import ro.vlad.simion.softbinatior.data.storage.IDataStore
import ro.vlad.simion.softbinatior.data.storage.InMemoryDataStore

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun providePetFinderRepository(
        configurationRepositoryImpl: PetFinderRepositoryImpl
    ): PetFinderRepository

    @Binds
    fun provideInMemoryRepository(
        dataStore: InMemoryDataStore
    ): IDataStore
}