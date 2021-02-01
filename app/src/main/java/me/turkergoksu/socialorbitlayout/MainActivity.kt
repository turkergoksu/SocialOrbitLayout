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

        // Example orbit
        val orbit = Orbit.Builder()
                .setFloatingObjectList(
                        mutableListOf(
                                FloatingImage(
                                        Color.parseColor("#2e7fff"),
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy1),
                                        size = 70f,
                                        location = FloatingObjectLocation.CENTER
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy6),
                                        location = FloatingObjectLocation.INNER
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy7),
                                        location = FloatingObjectLocation.INNER
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy5),
                                        location = FloatingObjectLocation.INNER
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy2),
                                        size = 50f
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy3)
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy4),
                                        size = 50f
                                ),
                                FloatingImage(
                                        Color.WHITE,
                                        BitmapFactory.decodeResource(resources, R.drawable.dummy8),
                                        size = 60f
                                )
                        )
                ).build()

        binding.socialOrbitLayout.setOrbit(orbit)
    }
}