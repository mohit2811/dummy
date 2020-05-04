package com.chaufferapplication.adapters

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.FutureAlarm
import com.example.myapplication.PastAlarm

class ViewPagerAdapter(fm: FragmentManager, private val tabCount: Int) :
    FragmentStatePagerAdapter(fm) {
    private val tabTitles =
        arrayOf("Past", "Future")

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->PastAlarm()
            1 -> FutureAlarm()
            else -> null
        }!!
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun saveState(): Parcelable? {
        return null
    }

}