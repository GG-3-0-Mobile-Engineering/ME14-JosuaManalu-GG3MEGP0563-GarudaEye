package com.yosha10.final_project.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yosha10.final_project.databinding.IntroScreenLayoutBinding
import com.yosha10.final_project.intro.model.IntroData

class IntroAdapter(private var context: Context, private var introDataList: List<IntroData>) :
    PagerAdapter() {
    override fun getCount(): Int = introDataList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = IntroScreenLayoutBinding.inflate(LayoutInflater.from(context), container, false)
        view.imageAnimation.setAnimation(introDataList[position].animationImage)
        view.introTvTitle.text = introDataList[position].title
        view.intoTvDescription.text = introDataList[position].description

        container.addView(view.root)
        return view.root
    }
}