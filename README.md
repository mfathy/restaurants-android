# Task 2

By [Mohammed Fathy](mailto:dev.mfathy@gmail.com)

## Instructions

1. Navigate to [repo](https://github.com/xing/test_android_binge-mfathy)
2. Clone locally using
        `git clone https://github.com/xing/test_android_binge-mfathy.git`
3. Start Android studio, then open the project, from open dialog.
4. Wait until the project syncs and builds successfully.
5. Start you local server.
6. Run the project using the Android studio on you Android emulator or device.
7. Please change server IP address to you machine IP if you will test the app on a device in 
`gradle.properties` & 'res/xml/network_security_config.xml'


## Discussion

I used the following libraries:
*   [AndroidX Library](https://developer.android.com/jetpack/androidx/) - AndroidX is a major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index). Like the Support Library, AndroidX ships separately from the Android OS and provides backwards-compatibility across Android releases. AndroidX fully replaces the Support Library by providing feature parity and new libraries.
*   [Mockito](http://site.mockito.org/) - A mocking framework used to implement unit tests.
*   [Dagger](https://github.com/google/dagger) - for dependency Injection
*   [Gson](https://github.com/google/gson) - a json serialize and deserialize library.
*   [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM. 
*   [Okhttp](http://square.github.io/okhttp/) - An HTTP+HTTP/2 client for Android and Java applications.
*   [Hamcrest](http://hamcrest.org/JavaHamcrest/) -  Junit Matchers
*   [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
*   [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) - LiveData & ViewModel.
*   [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling.


## Requirements

##### Implementing adding/removing movies as favorites and a new favorites screen that shows users a list of all their favorites.
* feature.Base package has common implementation of bookmark/un-bookmark operations
in BaseViewModel.
* SearchViewModel preserve search result and list state.
* BookmarkObserver has common implementation for attached view model.

