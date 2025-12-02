package com.example.gunplogs.ui.shapes

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.sourceInformationMarkerEnd

fun kitBox() : GenericShape =
    GenericShape { size, _ ->
        moveTo(100f, 0f)
        lineTo(size.width - 100f, 0f)
        lineTo(size.width, 100f)
        lineTo(size.width, size.height - 100f)
        lineTo(size.width - 100f, size.height)
        lineTo(0f, size.height)
        lineTo(0f, 100f)
        lineTo(100f, 0f)
        close()
}

fun menuBoxLeft() : GenericShape =
    GenericShape { size, _ ->
        lineTo(size.width - size.width*0.1f, 0f)
        lineTo(size.width, size.height*0.1f)
        lineTo(size.width, size.height)
        lineTo(size.width*0.1f, size.height)
        lineTo(0f, size.height*0.9f)
        lineTo(0f, 0f)
        close()
}

fun menuBoxRight() : GenericShape =
    GenericShape { size, _ ->
        moveTo(size.width*0.1f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height*0.9f)
        lineTo(size.width*0.9f, size.height)
        lineTo(0f, size.height)
        lineTo(0f, size.height*0.1f)
        lineTo(size.width*0.1f, 0f)
        close()
    }

fun hexShape() : GenericShape =
    GenericShape { size, _ ->
        moveTo(size.width*0.33f, 0f)
        lineTo(size.width*0.66f, 0f)
        lineTo(size.width, size.height*0.33f)
        lineTo(size.width, size.height*0.66f)
        lineTo(size.width*0.66f, size.height)
        lineTo(size.width*0.33f, size.height)
        lineTo(0f, size.height*0.66f)
        lineTo(0f, size.height*0.33f)
        lineTo(size.width*0.33f, 0f)
        close()
    }
