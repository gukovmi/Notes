package com.example.notes.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

	@Singleton
	@Provides
	fun provideCicerone(): Cicerone<Router> =
		Cicerone.create()

	@Provides
	fun provideRouter(cicerone: Cicerone<Router>): Router =
		cicerone.router

	@Provides
	fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder =
		cicerone.getNavigatorHolder()
}