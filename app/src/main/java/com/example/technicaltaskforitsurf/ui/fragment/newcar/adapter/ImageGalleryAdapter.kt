package com.example.technicaltaskforitsurf.ui.fragment.newcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesm.databinding.ItemGalleryPhotoPlaceHolderBinding
import com.example.technicaltaskforitsurf.ui.extensions.hide
import com.example.technicaltaskforitsurf.ui.extensions.loadImage
import com.example.technicaltaskforitsurf.ui.extensions.show
import com.example.technicaltaskforitsurf.ui.model.PhotoUploadResult

class ImageGalleryAdapter(
    private val onItemClick: () -> Unit
) : ListAdapter<PhotoUploadResult, ImageGalleryAdapter.ImageGalleryHolder>(Companion) {

    private var listWithDefaultItem =
        mutableListOf(PhotoUploadResult(localPath = "default"))

    fun submitMyList(newList: List<PhotoUploadResult>) {
        listWithDefaultItem = mutableListOf(PhotoUploadResult(localPath = "default"))
        listWithDefaultItem.addAll(newList)
        submitList(listWithDefaultItem)
    }

    inner class ImageGalleryHolder(private val binding: ItemGalleryPhotoPlaceHolderBinding) :
        ViewHolder(binding.root) {
        fun onBind(it: PhotoUploadResult) {
            with(binding) {
                if (it.localPath == "default") {
                    uploadImage.hide()
                } else {
                    it.localPath.let { path -> uploadImage.loadImage(path) }
                    uploadImage.show()
                }
            }
        }

        init {
            with(binding) {
                root.setOnClickListener {
                    if (adapterPosition == 0) onItemClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryHolder {
        return ImageGalleryHolder(
            ItemGalleryPhotoPlaceHolderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageGalleryHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }


    companion object : DiffUtil.ItemCallback<PhotoUploadResult>() {
        override fun areItemsTheSame(oldItem: PhotoUploadResult, newItem: PhotoUploadResult) =
            oldItem.localPath == newItem.localPath

        override fun areContentsTheSame(oldItem: PhotoUploadResult, newItem: PhotoUploadResult) =
            oldItem == newItem
    }
}