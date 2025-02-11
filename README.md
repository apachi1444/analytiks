# <p align="center"> 📊Analytiks</p>
<p align="center">
  <a href="https://github.com/aminekarimii/analytiks/actions"><img alt="Build Status" src="https://github.com/aminekarimii/analytiks/workflows/Android%20CI/badge.svg"/></a>
</p>  

![Group 54 (1)](https://user-images.githubusercontent.com/20410115/228402805-3309d17a-0bc5-4404-90f8-20c9b30e33a9.png)

## Description

An android library that centralizes analytics services in one place can be a useful tool for
developers who want to track the usage and performance of their app.
Should be easy as it sounds, a single implementation to start with the base analytics core features,
and then you can add each analytic service separately (to preserve library size).
A debug analytics mode that can log the same properties in the debug console.

![Scheme of the library logic](https://user-images.githubusercontent.com/20410115/225161402-d3a7d24f-da0d-4360-abab-fe86c68f0214.png)

## 📥 Download
[![Release](https://img.shields.io/maven-central/v/io.github.aminekarimii/analytiks)](https://img.shields.io/maven-central/v/io.github.aminekarimii/analytiks)

In your app level ```build.gradle``` file, add:

```gradle
dependencies {
    implementation 'io.github.aminekarimii:analytiks:VERSION'
    implementation 'io.github.aminekarimii:analytiks-core:VERSION'

    // You can add each Addon separately as following:
    implementation 'io.github.aminekarimii:analytiks-addon-googleanalytics:VERSION'
    implementation 'io.github.aminekarimii:analytiks-addon-mixpanel:VERSION'
    implementation 'io.github.aminekarimii:analytiks-addon-timber:VERSION'
    implementation 'io.github.aminekarimii:analytiks-addon-segment:VERSION'
}
```

## 🔌 Setup

1- In your activity, initiate the Analytiks library and keep an object to be used after as
following:

```kotlin
private lateinit var analytiks: Analytiks

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // ...
    val clients = listOf(
        TimberLocalClient(),
        MixpanelAnalyticsClient(token = "YOUR_TOKEN")
        // Your addons
    )

    analytiks = Analytiks(clients)
}
```

2- Initialize the addons

```kotlin
analytiks.initialize(this.applicationContext)
```

3- You're good to go!

```kotlin
analytiks.logEvent("your_event_name")
analytiks.pushAll()
```

## Features [🚧 wip]

The list of features provided by the library

- **Initialization:** `init` Initialize the "analytiks" library, along with its sub-libraries,
  during the initialization process.
- **Log event:** `event` send/save an event with a name and an optional set of properties.
- **Identify user:** `identify` Identify the current user by the given id or a random uuid in case
  of an empty one.
- **Set user property:** `setUserProperty` Sets a key value property to the identified user.
- **Reset:** `reset` the plugins and remove the default users configuration.
- **Flush events** `flush` send the recorded local data to the service servers on call.

## 🗃 Supported analytics SDKs
Here's a list of the most known analytics services that we will support in our library.  
| Service   |     Status    | Implementation | Official documentation |
| --------- | ------------- | -------------- | ------------ |
| Google/Firebase Analytics  | ✅  | <a href="./addon/analytiks-googleanalytics/README.md">Firebase/Google Addon doc</a>  | <a href="https://firebase.google.com/docs/analytics/get-started?platform=android">Firebase Analytics doc</a> |
| Segment  | ✅  | <a href="./addon/analytiks-segment/README.md">Segment Addon doc</a> | <a href="https://segment.com/docs/connections/sources/catalog/libraries/mobile/kotlin-android/" >Segment doc</a> |
| Mixpanel  | ✅  | <a href="./addon/analytiks-mixpanel/README.md">Mixpanel Addon doc</a>| <a href="https://developer.mixpanel.com/docs/android">Mixpanel doc</a> |
| Flurry Analytics  | 🚧  | - | - |
| Amplitude  | ✅ | <a href="./addon/analytiks-amplitude/README.md">Amplitude Addon doc</a> | <a href="https://www.docs.developers.amplitude.com/data/sdks/sdk-quickstart/">Amplitude doc</a> |
| App Annie  | 🚧  | - | - |
| Localytics  | 🚧  | - | - |
| AppsFlyer  | 🚧  | - | - |
| App Center Analytics  | 🚧  | - | - |
| Onesignal  | 🚧  | - | - |
| Timber - For local event logging  | ✅  | - | <a href="https://github.com/JakeWharton/timber">github/JakeWharton/timber</a> | 
| Your Custom Addon  | ✅  | - | <a href="./analytiks-core">Instructions</a> |

➕ Can't find your service? [open an issue](https://github.com/aminekarimii/analytiks/issues/new) with the name and the direct documentation link in the comment section.

## 📫 Contact Information

For any questions, suggestions, or discussions regarding the "Analytiks" library, feel free to reach out through the following channels:

- **Email:** [aminekarimi1998@gmail.com](mailto:aminekarimi1998@gmail.com)
- **LinkedIn:** [aminekarimi](https://www.linkedin.com/in/aminekarimi)
- **Twitter:** [@aminekarimii](https://twitter.com/aminekarimii)
- **GitHub:** [aminekarimii](https://github.com/aminekarimii)

## License 🔖

```
    Apache 2.0 License

    Copyright 2022 KARIMI Amine

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

```
