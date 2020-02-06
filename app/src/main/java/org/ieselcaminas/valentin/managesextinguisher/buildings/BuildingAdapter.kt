package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building


class BuildingAdapter: RecyclerView.Adapter<BuildingAdapter.ViewHolder>(){
    var data = listOf<Building>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = data[position]
        holder.bind(item)

    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageBuilding: ImageView = itemView.findViewById(R.id.ImageBuilding)
        val toolbarBuilding: Toolbar = itemView.findViewById(R.id.toolbarBuilding)

        fun bind(item: Building) {
            /*val res = holder.itemView.context.resources*/
            imageBuilding.setImageResource(R.drawable.building)
            toolbarBuilding.title = item.nameBuildings
            toolbarBuilding.inflateMenu(R.menu.menu_item)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_building_layout, parent, false)
                return ViewHolder(view)
            }
        }
    }
}
