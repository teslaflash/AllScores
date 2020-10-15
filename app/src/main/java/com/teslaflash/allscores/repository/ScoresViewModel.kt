package com.teslaflash.allscores.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.teslaflash.allscores.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoresViewModel(application: Application):AndroidViewModel(application) {

    private val repository:ScoresRepo

    init {
        val dao = ScoresDatabase.getDatabase(application).dao()
        repository = ScoresRepo(dao,application)
    }

    fun <T: Scores> insert(item: T) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun <T: Scores> update(item: T) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun <T: Scores> delete(item: T) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }

    fun getAllScorePlayer(): LiveData<List<ScoresPlayer>> = repository.getAllScorePlayer()

    fun getAllScoresTransaction(): LiveData<List<ScoresTransaction>> = repository.getAllScoresTransaction()

    fun getAllScoresMatch(): LiveData<List<ScoresMatch>> = repository.getAllScoresMatch()

    fun getScoresPlayerByName(query: String): LiveData<List<ScoresPlayer>> = repository.getScoresPlayerByName(query)

    fun getScoresTransactionById(query: Int): LiveData<List<ScoresTransaction>> = repository.getScoresTransactionById(query)

    fun getScoresRosterById(query: Int): LiveData<List<ScoresRoster>> = repository.getScoresRosterById(query)

}