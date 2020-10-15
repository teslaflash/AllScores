package com.teslaflash.allscores.database

import androidx.room.Entity
import androidx.room.PrimaryKey

open interface Scores

@Entity(tableName = "ScoresPlayer")
data class ScoresPlayer(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var balance: Int = 0,
    var games: Int = 0,
    var phone: String? = null,
    var fuckUps: Int = 0
): Scores

@Entity(tableName = "ScoresTransaction")
data class ScoresTransaction(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var playerId: Int,
    var playerName: String,
    var balanceChange: Int,
    var date: String
): Scores

@Entity(tableName = "ScoresMatch")
data class ScoresMatch(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var date: String? = null,
    var title: String? = null,
    var price: Int = 0,
    var playerCount: Int = 0,
    var isComplete: Boolean = false
): Scores

@Entity(tableName = "ScoresRoster")
data class ScoresRoster(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var matchId: Int,
    var playerId: Int,
    var playerName: String
): Scores