package com.yosha10.final_project.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.yosha10.final_project.R
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.utils.DateFormatter
import com.yosha10.final_project.core.utils.DisasterType
import com.yosha10.final_project.core.utils.DisasterTypeConverter
import com.yosha10.final_project.core.utils.Region
import com.yosha10.final_project.databinding.ActivityDetailDisasterBinding

class DetailDisasterActivity : AppCompatActivity() {

    private var _activityDetailDisasterBinding: ActivityDetailDisasterBinding? = null
    private val binding get() = _activityDetailDisasterBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailDisasterBinding = ActivityDetailDisasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.apply {
            title = getString(R.string.title_detail_disaster_activity)
            setDisplayHomeAsUpEnabled(true)
        }

        val detailDisaster = intent.getParcelableExtra<Disaster>(EXTRA_DATA)
        showDetailDisaster(detailDisaster)
    }

    private fun showDetailDisaster(data: Disaster?){
        data?.let {
            with(binding) {
                tvDetailCreatedAt.text = getString(R.string.created_at_text, DateFormatter.formatDate(data.createdAt))
                tvDetailDisasterType.text = DisasterTypeConverter.getDisasterTypeText(DisasterType.valueOf(data.disasterType.uppercase()))
                tvDetailStatus.text = data.status
                tvDetailLocation.text = Region.listRegions.filterValues { it == data.admin }.keys.first()
                tvDetailDescription.text = data.text
                Glide.with(this@DetailDisasterActivity)
                    .load(data.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(ivDetailImage)
            }
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}