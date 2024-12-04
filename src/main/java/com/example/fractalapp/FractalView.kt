import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.pow

class FractalView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var scaleFactor = 1.0f
    private var offsetX = 0.0f
    private var offsetY = 0.0f

    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the fractal and draw it
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(width * height)

        val centerX = width / 2.0
        val centerY = height / 2.0
        val zoom = 300.0 / scaleFactor

        for (y in 0 until height) {
            for (x in 0 until width) {
                val zx = (x - centerX - offsetX) / zoom
                val zy = (y - centerY - offsetY) / zoom
                pixels[y * width + x] = calculateFractalColor(zx, zy)
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    private fun calculateFractalColor(zx: Double, zy: Double): Int {
        var x = zx
        var y = zy
        val maxIterations = 100
        var iterations = 0

        while (x * x + y * y < 4 && iterations < maxIterations) {
            val temp = x * x - y * y + zx
            y = 2 * x * y + zy
            x = temp
            iterations++
        }

        return if (iterations == maxIterations) Color.BLACK else Color.HSVToColor(
            floatArrayOf(iterations * 360f / maxIterations, 1f, 1f)
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.5f, 5.0f)
            invalidate()
            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            offsetX -= distanceX / scaleFactor
            offsetY -= distanceY / scaleFactor
            invalidate()
            return true
        }
    }
}
