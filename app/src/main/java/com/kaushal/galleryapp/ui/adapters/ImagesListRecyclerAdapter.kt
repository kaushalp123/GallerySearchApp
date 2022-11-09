package com.kaushal.galleryapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.databinding.ImagesListItemBinding
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class ImagesListRecyclerAdapter :
    RecyclerView.Adapter<ImagesListRecyclerAdapter.ImageViewHolder>() {

    class Callback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.images[0].link == newItem.images[0].link
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, Callback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagesListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }


    inner class ImageViewHolder(
        private val binding: ImagesListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            Log.i("title", "is  ${item.images[0].title}")
            val title = item.images[0].title as CharSequence?
            binding.txtTitle.text = title ?: "NA"
            binding.txtDate.text =
                item.images[0].datetime?.let { "Posted on ".plus(getFormattedTime(it)) }.toString()
            if (item.images.size > 1) {
                binding.txtAdditionalImg.visibility = View.VISIBLE
                binding.txtAdditionalImg.text = "view +${item.images.size} more images"
            } else {
                binding.txtAdditionalImg.visibility = View.GONE
            }


            Glide.with(binding.img).load(item.images[0].link)
                .placeholder(com.kaushal.galleryapp.R.drawable.placeholder_img).into(binding.img)
        }

        private fun getFormattedTime(it: Long): String {
            val localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm a")
            return localDateTime.format(formatter)
        }
    }
}

