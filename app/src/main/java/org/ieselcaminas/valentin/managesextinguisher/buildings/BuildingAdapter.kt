package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerBuildingLayoutBinding


class BuildingAdapter(val clickListener: BuildingListener): ListAdapter<Building, BuildingAdapter.ViewHolder>(BuildingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: RecyclerBuildingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Building, clickListener: BuildingListener) {
            binding.building = item
            binding.clickListener = clickListener

            binding.ImageBuilding.setImageResource(R.mipmap.building)
            binding.toolbarBuilding.title = item.nameBuildings
            binding.toolbarBuilding.inflateMenu(R.menu.menu_item)

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerBuildingLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class BuildingDiffCallback : DiffUtil.ItemCallback<Building>() {
    override fun areItemsTheSame(oldItem: Building, newItem: Building): Boolean {
        return oldItem.buildingId == newItem.buildingId
    }

    override fun areContentsTheSame(oldItem: Building, newItem: Building): Boolean {
        return oldItem == newItem
    }

}

class BuildingListener(val clickListener: (buildingId: Long) -> Unit) {
    fun onClick(building: Building) = clickListener(building.buildingId)
}
