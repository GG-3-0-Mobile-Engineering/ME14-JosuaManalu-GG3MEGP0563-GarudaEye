package com.yosha10.final_project.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosha10.final_project.R
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import com.yosha10.final_project.core.utils.DisasterType
import com.yosha10.final_project.databinding.ItemListDisasterBinding
import java.util.Locale

class DisasterListAdapter : RecyclerView.Adapter<DisasterListAdapter.DisasterListViewHolder>() {

    private var listData = ArrayList<UrunDayaReport>()
    var onItemClick: ((UrunDayaReport) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<UrunDayaReport>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterListViewHolder {
        val binding =
            ItemListDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisasterListViewHolder(binding)
    }

    override fun getItemCount(): Int =
        listData.size


    override fun onBindViewHolder(holder: DisasterListViewHolder, position: Int) {
        holder.bind(listData[position])
    }


    @Suppress("DEPRECATION")
    inner class DisasterListViewHolder(private val binding: ItemListDisasterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UrunDayaReport) {
            with(binding) {
                itemTvTitle.text = checkIsNull(
                    data.properties.title,
                    itemView.context.getString(R.string.no_title)
                )
                itemTvDescription.text = checkIsNull(
                    data.properties.text,
                    itemView.context.getString(R.string.no_description)
                )
                val disasterType = data.properties.disaster_type
                val disasterTypeText = when(DisasterType.valueOf(disasterType.uppercase())) {
                    // the color of the badge card will change based on disaster type
                    DisasterType.FLOOD -> DisasterType.FLOOD.IDvalue
                    DisasterType.EARTHQUAKE -> DisasterType.EARTHQUAKE.IDvalue
                    DisasterType.FIRE -> DisasterType.FIRE.IDvalue
                    DisasterType.HAZE -> DisasterType.HAZE.IDvalue
                    DisasterType.WIND -> DisasterType.WIND.IDvalue
                    DisasterType.VOLCANO -> DisasterType.VOLCANO.IDvalue
                }
                itemTvDisasterType.text = disasterTypeText

                Glide.with(itemView.context)
                    .load(data.properties.image_url)
                    .placeholder(R.drawable.placeholder_image)
                    .into(itemIvDisaster)


                val colorBadge = when(DisasterType.valueOf(disasterType.uppercase())) {
                    // the color of the badge card will change based on disaster type
                    DisasterType.FLOOD -> DisasterType.FLOOD.colorResId
                    DisasterType.EARTHQUAKE -> DisasterType.EARTHQUAKE.colorResId
                    DisasterType.FIRE -> DisasterType.FIRE.colorResId
                    DisasterType.HAZE -> DisasterType.HAZE.colorResId
                    DisasterType.WIND -> DisasterType.WIND.colorResId
                    DisasterType.VOLCANO -> DisasterType.VOLCANO.colorResId
                }

                disasterTypeBadge.setCardBackgroundColor(ContextCompat.getColor(itemView.context, colorBadge))


            }
        }

        private fun checkIsNull(data: String, dataElse: String): String =
            data.ifEmpty { dataElse }


        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}