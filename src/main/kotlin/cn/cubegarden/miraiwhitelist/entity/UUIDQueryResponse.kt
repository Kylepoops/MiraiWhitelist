package cn.cubegarden.miraiwhitelist.entity

import com.google.gson.annotations.SerializedName

data class UUIDQueryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
