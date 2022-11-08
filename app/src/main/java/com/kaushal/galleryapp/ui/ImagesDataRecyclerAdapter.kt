package com.kaushal.galleryapp.ui

import android.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaushal.galleryapp.R
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.databinding.ImagesListItemBinding
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class ImagesDataRecyclerAdapter :
    ListAdapter<Data, RecyclerView.ViewHolder>(Callback()) {

    companion object {
        const val LIST_VIEW = 0
        const val GRID_VIEW = 1
    }

    private var isSwitchView = true

    class Callback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.images[0].link == newItem.images[0].link
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, Callback())

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            true -> ITEM_VIEW_TYPE_HORIZONTAL
            false -> ITEM_VIEW_TYPE_VERTICAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        if (viewType === LIST_VIEW) {
            itemView =
                LayoutInflater.from(parent.context).inflate(com.kaushal.galleryapp.R.layout.images_list_item, null)
        } else {
            itemView =
                LayoutInflater.from(parent.context).inflate(com.kaushal.galleryapp.R.layout.images_grid_item, null)
        }
        return itemView.rootView
    }

    fun toggleItemViewType(): Boolean {
        isSwitchView = !isSwitchView
        return isSwitchView
    }

    /**
     * The View Holder Created above are used here.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LIST_VIEW -> {
                val item = getItem(position) as ImageViewHolder1
                holder.bind(item.yourData)
            }
            is ImageViewHolder2 -> {
                val item = getItem(position) as ImageViewHolder2

                holder.bind(item.yourData)
            }
        }

        /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
             if (viewType == LIST_VIEW) {
                 val binding = ImagesListItemBinding
                     .inflate(LayoutInflater.from(parent.context), parent, false)
                 return ImageViewHolder(binding)
             } else {
                 val binding = ImagesGridItemBinding
                     .inflate(LayoutInflater.from(parent.context), parent, false)

                 return ImageViewHolder(binding)
             }

         }*/

        /*  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
              val imageList = differ.currentList[position]
              holder.bind(imageList)
          }

          override fun getItemViewType(position: Int): Int {
              return if (isSwitchView) {
                  LIST_VIEW
              } else {
                  GRID_VIEW
              }
          }*/

        class ImageViewHolder1(
            private val binding: ImagesListItemBinding
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Data) {
                Log.i("title", "is  ${item.images[0].title}")
                val title = item.images[0].title as CharSequence?
                binding.txtTitle.text = title ?: "NA"
                binding.txtDate.text = item.images[0].datetime?.let { getFormattedTime(it) }.toString()
                if (item.images.size > 1) {
                    binding.txtAdditionalImg.visibility = View.VISIBLE
                    binding.txtAdditionalImg.text = "view +${item.images.size} more images"
                } else {
                    binding.txtAdditionalImg.visibility = View.GONE
                }


                Glide.with(binding.img).load(item.images[0].link)
                    .placeholder(R.drawable.placeholder_img).into(binding.img)
            }

            private fun getFormattedTime(it: Long) : String {
                val localDateTime =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm a")
                return localDateTime.format(formatter)
            }
        }

        class ImageViewHolder2(
            private val binding: ImagesListItemBinding
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Data) {
                Log.i("title", "is  ${item.images[0].title}")
                val title = item.images[0].title as CharSequence?
                binding.txtTitle.text = title ?: "NA"
                binding.txtDate.text = item.images[0].datetime?.let { getFormattedTime(it) }
                if (item.images.size > 1) {
                    binding.txtAdditionalImg.visibility = View.VISIBLE
                    binding.txtAdditionalImg.text = "view +${item.images.size} more images"
                } else {
                    binding.txtAdditionalImg.visibility = View.GONE
                }


                Glide.with(binding.img).load(item.images[0].link)
                    .placeholder(R.drawable.placeholder_img).into(binding.img)
            }

            private fun getFormattedTime(it: Long) : String {
                val localDateTime =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm a")
                return localDateTime.format(formatter)
            }
        }

    }

    sealed class DataItem {
        data class HorizontalClass(val yourData: Data) : DataItem() {
            override val id = yourData.id
            override val type = true
        }

        data class VerticalClass(val yourData: Data) : DataItem() {
            override val id = yourData.id
            override val type = false
        }

        abstract val id: Long
        abstract val type: Boolean
    }
}

