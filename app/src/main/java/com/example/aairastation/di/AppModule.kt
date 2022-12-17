package com.example.aairastation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.aairastation.data.data_source.MainDatabase
import com.example.aairastation.data.repository.ImageRepositoryImpl
import com.example.aairastation.data.repository.MainRepositoryImpl
import com.example.aairastation.domain.ImageRepository
import com.example.aairastation.domain.MainRepository
import com.example.aairastation.feature_menu.domain.MenuUseCases
import com.example.aairastation.feature_menu.domain.use_case.*
import com.example.aairastation.feature_order.domain.use_case.OrderUseCases
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
    fun provideFoodDatabase(app: Application): MainDatabase {
        return Room.databaseBuilder(
            app, MainDatabase::class.java, MainDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMenuRepository(db: MainDatabase): MainRepository {
        return MainRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMenuUseCases(
        repository: MainRepository,
        imageRepository: ImageRepository,
    ): MenuUseCases {
        return MenuUseCases.create(repository, imageRepository)
    }

    @Provides
    @Singleton
    fun provideOrderUseCases(
        repository: MainRepository,
    ): OrderUseCases {
        return OrderUseCases.create(repository)
    }
}