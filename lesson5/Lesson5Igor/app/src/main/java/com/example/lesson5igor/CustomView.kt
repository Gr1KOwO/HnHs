package com.example.lesson5igor

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class  CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val columnHeights = mutableListOf<Float>()
    private val dateLabels = mutableListOf<String>()

    private var lineColor = ContextCompat.getColor(context, R.color.black)
    private var dateColor =  ContextCompat.getColor(context, R.color.black)
    private var width = resources.getDimensionPixelSize(R.dimen.custom_view_width)
    private var height = resources.getDimensionPixelSize(R.dimen.custom_view_height)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 4f
    }
    private val textDatePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 24f
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 24f
    }

    private val columnWidth = 15f
    private val maxColumnHeight = 100f

    private val simpleDateFormat = SimpleDateFormat("MM.dd", Locale.getDefault())

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.OnGestureListener {
            override fun onDown(p0: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(p0: MotionEvent) {

            }

            override fun onSingleTapUp(p0: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                return false
            }

            override fun onLongPress(p0: MotionEvent) {
                animateLine()
            }

            override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                return false
            }
        })

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0)
        lineColor = typedArray.getColor(R.styleable.CustomView_lineColor, lineColor)
        paint.color = lineColor
        dateColor = typedArray.getColor(R.styleable.CustomView_dateColorText, dateColor)
        textDatePaint.color = dateColor
        textPaint.color = lineColor
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        height = (MeasureSpec.getSize(widthMeasureSpec) / 2.4).toInt()
        width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paddingTop = 25.dpToPx()
        val paddingBottom = 25.dpToPx()
        val drawingHeight = height - paddingTop - paddingBottom

        val columnSpacing = width / (columnHeights.size + 1).toFloat()

        for (i in 0 until columnHeights.size) {
            val left = columnSpacing * (i + 1) - columnWidth / 2
            val top = paddingTop + drawingHeight - (columnHeights[i] / maxColumnHeight) * drawingHeight
            val right = left + columnWidth
            val bottom = height - paddingBottom
            canvas.drawRoundRect(left, top, right, bottom, 10f, 10f, paint)

            val text = columnHeights[i].toString()
            val textWidth = textPaint.measureText(text)
            val x = (left + right) / 2 - textWidth / 2
            val y = top - 10 // Расположение текста над столбцом
            canvas.drawText(text, x, y, textPaint)

            // Отрисовка текста даты снизу под столбцом
            val dateText = dateLabels[i]
            val dateTextWidth = textPaint.measureText(dateText)
            val dateX = (left + right) / 2 - dateTextWidth / 2
            val dateY = height - paddingBottom + 10.dpToPx()
            canvas.drawText(dateText, dateX, dateY, textDatePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when {
            gestureDetector.onTouchEvent(event) -> true
            event.action == MotionEvent.ACTION_UP -> {
                animateLine()
                true
            }
            else -> false
        }
    }

    fun setColumnHeights(newHeights: List<Float>) {
        columnHeights.clear()
        columnHeights.addAll(newHeights)

        val today = Date()
        val calendar = Calendar.getInstance()
        calendar.time = today
        dateLabels.clear()

        for (i in newHeights.indices) {
            dateLabels.add(simpleDateFormat.format(calendar.time))
            calendar.add(Calendar.DATE, -1)
        }
        dateLabels.reverse()

        invalidate()
    }

    private fun animateLine() {
        for (i in 0 until columnHeights.size) {
            val targetHeight = columnHeights[i]
            val startHeight = 0f

            val animation = ValueAnimator.ofFloat(startHeight, targetHeight).apply {
                duration = 1000
                addUpdateListener { animator ->
                    columnHeights[i] = animator.animatedValue as Float
                    invalidate()
                }
            }
            animation.start()
        }
    }

    private fun Int.dpToPx(): Float {
        val scale = resources.displayMetrics.density
        return this * scale
    }

    //Сохраняем состояние CustomView
    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = CustomViewState(superState)
        savedState.columnHeights = columnHeights.toFloatArray()
        savedState.dateLabels = dateLabels.toTypedArray()
        return savedState
    }

    // Восстановление состояния CustomView
    public override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is CustomViewState) {
            super.onRestoreInstanceState(state.superState)
            columnHeights.clear()
            columnHeights.addAll(state.columnHeights?.toList() ?: emptyList())
            dateLabels.clear()
            dateLabels.addAll(state.dateLabels ?: emptyArray())
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}