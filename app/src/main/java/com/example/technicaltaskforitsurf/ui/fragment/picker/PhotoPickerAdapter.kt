package com.example.technicaltaskforitsurf.ui.fragment.picker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesm.databinding.ItemPhotoInPickerBinding
import com.example.technicaltaskforitsurf.ui.extensions.loadImage
import com.example.technicaltaskforitsurf.ui.model.PhotoUploadResult


private const val MAX_SELECTED_PHOTOS = 11

class PhotoPickerAdapter(
    private val selectedItemsConst: List<PhotoUploadResult>
) : ListAdapter<PhotoUploadResult, PhotoPickerAdapter.PickerViewHolder>(Companion) {

    private val selectedItems = selectedItemsConst.toMutableList()
    private val visibleViewHolders = mutableListOf<PickerViewHolder>()

    fun getSelectedPhotos() = selectedItems.toList()

    inner class PickerViewHolder(private val binding: ItemPhotoInPickerBinding) :
        ViewHolder(binding.root) {
        fun onBind(result: PhotoUploadResult) {
            loadImage(result)
            setCheckboxStatus(result)
            setOverlayStatus(result)
            setupClickListener()
        }

        private fun loadImage(result: PhotoUploadResult) {
            result.localPath.let {
                binding.uploadImage.loadImage(it)
            }
        }

        private fun setCheckboxStatus(result: PhotoUploadResult) {
            binding.checkbox.isChecked = result.isChecked
        }

        private fun setupClickListener() {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(adapterPosition)
                    if (!currentItem.isChecked && selectedItems.size >= MAX_SELECTED_PHOTOS) {
                        Toast.makeText(
                            binding.root.context,
                            "Вы можете выбрать максимум $MAX_SELECTED_PHOTOS фотографий",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    toggleSelection(currentItem)
                    setOverlayStatus(currentItem)
                }
            }
        }

        private fun toggleSelection(currentItem: PhotoUploadResult) {
            currentItem.isChecked = !currentItem.isChecked
            binding.checkbox.isChecked = currentItem.isChecked
            if (currentItem.isChecked) {
                currentItem.orderNumber = selectedItems.size + 1
                selectedItems.add(currentItem)
            } else {
                selectedItems.remove(currentItem)
            }
        }

        private fun updateVisibleViewHolders() {
            for (visibleViewHolder in visibleViewHolders) {
                visibleViewHolder.updateOrderNumberText()
            }
        }

        private fun updateOrderNumberText() {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val currentItem = getItem(adapterPosition)
            }
        }

        private fun setOverlayStatus(result: PhotoUploadResult) {
            binding.dimOverlay.visibility = if (result.isChecked) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickerViewHolder {
        return PickerViewHolder(
            ItemPhotoInPickerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PickerViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
        if (!visibleViewHolders.contains(holder)) {
            visibleViewHolders.add(holder)
        }
    }

    override fun onViewAttachedToWindow(holder: PickerViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (!visibleViewHolders.contains(holder)) {
            visibleViewHolders.add(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: PickerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        visibleViewHolders.remove(holder)
    }

    companion object : DiffUtil.ItemCallback<PhotoUploadResult>() {
        override fun areItemsTheSame(oldItem: PhotoUploadResult, newItem: PhotoUploadResult) =
            oldItem.localPath == newItem.localPath

        override fun areContentsTheSame(oldItem: PhotoUploadResult, newItem: PhotoUploadResult) =
            oldItem == newItem
    }
}
