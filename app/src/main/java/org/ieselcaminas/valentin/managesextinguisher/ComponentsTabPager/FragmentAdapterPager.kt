package org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.extinguisher.ExtinguisherFragment
import org.ieselcaminas.valentin.managesextinguisher.flask.FlaskFragment

class FragmentAdapterPager(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    internal val PAGE_COUNT = 2
    private val tabTitles = arrayOf("Extinguishers", "Flasks")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = ExtinguisherFragment()
            1 -> fragment = FlaskFragment()
        }

        return fragment!!
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}