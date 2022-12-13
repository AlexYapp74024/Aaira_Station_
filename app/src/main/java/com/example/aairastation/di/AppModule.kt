package com.example.aairastation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.aairastation.feature_menu.data.data_source.MenuDatabase
import com.example.aairastation.feature_menu.data.repository.MenuRepositoryImpl
import com.example.aairastation.feature_menu.domain.repository.MenuRepository
import com.example.aairastation.feature_menu.domain.use_case.MenuUseCase
import com.example.forage.core.image_processing.ImageRepository
import com.example.forage.core.image_processing.ImageRepositoryImpl
import com.example.aairastation.feature_menu.domain.use_case.AddFood
import com.example.aairastation.feature_menu.domain.use_case.DeleteFood
import com.example.aairastation.feature_menu.domain.use_case.GetAllFoods
import com.example.aairastation.feature_menu.domain.use_case.GetFood
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
    fun provideMenuRepository(db: MenuDatabase): MenuRepository {
        return MenuRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(
        repository: MenuRepository,
        imageRepository: ImageRepository,
    ): MenuUseCase {
        return MenuUseCase(
            getAllFood = GetAllFoods(repository, imageRepository),
            getFood = GetFood(repository, imageRepository),
            deleteFood = DeleteFood(repository),
            addFood = AddFood(repository, imageRepository),
        )
    }
}