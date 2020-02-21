package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_building_insert.view.*
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerBuildingLayoutBinding


class BuildingAdapter(val clickListener: BuildingListener,
                      private val activity: FragmentActivity?,
                      private val viewLifecycleOwner: LifecycleOwner,
                      private val buildingViewModel: BuildingFragmentViewModel
): ListAdapter<Building, BuildingAdapter.ViewHolder>(BuildingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position)!!, clickListener, activity, viewLifecycleOwner, buildingViewModel)
    }

    class ViewHolder private constructor(val binding: RecyclerBuildingLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(itemBuilding: Building, clickListener: BuildingListener, activity: FragmentActivity?, viewLifecycleOwner: LifecycleOwner, buildingViewModel: BuildingFragmentViewModel) {
            binding.building = itemBuilding
            binding.clickListener = clickListener

            binding.ImageBuilding.setImageResource(R.mipmap.building)
            binding.toolbarBuilding.title = itemBuilding.nameBuildings
            binding.toolbarBuilding.inflateMenu(R.menu.menu_item)

            binding.toolbarBuilding.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    (R.id.action_Info) -> {
                        dialogInfoBuilding(itemBuilding, activity)
                    }
                    (R.id.action_Delete) -> {
                        dialogDeleteBuilding(itemBuilding, activity, buildingViewModel)
                    }
                    (R.id.action_modify) -> {
                        ModifyBuildingDialong(itemBuilding, activity, buildingViewModel)
                    }
                }
                true
            })

        }

        private fun ModifyBuildingDialong(itemBuilding: Building, activity: FragmentActivity?, buildingViewModel: BuildingFragmentViewModel) {
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_building_insert, null)

            mDialogView.editTextInsertBuildingNameDia.setText(itemBuilding.nameBuildings)

            val mbuilder = AlertDialog.Builder(activity).setView(mDialogView)
            mbuilder.setTitle("Building form")
            val mAlertDialog = mbuilder.show()
            mDialogView.buttonSubmitInsertBuilding.setOnClickListener {
                var building = itemBuilding
                building.nameBuildings = mDialogView.editTextInsertBuildingNameDia.text.toString()

                buildingViewModel.updateBuilding(building)
                buildingViewModel.startRefresh()
                mAlertDialog.dismiss()
            }
            mDialogView.buttonCancelInsertBuilding.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        private fun dialogInfoBuilding(itemBuilding: Building, activity: FragmentActivity?) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Building info")
            builder.setMessage("Building name: " + itemBuilding.nameBuildings)
            builder.setNeutralButton("Cancel") { dialog, which ->
                Toast.makeText(
                    activity,
                    "You cancelled the dialog.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        private fun dialogDeleteBuilding(itemBuilding: Building, activity: FragmentActivity?, buildingViewModel: BuildingFragmentViewModel) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Erase Building?")
            builder.setMessage("You'll lose all elements inside of this building")
            builder.setPositiveButton("Erase") { dialog, which ->
                buildingViewModel.deleteBuilding(itemBuilding.buildingId)
            }
            builder.setNegativeButton("Cancel") {dialog, which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
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
