package org.ieselcaminas.valentin.managesextinguisher.flask


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_ext_update.view.*
import kotlinx.android.synthetic.main.dialog_flask_insert.view.*
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.tabFragmentDirections

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFlaskBinding
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherListener

/**
 * A simple [Fragment] subclass.
 */
class FlaskFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlaskBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flask, container, false)

        val activity: FragmentActivity? = activity
        val application = requireNotNull(this.activity).application

        val databaseFlask: FlaskDao = ManagesExtinguisherDatabase.getInstance(application).flaskDao

        val viewModelFactory = FlaskFragmentViewModelFactory(databaseFlask, activity, application)
        val flaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            FlaskFragmentViewModel::class.java)

        binding.flaskViewModel = flaskViewModel

        //Adapter RecyclerView

        val manager = LinearLayoutManager(context)
        binding.flaskList.layoutManager = manager

        val adapter = FlaskAdapter(FlaskListener { flaskId ->
            flaskViewModel.getFlask(flaskId)
        }, activity, databaseFlask, viewLifecycleOwner, flaskViewModel)
        binding.flaskList.adapter = adapter

        flaskViewModel.getFlasksFromDataBase(SingletonFloorId.floorIdSingleton).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        //Observer to navigate TabFragmentSelf and refresh this fragment
        flaskViewModel.refresh.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    tabFragmentDirections.actionTabFragmentSelf(
                        SingletonFloorId.floorIdSingleton))
                flaskViewModel.doneRefresh()
            }
        })
        //Adapter RecyclerView

        binding.fabCreatorFlask.setOnClickListener() {
            dialogInsertFlask(flaskViewModel)
        }

        binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun dialogInsertFlask(flaskViewModel: FlaskFragmentViewModel) {
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_flask_insert, null)

        val mbuilder = AlertDialog.Builder(activity)
            .setView(mDialogView)
        mbuilder.setTitle("Insert Flask")
        val mAlertDialog = mbuilder.show()
        mDialogView.buttonSubmitExtinguisherDialogInsertFlask.setOnClickListener {
            mAlertDialog.dismiss()

            var flask = Flask()
            flask.flaskFloorId = SingletonFloorId.floorIdSingleton
            flask.nFlask = mDialogView.editTextInsertnFlaskDia.text.toString()
            flask.situation = mDialogView.editTextInsertFlaskSituationDia.text.toString()
            flask.powder = mDialogView.editTextInsertFlaskPowderDia.text.toString()
            flask.trademark = mDialogView.editTextInsertFlaskTradeMarkDia.text.toString()
            flask.model = mDialogView.editTextInsertFlaskModelDia.text.toString()
            flask.descriptionLocation = mDialogView.editTextInsertFlaskDescriptionLocationDia.text.toString()
            flask.emptyWeight = mDialogView.editTextInsertFlaskEmptyWeightDia.text.toString().toInt()
            flask.totalWeight = mDialogView.editTextInsertFlaskTotalWeightDia.text.toString().toInt()
            flask.factoryDate = System.currentTimeMillis()
            flask.dateLastRevision = System.currentTimeMillis()
            flask.dateNextRevision = System.currentTimeMillis()

            flaskViewModel.introduceFlask(flask)
            flaskViewModel.startRefresh()
        }
        mDialogView.buttonCancelDialogInsertFlask.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


}
