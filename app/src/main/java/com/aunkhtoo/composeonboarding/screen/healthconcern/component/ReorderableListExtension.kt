package com.aunkhtoo.composeonboarding.screen.healthconcern.component

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
Created By Aunt Htoo Aung on 25/11/2023.
 **/

/*
    LazyListItemInfo.index is the item's absolute index in the list
    Based on the item's "relative position" with the "currently top" visible item,
    this returns LazyListItemInfo corresponding to it
*/
fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? {
  return this.layoutInfo.visibleItemsInfo.getOrNull(absoluteIndex - this.layoutInfo.visibleItemsInfo.first().index)
}

/*
  Bottom offset of the element in Vertical list
*/
val LazyListItemInfo.offsetEnd: Int
  get() = this.offset + this.size

/*
   Moving element in the list
*/
fun <T> MutableList<T>.move(from: Int, to: Int) {
  if (from == to)
    return

  val element = this.removeAt(from) ?: return
  this.add(to, element)
}

/*
moving element in the mutable state list
 */
fun <T> SnapshotStateList<T>.move(from: Int, to: Int) {
  if (from == to)
    return

  val element = this.removeAt(from) ?: return
  this.add(to, element)
}