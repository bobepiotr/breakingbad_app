package com.example.bb_app.custom_views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView


class ProportionalImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable

        if (d != null) {
            val h = MeasureSpec.getSize(heightMeasureSpec)
            val w = h * d.intrinsicHeight / d.intrinsicWidth
            setMeasuredDimension(w, h)
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}