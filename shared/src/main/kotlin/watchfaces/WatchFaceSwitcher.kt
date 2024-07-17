package org.splitties.compose.oclock.sample.watchfaces

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.splitties.compose.oclock.OClockCanvas
import org.splitties.compose.oclock.TapEvent
import org.splitties.compose.oclock.sample.WatchFacePreview
import org.splitties.compose.oclock.sample.WearPreviewSizesProvider

var index = mutableIntStateOf(0)
@Composable
fun WatchFaceSwitcher() {
    OClockCanvas(
        onTap = { event ->
            if (event is TapEvent.Up) {
                if (index.value == 0) {
                    index.value = 1
                } else {
                    index.value = 0
                }
//                val lastIndex = allWatchFaces.lastIndex
//                val touchEdgeWidth = 48.dp.toPx()
//                val isOnLeftEdge = event.position.x <= touchEdgeWidth
//                val isOnRightEdge = isOnLeftEdge.not() &&
//                        event.position.x >= size.width - touchEdgeWidth
//                index = when {
//                    isOnLeftEdge -> index - 1
//                    isOnRightEdge -> index + 1
//                    else -> index
//                }.let {
//                    when {
//                        it < 0 -> lastIndex
//                        it > lastIndex -> 0
//                        else -> it
//                    }
//                }
            }
            true
        }
    ) { }
    allWatchFaces[index.value]()
}

@WatchFacePreview
@Composable
private fun WatchFaceSwitcherPreview(
    @PreviewParameter(WearPreviewSizesProvider::class) size: Dp
) = WatchFacePreview(size) {
    WatchFaceSwitcher()
}
