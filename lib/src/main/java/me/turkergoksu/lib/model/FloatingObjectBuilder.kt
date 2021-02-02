package me.turkergoksu.lib.model

/**
 * Created by turkergoksu on 02-Feb-21.
 */
interface FloatingObjectBuilder<B, O> {
    var backgroundColor: Int
    var borderWidth: Float
    var size: Float
    var elevation: Float
    var location: FloatingObjectLocation

    fun setBackgroundColor(backgroundColor: Int): B
    fun setBorderWidth(borderWidth: Float): B
    fun setSize(size: Float): B
    fun setElevation(elevation: Float): B
    fun setLocation(location: FloatingObjectLocation): B
    fun build(): O
}