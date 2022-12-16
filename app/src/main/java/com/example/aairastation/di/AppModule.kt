package com.example.aairastation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.aairastation.data.data_source.MenuDatabase
import com.example.aairastation.data.repository.ImageRepositoryImpl
import com.example.aairastation.data.repository.MainRepositoryImpl
import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFoodDatabase(app: Application): MenuDatabase {
        return Room.databaseBuilder(
            app, MenuDatabase::class.java, MenuDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMenuRepository(db: MenuDatabase): MainRepository {
        return MainRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(
        repository: MainRepository,
        imageRepository: ImageRepository,
    ): MenuUseCase {
        return MenuUseCase(
            getAllFood = GetAllFoods(repository, imageRepository),
            getFood = GetFood(repository, imageRepository),
            addFood = AddFood(repository, imageRepository),
        )
    }
}