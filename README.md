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

## Samples:

### Screenshots
<p float="left">
<img src="https://user-images.githubusercontent.com/71257281/125061075-a6f97200-e0b5-11eb-9d09-d5cc9efac538.jpg" width="200">
<img src="https://user-images.githubusercontent.com/71257281/125062494-1ae84a00-e0b7-11eb-8915-c8ea90e28211.jpg" width="200">
<img src="https://user-images.githubusercontent.com/71257281/125061108-ae208000-e0b5-11eb-8cd3-6187a8d1d2e5.jpg" width="200">
 </p>
 
### Videos
 <p float="left">
<img src="https://user-images.githubusercontent.com/71257281/125096585-f6ec2f00-e0dd-11eb-802e-9aa243222f90.gif" width="200">
<img src="https://user-images.githubusercontent.com/71257281/125097762-20598a80-e0df-11eb-80ec-6e506004bec4.gif" width="200">
 </p>

## API:
Calorie tracker uses https://spoonacular.com/food-api API for fetching information about recipes and ingredients
