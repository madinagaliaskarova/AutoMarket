package com.example.technicaltaskforitsurf.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesm.databinding.ItemListCarsBinding
import com.example.technicaltaskforitsurf.ui.extensions.loadImage
import com.example.technicaltaskforitsurf.ui.model.CarsModelUI

class HomeCarsAdapter(
    private val onItemClick: (id: Int) -> Unit
) : ListAdapter<CarsModelUI, HomeCarsAdapter.CarsViewHolder>(CarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListCarsBinding.inflate(layoutInflater, parent, false)
        return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val currentCar = getItem(position)
        holder.bind(currentCar)
    }

    inner class CarsViewHolder(private val binding: ItemListCarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CarsModelUI) {
            binding.uploadImage.loadImage(model.imageUrl)
            binding.tvCarName.text = model.carName
            binding.root.setOnClickListener {
                model.id?.let(onItemClick)
            }
        }
    }

    class CarsDiffCallback : DiffUtil.ItemCallback<CarsModelUI>() {
        override fun areItemsTheSame(oldItem: CarsModelUI, newItem: CarsModelUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CarsModelUI, newItem: CarsModelUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}