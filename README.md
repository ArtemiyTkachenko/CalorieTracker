## CalorieTracker

CalorieTracker is a demo application, based on modern Android application tech-stacks and MVVM architecture. The intent behind creating it is to get familiar with the latest kotlin based libraries. The intended function is to find recipes using the https://spoonacular.com/food-api, add them to your calendar and keep track of your calories, macronutrients etc.

## Tech stack:
* Minimum SDK level 21
* Multimodule + MVVM architecture
* [Kotlin based](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous calls.
* [Hilt](https://dagger.dev/hilt/) for dependency injection
* Jetpack   
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for the presentation layer
  * [Room](https://developer.android.com/training/data-storage/room) for persistence
  * [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) for lifecycle related utility
  * [Navigation](https://developer.android.com/guide/navigation) for fragment transitions

* [Ktor](https://ktor.io/) - kotlin based library for networking
* [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines
* [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) - official library for serialization in Kotlin
* [MockK](https://mockk.io/) - mocking library for Kotlin

## API:
Calorie tracker uses https://spoonacular.com/food-api API for fetching information about recipes and ingredients
