package com.aunkhtoo.composeonboarding.screen.healthconcern.component

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
Created By Aunt Htoo Aung on 25/11/2023.

see https://proandroiddev.com/basic-drag-n-drop-in-jetpack-compose-a6919ba58ba8
 **/

@Composable
fun <T> ReorderableList(
  items: List<T>,
  onMove: (from: Int, to: Int) -> Unit,
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical,
  onDragStart: () -> Unit = {},
  itemWithIndex: @Composable (index: Int, item: T) -> Unit
) {

  val scope = rememberCoroutineScope()

  var overscrollJob by remember { mutableStateOf<Job?>(null) }

  val dragDropListState = rememberReorderableListState(onMove = onMove)

  LazyColumn(
    modifier = modifier
      .pointerInput(Unit) {
        detectDragGesturesAfterLongPress(
          onDrag = { change, offset ->
            change.consume()
            dragDropListState.onDrag(offset)

            if (overscrollJob?.isActive == true)
              return@detectDragGesturesAfterLongPress

            dragDropListState.checkForOverScroll()
              .takeIf { it != 0f }
              ?.let {
                overscrollJob = scope.launch { dragDropListState.lazyListState.scrollBy(it) }
              }
              ?: run { overscrollJob?.cancel() }
          },
          onDragStart = { offset ->
            onDragStart()
            dragDropListState.onDragStart(offset)
          },
          onDragEnd = { dragDropListState.onDragInterrupted() },
          onDragCancel = { dragDropListState.onDragInterrupted() }
        )
      },
    state = dragDropListState.lazyListState,
    verticalArrangement = verticalArrangement
  ) {
    itemsIndexed(items) { index, item ->
      Column(
        modifier = Modifier
          .composed {
            val offsetOrNull =
              dragDropListState.elementDisplacement.takeIf {
                index == dragDropListState.currentIndexOfDraggedItem
              }

            Modifier
              .graphicsLayer {
                translationY = offsetOrNull ?: 0f
              }
          }
          .fillMaxWidth()
      ) {
        //content
        itemWithIndex(index, item)
      }
    }
  }
}