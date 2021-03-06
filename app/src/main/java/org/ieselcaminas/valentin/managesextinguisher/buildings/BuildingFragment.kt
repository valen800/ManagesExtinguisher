package org.ieselcaminas.valentin.managesextinguisher.buildings

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_building_insert.view.*
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.tabFragmentDirections
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.buildingsdatabase.Building
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentBuildingBinding

class BuildingFragment : Fragment() {

    private lateinit var appDatabase: ManagesExtinguisherDatabase
    private lateinit var buildingViewModel: BuildingFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentBuildingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_building, container, false)

        val application = requireNotNull(this.activity).application
        val databaseBuilding: BuildingDao = ManagesExtinguisherDatabase.getInstance(application).buildingDao

        val viewModelFactory = BuildingFragmentViewModelFactory(databaseBuilding, application)
        val buildingViewModel = ViewModelProviders.of(this, viewModelFactory).get(BuildingFragmentViewModel::class.java)
        binding.buildingViewModel = buildingViewModel

        //Adapter RecyclerView
        val manager = GridLayoutManager(activity, 2)
        binding.buildingList.layoutManager = manager

        val adapter = BuildingAdapter(BuildingListener {
            buildingId -> buildingViewModel.startNavigatingToFloors(buildingId)
        }, activity, viewLifecycleOwner, buildingViewModel)
        binding.buildingList.adapter = adapter

        buildingViewModel.buildingsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        //Adapter RecyclerView

        binding.fabCreatorBuilding.setOnClickListener() {
            buildingViewModel.startNavigatingToInserDialog()
        }

        buildingViewModel.navigateToFloors.observe(this, Observer { buildingId ->
            buildingId?.let {
                this.findNavController().navigate(BuildingFragmentDirections.actionBuildingFragmentToFloorsFragment2(buildingId))
                buildingViewModel.doneNavigatingToFloors()
            }
        })

        buildingViewModel.navigatingToInserDialog.observe(this, Observer {
            if (it == true) {
                inserBuildingDialong()
                buildingViewModel.doneNavigatingToInserDialog()
            }
        })

        buildingViewModel.refresh.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(BuildingFragmentDirections.actionBuildingFragmentSelf())
                buildingViewModel.doneRefresh()
            }
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }

    private fun inserBuildingDialong() {
        val mDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_building_insert, null)

        val mbuilder = AlertDialog.Builder(activity).setView(mDialogView)
        mbuilder.setTitle("Building form")
        val mAlertDialog = mbuilder.show()
        mDialogView.buttonSubmitInsertBuilding.setOnClickListener {
            var building = Building()
            building.nameBuildings = mDialogView.editTextInsertBuildingNameDia.text.toString()
            buildingViewModel.insertBuilding(building)
            mAlertDialog.dismiss()
        }
        mDialogView.buttonCancelInsertBuilding.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buildingViewModel = ViewModelProviders.of(this).get(BuildingFragmentViewModel::class.java)
    }
}
