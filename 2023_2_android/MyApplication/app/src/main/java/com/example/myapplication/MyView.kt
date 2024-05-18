package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyView : View {
    private var shapeType = ShapeType.Rect
    private var x = 0f
    private var y = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs : AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        val paint = Paint()

        when(shapeType){
            ShapeType.Rect -> canvas.drawRect(x, y, x+100f, y+100f, paint)
            ShapeType.Circle -> canvas.drawCircle(x+50f, y+50f, 50f, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.x
                y = event.y
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setShapeType(type : ShapeType){
        shapeType = type
        invalidate()
    }
}

enum class ShapeType{
    Rect, Circle
}