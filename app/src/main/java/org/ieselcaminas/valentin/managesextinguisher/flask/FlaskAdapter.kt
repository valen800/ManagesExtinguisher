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
import kotlinx.android.synthetic.main.dialog_ext_update.view.*
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerExtinguisherLayoutBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.RecyclerFlaskLayoutBinding
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherAdapter
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherFragmentViewModel
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherListener

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
            binding.flask = itemFlask
            binding.clickListener = clickListener

            binding.ImageFlask.setImageResource(R.drawable.extintor)
            binding.toolbarFlask.title = itemFlask.nFlask
            binding.toolbarFlask.inflateMenu(R.menu.menu_item_elements)

            /*binding.toolbarExtinguisher.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    (R.id.action_Info) -> {
                        dialogInfoExtinguisher(itemExt, activity)
                    }
                    (R.id.action_Delete) -> {
                        extinguisherViewModel.deleteExt(itemExt.extinguisherId)
                    }
                    (R.id.action_modify) -> {
                        dialogInsertModifyExt(itemExt, activity, extinguisherViewModel, position)
                    }
                }
                true
            })*/
        }

        companion object {
            fun from(parent: ViewGroup): FlaskAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    RecyclerFlaskLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        /*private fun dialogInsertModifyExt(
            itemExt: Extinguisher,
            activity: FragmentActivity?,
            extinguisherViewModel: ExtinguisherFragmentViewModel,
            position: Int
        ) {
            val mDialogView =
                LayoutInflater.from(activity).inflate(R.layout.dialog_ext_update, null)
            mDialogView.editTextnExtinguisherDia.setText(itemExt.nExtinguisher)
            mDialogView.editTextSituationDia.setText(itemExt.situation)
            mDialogView.editTextPowderDia.setText(itemExt.powder)
            mDialogView.editTextTradeMarkDia.setText(itemExt.trademark)
            mDialogView.editTextModelDia.setText(itemExt.model)
            mDialogView.editTextDescriptionLocationDia.setText(itemExt.descriptionLocation)
            mDialogView.editTextWeightDia.setText(itemExt.weight.toString())

            val mbuilder = AlertDialog.Builder(activity)
                .setView(mDialogView)
            mbuilder.setTitle("Modify Extinguisher")
            val mAlertDialog = mbuilder.show()
            mDialogView.buttonSubmitExtinguisherDialog.setOnClickListener {
                mAlertDialog.dismiss()
                var ext = itemExt
                ext.nExtinguisher = mDialogView.editTextnExtinguisherDia.text.toString()
                ext.situation = mDialogView.editTextSituationDia.text.toString()
                ext.powder = mDialogView.editTextPowderDia.text.toString()
                ext.trademark = mDialogView.editTextTradeMarkDia.text.toString()
                ext.model = mDialogView.editTextModelDia.text.toString()
                ext.descriptionLocation = mDialogView.editTextDescriptionLocationDia.text.toString()
                ext.weight = mDialogView.editTextWeightDia.text.toString().toInt()

                extinguisherViewModel.modifyExt(ext)
                extinguisherViewModel.startRefresh()
            }
            mDialogView.buttonCancelDialog.setOnClickListener {
                mAlertDialog.dismiss()
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
        }*/

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