package org.ieselcaminas.valentin.managesextinguisher.extinguisher.extinguishercreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding


class ExtinguisherCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher_creator, container, false)

        val application = requireNotNull(this.activity).application
        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherCreatorFragmentViewModelFactory(databaseExtinguisher, application)
        val extinguisherCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherCreatorFragmentViewModel::class.java)

        binding.extinguisherCreatorViewModel = extinguisherCreatorViewModel
        binding.setLifecycleOwner(this)

        binding.buttonSubmitExtinguisher.setOnClickListener() {
            extinguisherCreatorViewModel.insertExtinguisher(
                SingletonFloorId.floorIdSingleton, binding.editTextnExtinguisher.text.toString(), binding.editTextSituation.text.toString(),
                binding.editTextTradeMark.text.toString(), binding.editTextModel.text.toString(), binding.editTextDescriptionLocation.text.toString(),
                binding.editTextWeight.text.toString().toInt(), System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis())
            extinguisherCreatorViewModel.startNavigatingToElements()
        }

        extinguisherCreatorViewModel.navigateToElements.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(ExtinguisherCreatorFragmentDirections.actionExtinguisherCreatorFragment2ToTabFragment(SingletonFloorId.floorIdSingleton))
                extinguisherCreatorViewModel.doneNavigatingToElements()
            }
        })

        return binding.root
    }
}
