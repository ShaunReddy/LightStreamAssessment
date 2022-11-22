package com.example.shaunreddy.lightstreamassignment.characters.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyCharacterOverviewCardBinding

interface RickAndMortyCharacterOverviewAdapterHandler {
    fun onCharacterSelected(character: Character)
}

class RickAndMortyCharacterOverviewAdapter(private val context: Context, private val handler: RickAndMortyCharacterOverviewAdapterHandler): Adapter<RickAndMortyCharacterOverviewAdapter.RickAndMortyCharacterOverviewHolder>() {


    private var items = ArrayList<Character>()

    class RickAndMortyCharacterOverviewHolder(private val binding: RickandmortyCharacterOverviewCardBinding, private val context: Context): ViewHolder(binding.root){
        fun bind(item: Character?) {
            with(binding){
                item?.let{
                    rickandmortyCharacterOverviewStatus.text = it.status
                    rickandmortyCharacterOverviewName.text = it.name
                    Glide.with(context)
                        .load(it.image)
                        .into(rickandmortyCharacterOverviewImg)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RickAndMortyCharacterOverviewHolder {
        val binding = RickandmortyCharacterOverviewCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return RickAndMortyCharacterOverviewHolder(binding, context)
    }



    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addList(items: List<Character>) {
        val diffCallback = RickAndMortyCharactOverviewDiffUtilCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RickAndMortyCharacterOverviewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            handler.onCharacterSelected(items[position])
        }
    }
}