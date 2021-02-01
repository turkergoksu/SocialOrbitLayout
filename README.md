[![]()]()
![](https://img.shields.io/badge/minSdkVersion-21-brightgreen.svg)

# SocialOrbitLayout
Adjustable custom view that can be used for loading screens or welcome pages inside social apps to show floating objects around an orbit. Inspired by [Blogging app landing page](https://www.uplabs.com/posts/blog-landing-page-app).

![intro](screenshots/intro.gif?raw=true)

All the example images are generated from [thispersondoesnotexist.com](thispersondoesnotexist.com).

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
```kotlin
var orbit = Orbit.Builder()
        .setFloatingObjectList(
                mutableListOf(
                    // .
                    // .
                    // .
                    FloatingImage(
                        backgroundColor = Color.parseColor("#2e7fff"),
                        bitmap = BitmapFactory.decodeResource(resources, R.drawable.dummy1),
                        size = 70f,
                        location = FloatingObjectLocation.CENTER
                    ),
                    FloatingImage(
                        backgroundColor = Color.WHITE,
                        bitmap = BitmapFactory.decodeResource(resources, R.drawable.dummy2),
                        size = 50f,
                        location = FloatingObjectLocation.INNER
                    ),
                    FloatingImage(
                        bitmap = BitmapFactory.decodeResource(resources, R.drawable.dummy3),
                        location = FloatingObjectLocation.OUTER
                    )
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

⚠️ You may set `Orbit` attributes programmatically but it will be overwritten by values in the layout.

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
⚠️ Currently these attributes are for `FloatingImage`, not for `FloatingObjectView`. That means you cannot use these attributes inside layout files until me or someone else implements it.

| Name | Format | Default | Description |
| ---- | ------ | ------- | ----------- |
|`backgroundColor`|`Int`|`Color.WHITE`|Background color of floating image. In order to observe the color `borderWidth` needs to be bigger than `0f`. |
|`bitmap`|`Bitmap`|`null`|The image that `FloatingObjectView` shows.|
|`borderWidth`|`Float`|`2f`|Border width of `FloatingObjectView`.|
|`size`|`Float`|`40f`|Size of the `FloatingObjectView`(not `bitmap`).|
|`elevation`|`Float`|`10f`|Elevation value of `FloatingObjectView`.|
|`location`|`FloatingObjectLocation`|`FloatingObjectLocation.OUTER`|Location of `FloatingObjectView`. Can take three values named `FloatingObjectLocation.CENTER`, `FloatingObjectLocation.OUTER` and `FloatingObjectLocation.INNER`.|