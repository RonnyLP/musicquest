package com.example.melodyquest.di

import com.example.melodyquest.data.auth.FirebaseAuthRepository
import com.example.melodyquest.domain.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object AuthModule {
//
//
//    @Provides
//    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
//
//    @Provides fun provideAuthRepository(
//        firebaseAuth: FirebaseAuth
//    ): AuthRepository = FirebaseAuthRepository(firebaseAuth)
//
//}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return FirebaseAuthRepository(firebaseAuth)
    }
}