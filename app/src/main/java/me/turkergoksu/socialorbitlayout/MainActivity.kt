package me.turkergoksu.socialorbitlayout

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import me.turkergoksu.socialorbitlayout.databinding.ActivityMainBinding
import me.turkergoksu.socialorbitlayout.model.FloatingObject
import me.turkergoksu.socialorbitlayout.model.Orbit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Example orbit
        val orbit = Orbit(
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
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy3)
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy4),
                    size = 50f
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy8),
                    size = 60f
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy6)
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy7)
                ),
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy5)
                )
            )
        )

        binding.socialOrbitLayout.setOrbit(orbit)
    }
}