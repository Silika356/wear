package org.splitties.compose.oclock.sample.watchfaces

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louiscad.composeoclockplayground.shared.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.splitties.compose.oclock.LocalIsAmbient
import org.splitties.compose.oclock.LocalTime
import org.splitties.compose.oclock.OClockCanvas
import org.splitties.compose.oclock.internal.InternalComposeOClockApi
import org.splitties.compose.oclock.sample.WatchFacePreview
import org.splitties.compose.oclock.sample.WearPreviewSizesProvider
import org.splitties.compose.oclock.sample.elements.clockHand
import org.splitties.compose.oclock.sample.extensions.rotate
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BasicAnalogClock() {
    Background()
//    val textBrush = remember {
//        Brush.sweepGradient(
//            4.5f/8f to Color(0x7C00E5FF),
//            5.1f/8f to Color(0xFF00E5FF),
//            6.2f/8f to Color(0xFF00E5FF),
//            6.5f/8f to Color(0xFFFFFF8D),
//        )
//    }
//    ComposeOClockWatermark(textBrush)
    CurrentDate()
    HoursHand()
    MinutesHand()
    SecondsHand()
//    CenterDot()
}

@Composable
private fun Background() {
    val isAmbient by LocalIsAmbient.current
    val image = ImageBitmap.imageResource(id = R.drawable.background)
    OClockCanvas {
//        if (isAmbient.not()) drawCircle(kotlinDarkBg)
        drawImage(image, dstSize = IntSize(396, 396))
    }

}

var paint1 = Paint()
var paint2 = Paint()
var paint3 = Paint()

fun getPaint(width: Float, color: Color) {
    val borderWidth = width
    val shadowWidth = 10f
    val offsetY = 0f
    val offsetX = 0f
    val borderColor = android.graphics.Color.toArgb(color.value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(0.1f).value.toLong())
    val frameworkPaint = paint2.asFrameworkPaint()
    val shadowPaint = paint1.asFrameworkPaint()
    val centerPaint = paint3.asFrameworkPaint()
    centerPaint.color = Color.White.toArgb()
    centerPaint.strokeWidth = 1f
    frameworkPaint.color = color.toArgb()
    shadowPaint.setShadowLayer(
        shadowWidth,
        offsetX,
        offsetY,
        borderColor
    )
    frameworkPaint.strokeWidth = borderWidth
//    val dev = borderWidth.toPx() / 2.0f
//        it.drawLine(
//            Offset(0f, 0f),
//            Offset(0f, this.size.height + dev),
//            paint
//        ) // left
//    shadowPaint.color = shadowColor
//    shadowPaint.strokeWidth = 16f
//    if (leftBorder) {
//        it.drawLine(
//            Offset(0f, 0f),
//            Offset(0f, this.size.height),
//            paint2
//        ) // left
//    }
}

@Composable
private fun HoursHand() {
    val time = LocalTime.current
    val isAmbient by LocalIsAmbient.current
    OClockCanvas {
        rotate(degrees = time.hours % 12 * 30f + 180f + time.minutes.toFloat() / 60 * 30) {
            val height = 80f
            val width = 6f
            getPaint(width, Color.White)
            drawIntoCanvas {
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint1
                )
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y - width,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint2
                )
                if (!isAmbient) {
                    it.drawRoundRect(
                        center.x - width / 2,
                        center.y - width,
                        center.x + width / 2,
                        center.y + height,
                        10f,
                        10f,
                        paint3
                    )
                }
//                it.drawLine(Offset(center.x, center.y), Offset(center.x, center.y+150), paint1)
            }
//            drawLine(Offset(center.x, center.y), Offset(center.x, center.y+100))
//            clockHand(
//                brush = SolidColor(kotlinLogoColors[0]),
//                width = width,
//                height = size.height / 4f,
//                style = if (isAmbient) Stroke(width = 3.dp.toPx()) else Fill,
//                blendMode = BlendMode.Plus
//            )
        }
    }
}


@Composable
private fun MinutesHand() {
    val time = LocalTime.current
    val isAmbient by LocalIsAmbient.current
    OClockCanvas {
        rotate(degrees = time.minutes * 6f + 180f + time.seconds.toFloat() / 60 * 6) {
            val height = 120f
            val width = 4f
            getPaint(width, Color.Blue)
            drawIntoCanvas {
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint1
                )
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y - width,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint2
                )
                if (!isAmbient) {
                    it.drawRoundRect(
                        center.x - 1,
                        center.y - width,
                        center.x + 1,
                        center.y + height,
                        10f,
                        10f,
                        paint3
                    )
                }
            }
        }
    }
}

@Composable
private fun SecondsHand() {
    val time = LocalTime.current
    val isAmbient by LocalIsAmbient.current
    OClockCanvas {
        if (isAmbient) return@OClockCanvas
//        val color = kotlinLogoColors[2]
//        drawLine(
//            color,
//            start = center,
//            end = center.copy(y = size.height / 32f).rotate(time.seconds * 6f),
//            strokeWidth = 2.dp.toPx(),
//            blendMode = BlendMode.Lighten,
//            cap = StrokeCap.Round
//        )

        rotate(degrees = time.seconds * 6f + 180f) {
            val height = 160f
            val height2 = 20f
            val width = 3f
            val width2 = 6f
            getPaint(width, Color.Red)
            drawIntoCanvas {
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint1
                )
                it.drawRoundRect(
                    center.x - width / 2,
                    center.y,
                    center.x + width / 2,
                    center.y + height,
                    10f,
                    10f,
                    paint2
                )
                it.drawRoundRect(
                    center.x - 1,
                    center.y,
                    center.x + 1,
                    center.y + height,
                    10f,
                    10f,
                    paint3
                )
                it.drawRoundRect(
                    center.x - width2 / 2,
                    center.y,
                    center.x + width2 / 2,
                    center.y - height2,
                    10f,
                    10f,
                    paint1
                )
                it.drawRoundRect(
                    center.x - width2 / 2,
                    center.y,
                    center.x + width2 / 2,
                    center.y - height2,
                    10f,
                    10f,
                    paint2
                )
                it.drawRoundRect(
                    center.x - 1,
                    center.y,
                    center.x + 1,
                    center.y - height2,
                    10f,
                    10f,
                    paint3
                )
            }
//                it.drawLine(Offset(center.x, center.y), Offset(center.x, center.y+150), paint1)
        }
    }
}

@Composable
private fun CenterDot() {
    val isAmbient by LocalIsAmbient.current
    OClockCanvas {
        drawCircle(kotlinDarkBg, radius = 6.dp.toPx(), blendMode = BlendMode.Xor)
        drawCircle(
            color = if (isAmbient) Color.Black else kotlinLogoColors[2],
            radius = 5.dp.toPx()
        )
        if (isAmbient) {
            drawCircle(
                Color.Gray,
                radius = 5.dp.toPx()
            )
            drawCircle(
                Color.Black,
                radius = 2.dp.toPx()
            )
        }
    }
}

@OptIn(InternalComposeOClockApi::class)
@Composable
private fun CurrentDate() {
    val time = LocalTime.current
    val isAmbient by LocalIsAmbient.current
    val textMeasurer = rememberTextMeasurer()
    val style1 = TextStyle(
        fontSize = 16.sp,
        color = Color.White,
        background = Color.Black.copy(alpha = 0.2f)
    )
    val text = remember { mutableStateOf("") }
    OClockCanvas {
        drawText(
            textMeasurer, "${time.dateTime.dayOfMonth} ${text.value}", Offset(
                x = center.x - 52,
                y = center.y + 40
            ), style1
        )
        if (isAmbient) return@OClockCanvas
        text.value = SimpleDateFormat("MMMM", Locale("ru")).format(time.instant.toEpochMilli())
    }
}

private val kotlinDarkBg = Color(0xFF1B1B1B)
private val kotlinBlue = Color(0xFF7F52FF)
private val kotlinLogoColors = listOf(
    kotlinBlue,
    Color(0xFF_C811E2),
    Color(0xFF_E54857),
)

@WatchFacePreview
@Composable
private fun BasicAnalogClockPreview(
    @PreviewParameter(WearPreviewSizesProvider::class) size: Dp
) = WatchFacePreview(size) {
    BasicAnalogClock()
}
