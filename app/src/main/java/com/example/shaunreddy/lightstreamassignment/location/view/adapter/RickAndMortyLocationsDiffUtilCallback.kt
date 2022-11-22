package com.example.shaunreddy.lightstreamassignment.location.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation

class RickAndMortyLocationsDiffUtilCallback(private val oldList: List<RickAndMortyLocation>, private val newList: List<RickAndMortyLocation>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id
}