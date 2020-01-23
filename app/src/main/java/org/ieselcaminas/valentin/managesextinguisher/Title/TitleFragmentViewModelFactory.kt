package org.ieselcaminas.valentin.managesextinguisher.Title

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTitleBinding


class TitleFragmentViewModelFactory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)

        return binding.root
    }
}