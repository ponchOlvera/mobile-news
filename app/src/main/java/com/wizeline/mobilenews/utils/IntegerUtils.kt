package com.wizeline.mobilenews.utils

fun Int.dpAccordingToDensity(scale: Float): Float{
    return (this * scale + 0.5f)
}