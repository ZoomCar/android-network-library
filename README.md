[![GitHub version](https://badge.fury.io/gh/ZoomCar%2Fandroid-network-library.svg)](https://badge.fury.io/gh/ZoomCar%2Fandroid-network-library)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/Apache-2.0)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/ZoomCar/android-network-library/blob/master/LICENSE)

# android-network-library
This is a network client written on top of retrofit to perform network call just by plug n play this
library.

* This library can perform all the rest api calls (GET, POST, PUT, PATCH, DELETE)
* It provides custom protocols if user needs to perform any analytics event for failure/success
* It provides the option to turn on debug logs

### Getting started

#### Using ZC-Network-Client in your application
* Add the JitPack repository to your project level build file.
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
* Add the library dependency
```
implementation 'com.github.ZoomCar:android-network-library:1.0.5'
```

* Add third party dependencies, [Chuck Http Interceptor](https://github.com/jgilfelt/chuck) for logging, 
[GSON](https://github.com/google/gson) or [LoganSquare](https://github.com/bluelinelabs/LoganSquare) for parsing
```
// Chuck
debugImplementation 'com.readystatesoftware.chuck:library:1.1.0'
releaseImplementation 'com.readystatesoftware.chuck:library-no-op:1.1.0'

// Gson
implementation 'com.google.code.gson:gson:2.8.5'

// LoganSquare
implementation 'com.bluelinelabs:logansquare:1.3.7'
kapt 'com.bluelinelabs:logansquare-compiler:1.3.7'

// Jackson core
implementation 'com.fasterxml.jackson.core:jackson-core:2.9.9'
```

#### Initialization

* Initialize the repository in Application class as follows

```kotlin
ZcNetworkManager.builder(this)
    .setDebugLog(true)
    .setNetworkAnalyticsListener(this)
    .addBaseUrl(ApiConstant.BASE_URL)
    .build()
```

#### Making Rest API Call
```kotlin
val zcNetworkBuilder: ZcNetworkBuilder = ZcNetworkBuilder()
    .setActivity(this)
    .setBodyParams(hashMapOf("title" to "foo", "body" to "bar", "userId" to 1))
    .setRequestType(ZcRequestType.GET)
    .setHeaderParams(hashMapOf("Accept" to "application/json", "key" to "value"))
    .setUrl("https://jsonplaceholder.typicode.com/users/1")
    .setListener(object : ZcNetworkListener {
        override fun onSuccess(
            response: JsonElement?,
            requestCode: Int
        ) {
            val user = LoganSquare.parse(response?.asJsonObject.toString(), User::class.java)
            Log.d(LibTag.TAG, "onSuccess: ${user.name}")
        }

        override fun onError(error: NetworkError) {
            Log.d(LibTag.TAG, "onError: ${error.httpCode}")
        }
    })
zcNetworkBuilder.request()
```

#### Parsing

* Any parsing library can be used to parse the jsonString response [GSON](https://github.com/google/gson) 
or [LoganSquare](https://github.com/bluelinelabs/LoganSquare)
* Below examples are parsed using LoganSquare

##### For JsonObject
```kotlin
override fun onSuccess(
    response: JsonElement?,
    requestCode: Int
){
    val user = LoganSquare.parse(response?.asJsonObject.toString(), User::class.java)
}
```

##### For JsonArray
```kotlin
override fun onSuccess(
    response: JsonElement?,
    requestCode: Int
){
    val listOfUser = LoganSquare.parseList(response?.asJsonArray.toString(), User::class.java)
}
```

### Find this project useful ? :heart:

* Support it by clicking the :star: button on the upper right of this page. :v:

## License

```
   Copyright (C) 2020 Zoomcar India Pvt Ltd [Zoomcar](https://www.zoomcar.com)
   Copyright (C) 2011 Android Open Source Project

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