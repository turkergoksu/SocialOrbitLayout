package me.turkergoksu.socialorbitlayout

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import me.turkergoksu.socialorbitlayout.databinding.ActivityMainBinding
import me.turkergoksu.lib.model.FloatingImage
import me.turkergoksu.lib.model.FloatingObjectLocation
import me.turkergoksu.lib.model.Orbit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding =
                DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Example - 1
        val orbit1 = Orbit.Builder()
                .setFloatingObjectList(
                        mutableListOf(
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy1))
                                        .setBackgroundColor(Color.parseColor("#7919f7"))
                                        .setSize(70f)
                                        .setLocation(FloatingObjectLocation.CENTER)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy6))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy7))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy5))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy2))
                                        .setSize(50f)
                                        .setLocation(FloatingObjectLocation.OUTER)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy3))
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy4))
                                        .setSize(50f)
                                        .build(),
                                FloatingImage.Builder()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy8))
                                        .setSize(60f)
                                        .build(),
                        )
                ).build()

        // Example - 2
        val floatingImageBuilder = FloatingImage.Builder()
                .setBackgroundColor(Color.RED)
                .setBorderWidth(3f)
                .setSize(50f)

        val orbit2 = Orbit.Builder()
                .setFloatingObjectList(
                        mutableListOf(
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy1))
                                        .setBackgroundColor(Color.parseColor("#2e7fff"))
                                        .setSize(70f)
                                        .setLocation(FloatingObjectLocation.CENTER)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy6))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy7))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy5))
                                        .setLocation(FloatingObjectLocation.INNER)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy2))
                                        .setSize(50f)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy3))
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy4))
                                        .setSize(50f)
                                        .build(),
                                floatingImageBuilder.copy()
                                        .setBitmap(BitmapFactory.decodeResource(resources, R.drawable.dummy8))
                                        .setSize(60f)
                                        .build(),
                        )
                ).build()

        binding.socialOrbitLayout.setOrbit(orbit1)
//        binding.socialOrbitLayout.setOrbit(orbit2)
    }
}