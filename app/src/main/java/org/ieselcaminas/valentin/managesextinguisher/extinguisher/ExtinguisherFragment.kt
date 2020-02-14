package org.ieselcaminas.valentin.managesextinguisher.extinguisher


import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.SingletonFloorId
import org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager.tabFragmentDirections
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherBinding
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorAdapter
import org.ieselcaminas.valentin.managesextinguisher.floors.FloorListener
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherFragment : Fragment() {

    private lateinit var extinguisher : LiveData<Extinguisher>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher, container, false)
        val activity: FragmentActivity? = activity
        val application = requireNotNull(this.activity).application

        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherFragmentViewModelFactory(databaseExtinguisher, activity, application)
        val extinguisherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherFragmentViewModel::class.java)

        binding.extinguisherViewModel = extinguisherViewModel
        binding.setLifecycleOwner(this)

        //Adapter RecyclerView

        val manager = LinearLayoutManager(context)
        binding.extinguisherList.layoutManager = manager

        val adapter = ExtinguisherAdapter(ExtinguisherListener { extinguisherId ->
            extinguisherViewModel.getExtinguisher(extinguisherId)
            //extinguisher =
            //dialogExtinguisher(extinguisher)
        }, activity)
        binding.extinguisherList.adapter = adapter

        extinguisherViewModel.getExtinguishersFromDataBase(SingletonFloorId.floorIdSingleton).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //Adapter RecyclerView

        binding.fabCreatorExtinguisher.setOnClickListener() {
            extinguisherViewModel.startNavigatingToExtinguisherCreator()
        }

        extinguisherViewModel.navigateToExtinguisherCreator.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(tabFragmentDirections.actionTabFragmentToExtinguisherCreatorFragment2(SingletonFloorId.floorIdSingleton))
                extinguisherViewModel.doneNavigatingToExtinguisherCreator()
            }
        })



        return binding.root
    }

    private fun dialogExtinguisher(extinguisher: LiveData<Extinguisher>) {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Extinguisher info")
        builder.setMessage(
            "Numero: " + extinguisher.value?.nExtinguisher + "\n" + "Powder: " + extinguisher.value?.powder + "\n"
                    + "Weight: " + extinguisher.value?.weight + "\n"
        )
        builder.setNeutralButton("Cancel") { dialog, which ->
            Toast.makeText(activity, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
