package org.ieselcaminas.valentin.managesextinguisher.floors

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
import kotlinx.android.synthetic.main.dialog_floor_insert.view.*
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.buildings.BuildingFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerFloorLayoutBinding

class FloorAdapter(
    val clickListener: FloorListener,
    private val activity: FragmentActivity?,
    private val viewLifecycleOwner: LifecycleOwner,
    private val floorViewModel: FloorsFragmentViewModel
    ): ListAdapter<Floor, FloorAdapter.ViewHolder>(FloorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, activity, viewLifecycleOwner, floorViewModel)
    }

    class ViewHolder private constructor(val binding: RecyclerFloorLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(itemFloor: Floor, clickListener: FloorListener, activity: FragmentActivity?, viewLifecycleOwner: LifecycleOwner, floorViewModel: FloorsFragmentViewModel) {
            binding.toolbarFloor.menu.clear()
            binding.floor = itemFloor
            binding.clickListener = clickListener

            binding.ImageFloor.setImageResource(R.mipmap.floor256)
            binding.toolbarFloor.title = itemFloor.nameFloor
            binding.toolbarFloor.inflateMenu(R.menu.menu_item)

            binding.toolbarFloor.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    (R.id.action_Info) -> {
                        dialogInfoFloor(itemFloor, activity)
                    }
                    (R.id.action_Delete) -> {
                        dialogDeleteFloor(itemFloor, activity, floorViewModel)
                    }
                    (R.id.action_modify) -> {
                        dialogModifyFloor(itemFloor, activity, floorViewModel)
                    }
                }
                true
            })
        }

        private fun dialogModifyFloor(itemFloor: Floor, activity: FragmentActivity?, floorViewModel: FloorsFragmentViewModel) {
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_floor_insert, null)

            mDialogView.editTextInsertFloorNameDia.setText(itemFloor.nameFloor)
            mDialogView.editTextInsertNumberFloorNameDia.setText(itemFloor.nFloor.toString())

            val mbuilder = AlertDialog.Builder(activity).setView(mDialogView)
            mbuilder.setTitle("Floor form")
            val mAlertDialog = mbuilder.show()
            mDialogView.buttonSubmitInsertFloor.setOnClickListener {
                var floor = itemFloor
                floor.nameFloor = mDialogView.editTextInsertFloorNameDia.text.toString()
                floor.nFloor = mDialogView.editTextInsertNumberFloorNameDia.text.toString().toInt()
                floorViewModel.updateFloor(floor)
                floorViewModel.startRefresh()
                mAlertDialog.dismiss()
            }
            mDialogView.buttonCancelInsertFloor.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        private fun dialogInfoFloor(itemFloor: Floor, activity: FragmentActivity?) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Floor info")
            builder.setMessage("Floor Name: " + itemFloor.nameFloor + "\n" + "Floor number: " + itemFloor.nFloor)
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

        private fun dialogDeleteFloor(itemFloor: Floor, activity: FragmentActivity?, floorViewModel: FloorsFragmentViewModel) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Erase Floor?")
            builder.setMessage("You'll lose all elements inside of this floor")
            builder.setPositiveButton("Erase") { dialog, which ->
                floorViewModel.deleteFloor(itemFloor.floorId)
            }
            builder.setNegativeButton("Cancel") {dialog, which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
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