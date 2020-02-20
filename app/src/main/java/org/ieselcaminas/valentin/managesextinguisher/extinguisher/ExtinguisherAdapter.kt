package org.ieselcaminas.valentin.managesextinguisher.extinguisher

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
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerExtinguisherLayoutBinding


class ExtinguisherAdapter(
    private val clickListener: ExtinguisherListener,
    private val activity: FragmentActivity?,
    private val databaseExtinguisher: ExtinguisherDao,
    private val viewLifecycleOwner: LifecycleOwner,
    private val extinguisherViewModel: ExtinguisherFragmentViewModel

) : ListAdapter<Extinguisher, ExtinguisherAdapter.ViewHolder>(ExtinguisherDiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtinguisherAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExtinguisherAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, activity, databaseExtinguisher, viewLifecycleOwner, extinguisherViewModel, position)
    }

    class ViewHolder private constructor(val binding: RecyclerExtinguisherLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            itemExt: Extinguisher,
            clickListener: ExtinguisherListener,
            activity: FragmentActivity?,
            databaseExtinguisher: ExtinguisherDao,
            viewLifecycleOwner: LifecycleOwner,
            extinguisherViewModel: ExtinguisherFragmentViewModel,
            position: Int
        ) {
            binding.extinguisher = itemExt
            binding.clickListener = clickListener

            binding.ImageExtinguisher.setImageResource(R.mipmap.extintor)
            binding.toolbarExtinguisher.title = itemExt.nExtinguisher
            binding.toolbarExtinguisher.inflateMenu(R.menu.menu_item_elements)

            binding.toolbarExtinguisher.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    (R.id.action_Info) -> {
                        dialogInfoExtinguisher(itemExt, activity)
                    }
                    (R.id.action_Delete) -> {
                        extinguisherViewModel.deleteExt(itemExt.extinguisherId)
                    }
                    (R.id.action_modify) -> {
                        //dialogModifyExt(itemExt, activity, extinguisherViewModel, position)
                    }
                }
                true
            })
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerExtinguisherLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        private fun dialogInfoExtinguisher(itemExt: Extinguisher, activity: FragmentActivity?) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Extinguisher info")
            builder.setMessage(
                "Numero: " + itemExt.nExtinguisher + "\n\n" + "Powder: " + itemExt.powder + "\n" + "TradeMark: " + itemExt.trademark + "\n" + "Weight: " + itemExt.weight + " Kg" + "\n"
                        + "Model: " + itemExt.model + "\n\n" + "Description Location: " + itemExt.descriptionLocation + "\n" + "Situation: " + itemExt.situation + "\n\n"
                        + "Factory Date: " + itemExt.factoryDate + "\n" + "Date Last Revision: " + itemExt.dateLastRevision + "\n"
                        + "Date Next Revision: " + itemExt.dateNextRevision + "\n"
            )
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