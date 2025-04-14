package com.example.advancedandroidcourse.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * This file is an example of a Hilt module for providing Firebase dependencies.
 * It would be needed when you want to customize Firebase instance creation
 * (e.g., with specific settings) or provide mock instances for testing.
 * In this simple demo, it's not required because FirebaseAuth.getInstance()
 * and FirebaseFirestore.getInstance() already provide singleton instances
 * that Hilt can use directly without additional configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}