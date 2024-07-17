package org.splitties.compose.oclock.sample.watchfaces

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import ScreenModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import com.google.android.gms.common.api.internal.LifecycleActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.splitties.compose.oclock.LocalIsAmbient
import org.splitties.compose.oclock.LocalTime
import org.splitties.compose.oclock.OClockCanvas
import org.splitties.compose.oclock.sample.WatchFacePreview
import org.splitties.compose.oclock.sample.WearPreviewSizesProvider
import org.splitties.compose.oclock.sample.extensions.loop

@Composable
fun TraderClock() {
    val isAmbient by LocalIsAmbient.current
    val scope = CoroutineScope(Dispatchers.IO)
    DigitClock()
    DrawPrice("BTC", ScreenModel.priceBTC, Offset(-100f, -50f))
    DrawPrice("ETH", ScreenModel.priceETH, Offset(-100f, -00f))
    DrawPrice("SOL", ScreenModel.priceSOL, Offset(-100f, 50f))
    LaunchedEffect(isAmbient) {
        if (!isAmbient) {
            scope.launch {
                ScreenModel.priceBTC.value = "..."
                ScreenModel.priceETH.value = "..."
                ScreenModel.priceSOL.value = "..."
                ScreenModel.startScope(isAmbient)
            }
        }
    }
    if (isAmbient) {
        index.value = 0
    }
}

private val brightColors = listOf(
    Color(0x7C00E5FF),
    Color(0xFF00E5FF),
    Color(0xFF64DD17),
)

private val someColors = listOf(
    Color(0xFF3D5AFE),
    Color(0xFF00E5FF),
    Color(0xFF00B0FF),
).loop()

private val ambientTransitionSpec = spring<Float>(stiffness = Spring.StiffnessLow)

private val composeMultiplatformColor = Color(red = 66, green = 133, blue = 244)

@Composable
private fun Background() {
    val isAmbient by LocalIsAmbient.current
    val ambientColor = Color.Black
    val interactiveColor = Color.White
    val ambientProgress by animateFloatAsState(
        targetValue = if (isAmbient) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    OClockCanvas {
        val showAmbientBg = ambientProgress != 0f
        val showInteractiveBg = ambientProgress != 1f
        if (showAmbientBg) {
            drawCircle(ambientColor)
        }
        if (showInteractiveBg) {
            val radius = (size.minDimension / 2f) * (1 - ambientProgress)
            drawCircle(interactiveColor, radius = radius)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun DigitClock() {
    val time = LocalTime.current
    val textMeasurer = rememberTextMeasurer()
    val style1 = TextStyle(
        fontSize = 24.sp,
        color = Color.White,
        background = Color.Black.copy(alpha = 0.2f),
        textDecoration = TextDecoration.Underline
    )
    OClockCanvas {
        drawCircle(
            center = Offset(
                x = center.x,
                y = center.y
            ),
            radius = 180f,
            color = Color.Cyan,
            style = Stroke(
                width = 8f
            )
        )
        drawText(
            textMeasurer,
            "${String.format("%02d", time.hours)}:" +
                    "${String.format("%02d", time.minutes)}:" +
                    "${String.format("%02d", time.seconds)}",
            Offset(
                x = center.x - 90,
                y = center.y - 120
            ),
            style1
        )
    }
}

@Composable
private fun DrawPrice(name: String, state: MutableState<String>, offs: Offset) {
    val textMeasurer = rememberTextMeasurer()
    val style1 = TextStyle(
        fontSize = 15.sp,
        color = Color.White,
        background = Color.Black.copy(alpha = 0.2f),
        textDecoration = TextDecoration.Underline
    )
    val priceOffs = 30f
    OClockCanvas {
        drawText(
            textMeasurer,
            name,
            offs.copy(center.x + offs.x, center.y + offs.y),
            style1
        )
        drawText(
            textMeasurer,
            state.value,
            offs.copy(center.x + priceOffs, center.y + offs.y),
            style1
        )
    }
}


@WatchFacePreview
@Composable
private fun ComposeTraderClockPreview(
    @PreviewParameter(WearPreviewSizesProvider::class) size: Dp
) = WatchFacePreview(size) {
    TraderClock()
}
