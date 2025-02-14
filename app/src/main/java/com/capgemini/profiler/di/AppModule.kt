package com.capgemini.profiler.di

import com.capgemini.profiler.data.AuthRepository
import com.capgemini.profiler.data.AuthRepositoryImpl
import com.capgemini.profiler.domain.AuthUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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