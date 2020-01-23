package org.ieselcaminas.valentin.managesextinguisher.Buildings


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.Building.BuildingDao
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.Floor.FloorDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTitleBinding


class BuildingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about, container, false)

        val application = requireNotNull(this.activity).application

        val databaseBuilding: BuildingDao =
            ManagesExtinguisherDatabase.getInstance(application).buildingDao
        val databaseFloor: FloorDao =
            ManagesExtinguisherDatabase.getInstance(application).floorDao

        val viewModelFactory =
            BuildingFragmentViewModelFactory(databaseBuilding, databaseFloor, application)

        val buildingViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(BuildingFragmentViewModel::class.java)

        binding.setLifecycleOwner(this)

        return binding.root
    }
}
