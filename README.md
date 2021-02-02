[![](https://jitpack.io/v/turkergoksu/SocialOrbitLayout.svg)](https://jitpack.io/#turkergoksu/SocialOrbitLayout)
![](https://img.shields.io/badge/minSdkVersion-21-brightgreen.svg)

# SocialOrbitLayout
Adjustable custom view that can be used for loading screens or welcome pages inside social apps to show floating objects around an orbit. Inspired by [Blogging app landing page](https://www.uplabs.com/posts/blog-landing-page-app).

![intro](screenshots/intro.gif?raw=true)

All the example images are generated from [thispersondoesnotexist.com]()

## 🛠️Setup
#### 1. Add the JitPack repository to your build file.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.turkergoksu:SocialOrbitLayout:1.0.0'
}
```


## 🕹️Usage
```xml
<me.turkergoksu.lib.view.SocialOrbitLayout
            android:id="@+id/socialOrbitLayout"
            android:layout_width="match_parent"
            android:layout_height="400dp"
            app:innerOrbitAngleDistance="120"
            app:innerOrbitAnimDuration="30000"
            app:innerOrbitColor="#e0ecff"
            app:innerOrbitRadius="90dp"
            app:innerOrbitStartAngle="20"
            app:innerOrbitWidth="40dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:outerOrbitAngleDistance="90"
            app:outerOrbitAnimDuration="60000"
            app:outerOrbitColor="#d3d3d3"
            app:outerOrbitPadding="40dp"
            app:outerOrbitStartAngle="70"
            app:outerOrbitWidth="2dp" />
```

Currently, you can programmatically add `FloatingImage` to `Orbit` with given `Bitmap`.

⚠️ You may set `Orbit` attributes programmatically but it will be overwritten by the values in the layout. 
```kotlin
var orbit = Orbit.Builder()
        .setFloatingObjectList(
                mutableListOf(
                    // .
                    // .
                    // .
                    FloatingImage.Builder()
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy1))
                        .setBackgroundColor(Color.parseColor("#2e7fff"))
                        .setSize(70f)
                        .setLocation(FloatingObjectLocation.CENTER)
                        .build(),
                    FloatingImage.Builder()
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy2))
                        .setSize(50f)
                        .setLocation(FloatingObjectLocation.INNER)
                        .build(),
                    FloatingImage.Builder()
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy3))
                        .setLocation(FloatingObjectLocation.OUTER)
                        .build(),
                    // .
                    // .
                    // .
                )
        )
        .setOuterOrbitWidth(width = 2f)
        .setInnerOrbitColor(Color.parseColor("#2e7fff"))
        .setOuterOrbitAngleDistance(distance = 90.0)
        .setOuterOrbitAnimDuration(duration = 60000)
        .setInnerOrbitRadius(radius = 90f)
        .setInnerOrbitAngleDistance(distance = 120.0)
        .setInnerOrbitAnimDuration(duration = 30000)
        .build()
socialOrbitLayout.setOrbit(orbit)
```


If you don't want to create a `FloatingImageBuilder` for every `FloatingImage` object, you may create a single builder and change it's attributes. This implementation actually better for objects that shares same attributes(Or the majority). Check the following example.
```kotlin
val floatingImageBuilder = FloatingImage.Builder()
                .setBackgroundColor(Color.RED)
                .setBorderWidth(3f)
                .setSize(50f)
                
var orbit = Orbit.Builder()
        .setFloatingObjectList(
                mutableListOf(
                    // .
                    // .
                    // .
                    floatingImageBuilder.copy() // copy() is crucial here!
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy1))
                        .setBackgroundColor(Color.WHITE)
                        .setSize(70f)
                        .setLocation(FloatingObjectLocation.CENTER)
                        .build(),
                    floatingImageBuilder.copy()
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy2))
                        .setLocation(FloatingObjectLocation.INNER)
                        .build(),
                    floatingImageBuilder.copy()
                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy3))
                        .setBorderWidth(2f)
                        .build(),
                    // .
                    // .
                    // .
                )
        )
        .build()
socialOrbitLayout.setOrbit(orbit)

```

## 📝Attributes
### SocialOrbitLayout
| Name | Format | Default | Description |
| ---- | ------ | ------- | ----------- |
|`outerOrbitColor`|`color`|`Color.LTGRAY`|Color of the outer orbit.|
|`outerOrbitWidth`|`dimension`|`2dp`|Width of the outer orbit.|
|`outerOrbitAnimDuration`|`integer`|`60000`|The time that any floating object will make 1 full turn in outer orbit with given milliseconds.|
|`outerOrbitPadding`|`dimension`|`40dp`|The padding between outer orbit and whole view.|
|`outerOrbitStartAngle`|`integer`|`70`|The start angle of first floating object on outer orbit.|
|`outerOrbitAngleDistance`|`integer`|`90`|The angle distance between floating objects on outer orbit.|
|`innerOrbitRadius`|`dimension`|`90dp`|Radius of the inner orbit.|
|`innerOrbitColor`|`color`|`#F8F4FE`|Color of the inner orbit.|
|`innerOrbitWidth`|`dimension`|`40dp`|Width of the inner orbit.|
|`innerOrbitAnimDuration`|`integer`|`30000`|The time that any floating object will make 1 full turn in inner orbit with given milliseconds.|
|`innerOrbitStartAngle`|`integer`|`20`|The start angle of first floating object on inner orbit.|
|`innerOrbitAngleDistance`|`integer`|`120`|The angle distance between floating objects on inner orbit.|

### FloatingImage
⚠️ To avoid any future confusion after seeing `SocialOrbitLayout`, the following attributes are for the `FloatingImage` object and not for the layout file. Because currently there isn't any `FloatingObjectView` attribute implementation.

| Name | Format | Default | Description |
| ---- | ------ | ------- | ----------- |
|`bitmap`|`Bitmap`|`null`|The image that `FloatingObjectView` shows.|
|`backgroundColor`|`Int`|`Color.WHITE`|Background color of floating image. In order to observe the color `borderWidth` needs to be bigger than `0f`. |
|`borderWidth`|`Float`|`2f`|Border width of `FloatingObjectView`.|
|`size`|`Float`|`40f`|Size of the `FloatingObjectView`(not `bitmap`).|
|`elevation`|`Float`|`10f`|Elevation value of `FloatingObjectView`.|
|`location`|`FloatingObjectLocation`|`FloatingObjectLocation.OUTER`|Location of `FloatingObjectView`. Can take three values named `FloatingObjectLocation.CENTER`, `FloatingObjectLocation.OUTER` and `FloatingObjectLocation.INNER`.|