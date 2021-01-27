package me.turkergoksu.socialorbitlayout

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orbit = Orbit(mutableListOf())
        for (i in 0..7) {
            val floatingObject = FloatingObject(
                Color.WHITE,
                BitmapFactory.decodeResource(resources, R.drawable.dummy1)
            )
            orbit.floatingObjects?.add(floatingObject)
        }
    }
}