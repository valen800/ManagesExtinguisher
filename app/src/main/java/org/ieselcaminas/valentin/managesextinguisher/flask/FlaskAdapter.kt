package org.ieselcaminas.valentin.managesextinguisher.flask

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
import org.ieselcaminas.valentin.managesextinguisher.DateUtils
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.SingletonExt
import org.ieselcaminas.valentin.managesextinguisher.SingletonFlask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerFlaskLayoutBinding
import java.util.*

class FlaskAdapter(
    private val clickListener: FlaskListener,
    private val activity: FragmentActivity?,
    private val databaseFlask: FlaskDao,
    private val viewLifecycleOwner: LifecycleOwner,
    private val flaskViewModel: FlaskFragmentViewModel
) : ListAdapter<Flask, FlaskAdapter.ViewHolder>(FlaskDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlaskAdapter.ViewHolder {
        return FlaskAdapter.ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FlaskAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, activity, databaseFlask, viewLifecycleOwner, flaskViewModel, position)
    }

    class ViewHolder private constructor(val binding: RecyclerFlaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            itemFlask: Flask,
            clickListener: FlaskListener,
            activity: FragmentActivity?,
            databaseFlask: FlaskDao,
            viewLifecycleOwner: LifecycleOwner,
            flaskViewModel: FlaskFragmentViewModel,
            position: Int
        ) {
            binding.toolbarFlask.menu.clear()
            binding.flask = itemFlask
            binding.clickListener = clickListener

            binding.ImageFlask.setImageResource(R.mipmap.extintor)
            binding.toolbarFlask.title = itemFlask.nFlask
            binding.toolbarFlask.inflateMenu(R.menu.menu_item_elements)

            binding.textViewFactoryDateFlaskResult.setText(DateUtils.convertLongToTime(itemFlask.factoryDate))
            binding.textViewNextDateFlaskResult.setText(DateUtils.convertLongToTime(itemFlask.dateNextRevision))
            binding.textViewModelFlaskResult.setText(itemFlask.model)
            binding.textViewWeightFlaskResult.setText(itemFlask.contentWeight.toString())

            binding.toolbarFlask.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    (R.id.action_Info) -> {
                        dialogInfoFlask(itemFlask, activity)
                    }
                    (R.id.action_Delete) -> {
                        dialogDeleteFlask(itemFlask, activity, flaskViewModel)
                    }
                    (R.id.action_modify) -> {
                        SingletonFlask.itemFlask = itemFlask
                        flaskViewModel.startNavigatingToFlaskCreatorFromAdapter(itemFlask.flaskId)
                    }
                }
                true
            })
        }

        companion object {
            fun from(parent: ViewGroup): FlaskAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerFlaskLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        private fun dialogDeleteFlask(itemFlask: Flask, activity: FragmentActivity?, flaskViewModel: FlaskFragmentViewModel) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Erase Flask?")
            builder.setMessage("You'll lose this flask")
            builder.setPositiveButton("Erase") { dialog, which ->
                flaskViewModel.deleteFlask(itemFlask.flaskId)
            }
            builder.setNegativeButton("Cancel") {dialog, which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        private fun dialogInfoFlask(itemFlask: Flask, activity: FragmentActivity?) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Flask info")
            builder.setMessage(
                "Number: " + itemFlask.nFlask + "\n\n" + "Powder: " + "TradeMark: " + itemFlask.trademark + "\n"
                        + "Empty Weight: " + itemFlask.emptyWeight + " grams" + "\n"
                        + "Content Weight: " + itemFlask.contentWeight + " grams" + "\n"
                        + "Model: " + itemFlask.model + "\n\n" + "Description Location: " + itemFlask.descriptionLocation + "\n" + "Situation: " + itemFlask.situation + "\n\n"
                        + "Factory Date: " + DateUtils.convertLongToTime(itemFlask.factoryDate) + "\n" + "Date Last Revision: " + DateUtils.convertLongToTime(itemFlask.dateLastRevision) + "\n"
                        + "Date Next Revision: " + DateUtils.convertLongToTime(itemFlask.dateNextRevision) + "\n"
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

class FlaskDiffCallBack : DiffUtil.ItemCallback<Flask>() {
    override fun areItemsTheSame(oldItem: Flask, newItem: Flask): Boolean {
        return oldItem.flaskId == newItem.flaskId
    }

    override fun areContentsTheSame(oldItem: Flask, newItem: Flask): Boolean {
        return oldItem == newItem
    }

}

class FlaskListener(val clickListener: (flaskId: Long) -> Unit) {
    fun onClick(flask: Flask) = clickListener(flask.flaskId)
}