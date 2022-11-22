package com.example.shaunreddy.lightstreamassignment.location.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyLocationsAdapterViewBinding
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation

interface RickAndMortyLocationAdapterHandler {
    fun onLocationSelected(location: RickAndMortyLocation)
}

class RickAndMortyLocationsAdapter(private val context: Context,
                                   private val handler: RickAndMortyLocationAdapterHandler): Adapter<RickAndMortyLocationsAdapter.RickAndMortyLocationsViewHolder>() {

    private lateinit var binding: RickandmortyLocationsAdapterViewBinding
    private var items: ArrayList<RickAndMortyLocation> = ArrayList()

    class RickAndMortyLocationsViewHolder(private val binding: RickandmortyLocationsAdapterViewBinding): ViewHolder(binding.root) {
        fun bind(rickAndMortyLocation: RickAndMortyLocation) {
            binding.rickandmortyLocationsName.text = rickAndMortyLocation.name
            binding.rickandmortyLocationsType.text = rickAndMortyLocation.type
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RickAndMortyLocationsViewHolder {
        binding = RickandmortyLocationsAdapterViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return RickAndMortyLocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RickAndMortyLocationsViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            handler.onLocationSelected(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateItems(items: List<RickAndMortyLocation>) {
        val diffCallback = RickAndMortyLocationsDiffUtilCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}