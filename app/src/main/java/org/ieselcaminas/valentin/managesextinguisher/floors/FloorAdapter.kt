package org.ieselcaminas.valentin.managesextinguisher.floors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerFloorLayoutBinding

class FloorAdapter(val clickListener: FloorListener): ListAdapter<Floor, FloorAdapter.ViewHolder>(FloorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: RecyclerFloorLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Floor, clickListener: FloorListener) {
            binding.floor = item
            binding.clickListener = clickListener

            binding.ImageFloor.setImageResource(R.mipmap.floor256)
            binding.toolbarFloor.title = item.nameFloor
            binding.toolbarFloor.inflateMenu(R.menu.menu_item)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerFloorLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class FloorDiffCallback : DiffUtil.ItemCallback<Floor>() {
    override fun areItemsTheSame(oldItem: Floor, newItem: Floor): Boolean {
        return oldItem.floorId == newItem.floorId
    }

    override fun areContentsTheSame(oldItem: Floor, newItem: Floor): Boolean {
        return oldItem == newItem
    }

}

class FloorListener(val clickListener: (floorId: Long) -> Unit) {
    fun onClick(floor: Floor) = clickListener(floor.floorId)
}