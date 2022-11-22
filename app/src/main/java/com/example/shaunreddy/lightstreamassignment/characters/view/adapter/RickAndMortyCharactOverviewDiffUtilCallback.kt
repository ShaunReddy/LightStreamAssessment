package com.example.shaunreddy.lightstreamassignment.characters.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character

class RickAndMortyCharactOverviewDiffUtilCallback(private val oldList: List<Character>, private val newList: List<Character>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}