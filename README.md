[![]()]()
![](https://img.shields.io/badge/minSdkVersion-21-brightgreen.svg)

# SocialOrbitLayout
A Kotlin based adjustable custom view.

![intro](screenshots/intro.gif?raw=true)

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
## TODO
```xml
<me.turkergoksu.lib.view.SocialOrbitLayout
            android:id="@+id/socialOrbitLayout"
            android:layout_width="match_parent"
            android:layout_height="400dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
```


```kotlin
var orbit = Orbit(
            mutableListOf(
                FloatingObject(
                    Color.parseColor("#7409ed"),
                    BitmapFactory.decodeResource(resources, R.drawable.dummy1),
                    size = 70f
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy2),
                    size = 50f
                )
            )
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
|`innerOrbitRadius`|`dimension`|`90dp`|Radius of the inner orbit.|
|`innerOrbitColor`|`color`|`#F8F4FE`|Color of the inner orbit.|
|`innerOrbitWidth`|`dimension`|`40dp`|Width of the inner orbit.|
|`innerOrbitAnimDuration`|`integer`|`30000`|The time that any floating object will make 1 full turn in inner orbit with given milliseconds.|
