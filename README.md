# Takeaway Task

By [Mohammed Fathy](mailto:dev.mfathy@gmail.com)

## Instructions

1. Download task.zip file.
2. Extract task.zip file.
3. Start Android studio, then open the project, from open dialog.
4. Wait until the project syncs and builds successfully.

## Discussion

I used the following libraries:
*   [AndroidX Library](https://developer.android.com/jetpack/androidx/) - AndroidX is a major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index). Like the Support Library, AndroidX ships separately from the Android OS and provides backwards-compatibility across Android releases. AndroidX fully replaces the Support Library by providing feature parity and new libraries.
*   [Mockito](http://site.mockito.org/) - A mocking framework used to implement unit tests.
*   [Dagger](https://github.com/google/dagger) - for dependency Injection
*   [Gson](https://github.com/google/gson) - a json serialize and deserialize library.
*   [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
*   [Hamcrest](http://hamcrest.org/JavaHamcrest/) -  Junit Matchers
*   [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) - LiveData & ViewModel.
*   [Konveyor](https://github.com/vacxe/Konveyor) - Library for building objects with random parameters.


## Requirements

##### Implementing and visualise a restaurant list with filtering & sorting.
* Task implemented using MVVM architecture with Dagger2, Room, and RxJava.
* The app has the following packages:
** data: It contains all the data accessing and manipulating components.
*** Local: it has all the local database & data store implementation.
*** Remote: it has all the remote data store implementation.
* features: View classes along with their corresponding ViewModel with presentation logic.
* injection: Dependency providing classes using Dagger2.
* interactors: It contains all use cases with their corresponding business logic if exists.


## Testing:
* Task contains more than 80% of unit tests for project classes and 1 Espresso ui test for Restaurants activity.


