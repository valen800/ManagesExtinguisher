package org.ieselcaminas.valentin.managesextinguisher.extinguisher


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentBuildingBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherBinding

/**
 * A simple [Fragment] subclass.
 */
class ExtinguisherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExtinguisherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_extinguisher, container, false)



        return binding.root
    }


}
