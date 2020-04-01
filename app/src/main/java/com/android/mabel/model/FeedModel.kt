package com.android.mabel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed")
data class FeedModel(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val image: String,
    val ingress: String,
    @ColumnInfo(name = "publish_date")
    val dateTime: String,
    val tags: List<String>,
    val created: Int,
    val changed: Int

)