package com.capgemini.profiler.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capgemini.profiler.data.AuthRepository
import com.capgemini.profiler.data.AuthRepositoryImpl
import com.capgemini.profiler.data.UserPreferences
import com.capgemini.profiler.domain.AuthUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): androidx.datastore.core.DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: androidx.datastore.core.DataStore<Preferences>): UserPreferences {
        return UserPreferences(dataStore)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth,store:FirebaseFirestore): AuthRepository = AuthRepositoryImpl(auth,store)

    @Provides
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase = AuthUseCase(repository)
}