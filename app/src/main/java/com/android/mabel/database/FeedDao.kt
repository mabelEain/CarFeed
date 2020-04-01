package com.android.mabel.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.mabel.model.FeedModel

@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFeeds(feedList : List<FeedModel>)

    @Query("Select * from feed")
    fun gelAllFeeds(): List<FeedModel>
}