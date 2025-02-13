package com.capgemini.profiler.di

import com.capgemini.profiler.data.AuthRepository
import com.capgemini.profiler.data.AuthRepositoryImpl
import com.capgemini.profiler.domain.AuthUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase = AuthUseCase(repository)
}