package com.teslaflash.allscores.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.teslaflash.allscores.database.*

class ScoresRepo(dao: ScoresDao, val application: Application) {

    val db = ScoresDatabase.getDatabase(application)
    private val dao = ScoresDatabase.getDatabase(application).dao()

    suspend fun <T: Scores> insert(item: T) {
        when (item) {
            is ScoresPlayer -> dao.insert(item)
            is ScoresTransaction -> dao.insert(item)
            is ScoresMatch -> dao.insert(item)
            is ScoresRoster -> dao.insert(item)
        }
    }

    suspend fun <T: Scores> update(item: T) {
        when (item) {
            is ScoresPlayer -> dao.update(item)
            is ScoresTransaction -> dao.update(item)
            is ScoresMatch -> dao.update(item)
            is ScoresRoster -> dao.update(item)
        }
    }

    suspend fun <T: Scores> delete(item: T) {
        when (item) {
            is ScoresPlayer -> dao.delete(item)
            is ScoresTransaction -> dao.delete(item)
            is ScoresMatch -> dao.delete(item)
            is ScoresRoster -> dao.delete(item)
        }
    }

    fun getAllScorePlayer():LiveData<List<ScoresPlayer>> = dao.getAllScorePlayers()

    fun getAllScoresTransaction():LiveData<List<ScoresTransaction>> = dao.getAllScoreTransaction()

    fun getAllScoresMatch():LiveData<List<ScoresMatch>> = dao.getAllScoreMatch()

    fun getScoresPlayerByName(query: String):LiveData<List<ScoresPlayer>> = dao.getScorePlayerByName(query)

    fun getScoresTransactionById(query: Int):LiveData<List<ScoresTransaction>> = dao.getScoreTransactionById(query)

    fun getScoresRosterById(query: Int):LiveData<List<ScoresRoster>> = dao.getScoreRosterById(query)

}