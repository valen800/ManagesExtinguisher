package org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import org.ieselcaminas.valentin.managesextinguisher.R
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentExtinguisherBinding
import org.ieselcaminas.valentin.managesextinguisher.databinding.FragmentTabBinding

/**
 * A simple [Fragment] subclass.
 */
class tabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false)

        val viewPager = binding.pager as ViewPager
        viewPager.adapter = FragmentAdapterPager(childFragmentManager)
        viewPager.setPageTransformer(true, ZoomPageTransformer())

        val tabLayout = binding.appbartabs as TabLayout
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }


}
