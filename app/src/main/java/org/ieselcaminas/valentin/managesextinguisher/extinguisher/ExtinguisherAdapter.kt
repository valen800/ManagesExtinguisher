package org.ieselcaminas.valentin.managesextinguisher.extinguisher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerExtinguisherLayoutBinding

class ExtinguisherAdapter(val clickListener: ExtinguisherListener): ListAdapter<Extinguisher, ExtinguisherAdapter.ViewHolder>(ExtinguisherDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtinguisherAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExtinguisherAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: RecyclerExtinguisherLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Extinguisher, clickListener: ExtinguisherListener) {
            binding.extinguisher = item
            binding.clickListener = clickListener

            binding.ImageExtinguisher.setImageResource(R.drawable.extintor)
            binding.toolbarExtinguisher.title = item.nExtinguisher
            binding.toolbarExtinguisher.inflateMenu(R.menu.menu_item)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerExtinguisherLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ExtinguisherDiffCallBack : DiffUtil.ItemCallback<Extinguisher>() {
    override fun areItemsTheSame(oldItem: Extinguisher, newItem: Extinguisher): Boolean {
        return oldItem.extinguisherId == newItem.extinguisherId
    }

    override fun areContentsTheSame(oldItem: Extinguisher, newItem: Extinguisher): Boolean {
        return oldItem == newItem
    }

}

class ExtinguisherListener(val clickListener: (extinguisherId: Long) -> Unit) {
    fun onClick(extinguisher: Extinguisher) = clickListener(extinguisher.extinguisherId)
}