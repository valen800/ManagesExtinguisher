package org.ieselcaminas.valentin.managesextinguisher.floors


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_building_insert.view.*
import kotlinx.android.synthetic.main.dialog_floor_insert.view.*

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.database.flask.FlaskDao
import org.ieselcaminas.valentin.managesextinguisher.database.floor.Floor
import org.ieselcaminas.valentin.managesextinguisher.database.floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFloorsBinding

/**
 * A simple [Fragment] subclass.
 */
class FloorsFragment : Fragment() {

    companion object {
        fun newInstance() = FloorsFragment()
    }

    private lateinit var appDatabase: ManagesExtinguisherDatabase
    private lateinit var floorsViewModel: FloorsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFloorsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_floors, container, false)
        val application = requireNotNull(this.activity).application
        val args = FloorsFragmentArgs.fromBundle(arguments!!)

        val databaseFloor: FloorDao = ManagesExtinguisherDatabase.getInstance(application).floorDao

        val viewModelFactory = FloorsFragmentViewModelFactory(databaseFloor, application)
        val floorsViewModel = ViewModelProviders.of(this, viewModelFactory).get(FloorsFragmentViewModel::class.java)
        binding.floorsViewModel = floorsViewModel
        binding.setLifecycleOwner(this)

        //Adapter RecyclerView

        val manager = GridLayoutManager(activity, 2)
        binding.floorList.layoutManager = manager

        val adapter = FloorAdapter(FloorListener {
                floorId -> floorsViewModel.startNavigatingToFragmentElements(floorId)
        }, activity, viewLifecycleOwner, floorsViewModel)
        binding.floorList.adapter = adapter

        floorsViewModel.getFloorsFromDataBase(args.buildingId).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //Adapter RecyclerView

        binding.fabCreatorFloor.setOnClickListener() {
            floorsViewModel.startNavigatingToFloorDialog()
        }

        floorsViewModel.navigateToFragmentElements.observe(this, Observer { floorId ->
            floorId?.let {
                this.findNavController().navigate(FloorsFragmentDirections.actionFloorsFragmentToTabFragment(floorId))
                floorsViewModel.doneNavigatingToFragmentElements()
            }
        })

        floorsViewModel.navigateToFloorDialog.observe(this, Observer {
            if (it == true) {
                inserFloorDialong(args.buildingId)
                floorsViewModel.doneNavigatingToFloorDialog()
            }
        })

        floorsViewModel.refresh.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(FloorsFragmentDirections.actionFloorsFragmentSelf(args.buildingId))
                floorsViewModel.doneRefresh()
            }
        })



        return binding.root
    }

    private fun inserFloorDialong(buildingId: Long) {
        val mDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_floor_insert, null)

        val mbuilder = AlertDialog.Builder(activity).setView(mDialogView)
        mbuilder.setTitle("Floor form")
        val mAlertDialog = mbuilder.show()
        mDialogView.buttonSubmitInsertFloor.setOnClickListener {
            var floor = Floor()
            floor.buildingFloorID = buildingId
            floor.nameFloor = mDialogView.editTextInsertFloorNameDia.text.toString()
            floor.nFloor = mDialogView.editTextInsertNumberFloorNameDia.text.toString().toInt()
            floorsViewModel.insertFloor(floor)
            mAlertDialog.dismiss()
        }
        mDialogView.buttonCancelInsertFloor.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floorsViewModel = ViewModelProviders.of(this).get(FloorsFragmentViewModel::class.java)
    }
}
