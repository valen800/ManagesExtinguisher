package org.ieselcaminas.valentin.managesextinguisher.title

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTitleBinding


class TitleFragmentViewModel : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)

        return binding.root
    }
}