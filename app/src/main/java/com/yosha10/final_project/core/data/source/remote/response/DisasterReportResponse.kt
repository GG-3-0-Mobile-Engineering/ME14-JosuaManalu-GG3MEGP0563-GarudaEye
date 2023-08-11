package com.yosha10.final_project.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DisasterReportResponse(

	@field:SerializedName("result")
	val disasterResultResponse: DisasterResultResponse? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DisasterResultResponse(

	@field:SerializedName("objects")
	val disasterObjectsResponse: DisasterObjectsResponse? = null,
)

data class DisasterObjectsResponse(

	@field:SerializedName("output")
	val disasterOutputResponse: DisasterOutputResponse? = null
)

data class DisasterOutputResponse(

	@field:SerializedName("geometries")
	val disasterGeometriesResponse: List<DisasterGeometriesResponse>? = null,
)

data class DisasterGeometriesResponse(

	@field:SerializedName("coordinates")
	val coordinates: List<Double?>? = null,

	@field:SerializedName("properties")
	val disasterResponse: DisasterResponse? = null
)

data class DisasterResponse(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("disaster_type")
	val disasterType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val disasterTagsResponse: DisasterTagsResponse? = null,

	@field:SerializedName("pkey")
	val pkey: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DisasterTagsResponse(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String? = null,
)