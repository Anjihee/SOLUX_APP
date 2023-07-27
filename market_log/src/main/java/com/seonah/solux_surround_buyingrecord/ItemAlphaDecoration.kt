package com.seonah.solux_surround_buyingrecord

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemAlphaDecoration (private val alpha: Float) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val itemCount = parent.childCount
        for (i in 0 until itemCount) {
            val child = parent.getChildAt(i)
            child.alpha = alpha
            child.invalidate()
        }
    }
}