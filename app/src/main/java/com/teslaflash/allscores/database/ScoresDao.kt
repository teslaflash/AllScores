package com.teslaflash.allscores.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao

interface ScoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScoresPlayer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScoresTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScoresMatch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScoresRoster)

    @Update
    suspend fun update(item: ScoresPlayer)

    @Update
    suspend fun update(item: ScoresTransaction)

    @Update
    suspend fun update(item: ScoresMatch)

    @Update
    suspend fun update(item: ScoresRoster)

    @Delete
    suspend fun delete(item: ScoresPlayer)

    @Delete
    suspend fun delete(item: ScoresTransaction)

    @Delete
    suspend fun delete(item: ScoresMatch)

    @Delete
    suspend fun delete(item: ScoresRoster)

    @Query("SELECT * FROM ScoresPlayer ORDER BY fuckUps ASC")
    fun getAllScorePlayers(): LiveData<List<ScoresPlayer>>

    @Query("SELECT * FROM ScoresTransaction ORDER BY id ASC")
    fun getAllScoreTransaction(): LiveData<List<ScoresTransaction>>

    @Query("SELECT * FROM ScoresMatch ORDER BY id ASC")
    fun getAllScoreMatch(): LiveData<List<ScoresMatch>>

    @Query("SELECT * FROM ScoresPlayer WHERE name =:query ORDER BY id ASC")
    fun getScorePlayerByName(query: String): LiveData<List<ScoresPlayer>>

    @Query("SELECT * FROM ScoresTransaction WHERE playerId =:query ORDER BY id ASC")
    fun getScoreTransactionById(query: Int): LiveData<List<ScoresTransaction>>

    @Query("SELECT * FROM ScoresRoster WHERE matchId =:query ORDER BY id ASC")
    fun getScoreRosterById(query: Int): LiveData<List<ScoresRoster>>
}