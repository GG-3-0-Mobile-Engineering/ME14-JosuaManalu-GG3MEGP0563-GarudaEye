package com.yosha10.final_project.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.yosha10.final_project.R
import com.yosha10.final_project.databinding.ActivityIntroBinding
import com.yosha10.final_project.intro.model.IntroData
import com.yosha10.final_project.main.MainActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var introAdapter: IntroAdapter
    private lateinit var introViewPager: ViewPager
    var position = 0

    private var _activityIntroBinding: ActivityIntroBinding? = null
    private val binding get() = _activityIntroBinding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityIntroBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (getPrefData()) {
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val introDataList: MutableList<IntroData> = ArrayList()
        introDataList.add(
            IntroData(
                getString(R.string.intro_title_1),
                getString(R.string.intro_desc_1),
                getString(R.string.intro_animation_1)
            )
        )
        introDataList.add(
            IntroData(
                getString(R.string.intro_title_2),
                getString(R.string.intro_desc_2),
                getString(R.string.intro_animation_2)
            )
        )
        introDataList.add(
            IntroData(
                getString(R.string.intro_title_3),
                getString(R.string.intro_desc_3),
                getString(R.string.intro_animation_3)
            )
        )

        setIntroViewPagerAdapter(introDataList)

        position = introViewPager.currentItem
        binding.tvNext.setOnClickListener {
            if (position < introDataList.size) {
                position++
                introViewPager.currentItem = position
            }

            if (position == introDataList.size) {
                savePrefData()
                val intent = Intent(this@IntroActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.tabIndicator.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == introDataList.size - 1) {
                    binding.tvNext.text = getString(R.string.get_started)
                } else {
                    binding.tvNext.text = getString(R.string.next)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityIntroBinding = null
    }

    private fun setIntroViewPagerAdapter(introData: List<IntroData>) {
        introViewPager = binding.introViewPager
        introAdapter = IntroAdapter(this@IntroActivity, introData)
        introViewPager.adapter = introAdapter
        binding.tabIndicator.setupWithViewPager(introViewPager)
    }

    private fun savePrefData() {
        sharedPreferences = applicationContext.getSharedPreferences(
            getString(R.string.packageApp),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean(getString(R.string.shared_pref_key_intro), true)
        editor.apply()
    }

    private fun getPrefData(): Boolean {
        sharedPreferences = applicationContext.getSharedPreferences(
            getString(R.string.packageApp),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(getString(R.string.shared_pref_key_intro), false)
    }
}