package com.example.aula1.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Games")
data class Games(
    @PrimaryKey @NonNull @ColumnInfo(name = "gameId") @SerializedName("gameId") val gameId: String = "",

    @NonNull @ColumnInfo(name = "homeTeamFullName") @SerializedName("homeTeamFullName") val homeTeamFullName: String = "",
    @NonNull @ColumnInfo(name = "homeTeamNickName") @SerializedName("homeTeamNickName") val homeTeamNickName: String = "",
    @NonNull @ColumnInfo(name = "homeTeamScore") @SerializedName("homeTeamScore") val homeTeamScore: Int = 0,
    @NonNull @ColumnInfo(name = "homeTeamAbbreviation") @SerializedName("homeTeamAbbreviation") val homeTeamAbbreviation: String = "",

    @NonNull @ColumnInfo(name = "awayTeamFullName") @SerializedName("awayTeamFullName") val awayTeamFullName: String = "",
    @NonNull @ColumnInfo(name = "awayTeamNickName") @SerializedName("awayTeamNickName") val awayTeamNickName: String = "",
    @NonNull @ColumnInfo(name = "awayTeamScore") @SerializedName("awayTeamScore") val awayTeamScore: Int = 0,
    @NonNull @ColumnInfo(name = "awayTeamAbbreviation") @SerializedName("awayTeamAbbreviation") val awayTeamAbbreviation: String = "",

    @NonNull @ColumnInfo(name = "date") @SerializedName("date") val date: String = "",
    @NonNull @ColumnInfo(name = "dateBR") @SerializedName("dateBR") val dateBR: String = "",
)
