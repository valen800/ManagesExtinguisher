package org.ieselcaminas.valentin.managesextinguisher.flask


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentFlaskBinding

/**
 * A simple [Fragment] subclass.
 */
class FlaskFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlaskBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flask, container, false)

        return binding.root
    }


}
