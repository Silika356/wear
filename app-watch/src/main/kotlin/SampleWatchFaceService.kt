package com.louiscad.composeoclockplayground

import ScreenModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import kotlinx.coroutines.flow.*
import org.splitties.compose.oclock.ComposeWatchFaceService
import org.splitties.compose.oclock.sample.watchfaces.WatchFaceSwitcher

class SampleWatchFaceService : ComposeWatchFaceService(
    complicationSlotIds = emptySet(),
    invalidationMode = InvalidationMode.WaitForInvalidation
) {

    @Composable
    override fun Content(complicationData: Map<Int, StateFlow<ComplicationData>>) {
        WatchFaceSwitcher()
//        LaunchedEffect("") {
//            ScreenModel.startScope()
//        }
    }

    override fun supportedComplicationTypes(slotId: Int) = listOf(
        ComplicationType.RANGED_VALUE,
        ComplicationType.SHORT_TEXT
    )
}
