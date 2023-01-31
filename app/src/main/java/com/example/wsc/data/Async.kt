

package com.example.wsc.data

/**
 * A generic class that holds a loading signal or a [Result].
 */
sealed class Async<out T> {
    object Loading : Async<Nothing>()
    data class Done<out T>(val data: T) : Async<T>()
}
