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
import org.ieselcaminas.valentin.managesextinguisher.database.ManagesExtinguisherDatabase
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.ExtinguisherDao
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherCreatorBinding
import java.util.*
import android.app.DatePickerDialog
import android.util.Log
import android.widget.*
import org.ieselcaminas.valentin.managesextinguisher.DateUtils
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.SingletonDate
import org.ieselcaminas.valentin.managesextinguisher.SingletonExt
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ExtinguisherCreatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherCreatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher_creator, container, false)

        val application = requireNotNull(this.activity).application
        val databaseExtinguisher: ExtinguisherDao = ManagesExtinguisherDatabase.getInstance(application).extinguisherDao

        val viewModelFactory = ExtinguisherCreatorFragmentViewModelFactory(databaseExtinguisher, application)
        val extinguisherCreatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ExtinguisherCreatorFragmentViewModel::class.java)

        val args = ExtinguisherCreatorFragmentArgs.fromBundle(arguments!!)

        binding.extinguisherCreatorViewModel = extinguisherCreatorViewModel
        binding.setLifecycleOwner(this)

        // SPINNERS
        var spinnerWeightResult = 0
        var spinnerModelResult = ""

        context?.let {
            val contentWeight = arrayListOf(5, 6, 9, 10, 12, 25, 50)
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentWeight)
            binding.spinnerWeight.adapter = arrayAdapter

            binding.spinnerWeight.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    spinnerWeightResult = 5
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinnerWeightResult = contentWeight[p2]
                }
            }
        }

        context?.let {
            val contentModel = arrayListOf("Water spray", "Demineralized water", "Water and foam (AFFF)",
                "Carbon dioxide (CO2)", "Universal chemical powder – ABC", "Dry chemical powder – BC",
                "Chemical powder – D")
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contentModel)
            binding.spinnerModels.adapter = arrayAdapter


            binding.spinnerModels.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    spinnerModelResult = "Model deselected"
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinnerModelResult = contentModel[p2].toString()
                }
            }
        }

        // SPINNERS

        // DATEPICKER
        var factoryDate = System.currentTimeMillis()
        var dateLastRevision = System.currentTimeMillis()
        var dateNextRevision = System.currentTimeMillis()

        binding.buttonFactoryDateExtinguisher.setOnClickListener() {
            var c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            context?.let {
                val dpd = DatePickerDialog(it, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                    //set to TextView
                    var date = "" + mDay + "-" + (mMonth + 1) + "-" + mYear
                    binding.LabelFactoryDate.text = date

                    val date2 = SimpleDateFormat("dd-MM-yyyy").parse(date)
                    factoryDate = date2.time
                    Log.i("TIME", date2.time.toString())
                }, year, month, day)
                dpd.show()
            }
        }

        binding.buttonDateLastRevisionExtinguisher.setOnClickListener() {
            var c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            context?.let {
                val dpd = DatePickerDialog(it, DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                    //set to TextView
                    var dateString = "" + mDay + "-" + (mMonth + 1) + "-" + mYear
                    var dateStringNext = "" + mDay + "-" + (mMonth + 1) + "-" + (mYear + 5)
                    binding.LabelDateLastRevision.text = dateString

                    val dateLastRevisionFormat = SimpleDateFormat("dd-MM-yyyy").parse(dateString)
                    val dateNextRevisionFormat = SimpleDateFormat("dd-MM-yyyy").parse(dateStringNext)
                    dateLastRevision = dateLastRevisionFormat.time
                    dateNextRevision = dateNextRevisionFormat.time
                }, year, month, day)
                dpd.show()
            }
        }

        // DATEPICKER

        // NAVIGATES
        binding.buttonSubmitExtinguisher.setOnClickListener() {
            extinguisherCreatorViewModel.insertExtinguisher(
                SingletonFloorId.floorIdSingleton,
                binding.editTextnExtinguisher.text.toString(),
                binding.editTextSituation.text.toString(),
                binding.editTextTradeMark.text.toString(),
                spinnerModelResult,
                binding.textAreaDescriptionLocationExtinguisher.text.toString(),
                spinnerWeightResult,
                factoryDate,
                dateLastRevision,
                dateNextRevision)

            extinguisherCreatorViewModel.startNavigatingToElements()
        }

        binding.buttonCancelExtinguisher.setOnClickListener() {
            extinguisherCreatorViewModel.startNavigatingToElements()
        }

        extinguisherCreatorViewModel.navigateToElements.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(ExtinguisherCreatorFragmentDirections.actionExtinguisherCreatorFragment2ToTabFragment(SingletonFloorId.floorIdSingleton))
                extinguisherCreatorViewModel.doneNavigatingToElements()
            }
        })
        // NAVIGATES

        return binding.root
    }
}
