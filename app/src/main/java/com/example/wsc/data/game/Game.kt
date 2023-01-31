package com.example.wsc.data.game

import com.google.gson.annotations.SerializedName

data class Game @JvmOverloads constructor(
    @SerializedName("fixture")
    val fixture: Fixture,
    @SerializedName("goals")
    val goals: Goals,
    @SerializedName("league")
    val league: League,
    @SerializedName("score")
    val score: Score,
    @SerializedName("storifyMeHandle")
    val storifyMeHandle: String,
    @SerializedName("storifyMeID")
    val storifyMeID: Int,
    @SerializedName("teams")
    val teams: Teams,
    @SerializedName("WSCGameId")
    val id: String,
    @SerializedName("wscGame")
    val wscGame: WscGame
) {
    data class Fixture(
        @SerializedName("date")
        val date: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("periods")
        val periods: Periods,
        @SerializedName("referee")
        val referee: String,
        @SerializedName("status")
        val status: Status,
        @SerializedName("timestamp")
        val timestamp: Int,
        @SerializedName("timezone")
        val timezone: String,
        @SerializedName("venue")
        val venue: Venue
    ) {
        data class Periods(
            @SerializedName("first")
            val first: Any,
            @SerializedName("second")
            val second: Any
        )

        data class Status(
            @SerializedName("elapsed")
            val elapsed: Any,
            @SerializedName("long")
            val long: String,
            @SerializedName("short")
            val short: String
        )

        data class Venue(
            @SerializedName("city")
            val city: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        )
    }

    data class Goals(
        @SerializedName("away")
        val away: Any,
        @SerializedName("home")
        val home: Any
    )

    data class League(
        @SerializedName("country")
        val country: String,
        @SerializedName("flag")
        val flag: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo")
        val logo: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("round")
        val round: String,
        @SerializedName("season")
        val season: Int
    )

    data class Score(
        @SerializedName("extratime")
        val extratime: Extratime,
        @SerializedName("fulltime")
        val fulltime: Fulltime,
        @SerializedName("halftime")
        val halftime: Halftime,
        @SerializedName("penalty")
        val penalty: Penalty
    ) {
        data class Extratime(
            @SerializedName("away")
            val away: Any,
            @SerializedName("home")
            val home: Any
        )

        data class Fulltime(
            @SerializedName("away")
            val away: Any,
            @SerializedName("home")
            val home: Any
        )

        data class Halftime(
            @SerializedName("away")
            val away: Any,
            @SerializedName("home")
            val home: Any
        )

        data class Penalty(
            @SerializedName("away")
            val away: Any,
            @SerializedName("home")
            val home: Any
        )
    }

    data class Teams(
        @SerializedName("away")
        val away: Away,
        @SerializedName("home")
        val home: Home
    ) {
        data class Away(
            @SerializedName("id")
            val id: Int,
            @SerializedName("logo")
            val logo: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("winner")
            val winner: Any
        )

        data class Home(
            @SerializedName("id")
            val id: Int,
            @SerializedName("logo")
            val logo: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("winner")
            val winner: Any
        )
    }

    data class WscGame(
        @SerializedName("awayTeamName")
        val awayTeamName: String,
        @SerializedName("gameId")
        val gameId: String,
        @SerializedName("gameTime")
        val gameTime: String,
        @SerializedName("homeTeamName")
        val homeTeamName: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("primeStory")
        val primeStory: PrimeStory
    ) {
        data class PrimeStory(
            @SerializedName("pages")
            val pages: List<Page>,
            @SerializedName("publishDate")
            val publishDate: String,
            @SerializedName("storyId")
            val storyId: String,
            @SerializedName("storyThumbnail")
            val storyThumbnail: String,
            @SerializedName("storyThumbnailSquare")
            val storyThumbnailSquare: String,
            @SerializedName("title")
            val title: String
        ) {
            data class Page(
                @SerializedName("actionType")
                val actionType: String,
                @SerializedName("awayScore")
                val awayScore: Int,
                @SerializedName("duration")
                val duration: Int,
                @SerializedName("eventType")
                val eventType: String,
                @SerializedName("gameClock")
                val gameClock: String,
                @SerializedName("homeScore")
                val homeScore: Int,
                @SerializedName("paggeId")
                val paggeId: String,
                @SerializedName("period")
                val period: String,
                @SerializedName("rating")
                val rating: Int,
                @SerializedName("title")
                val title: String,
                @SerializedName("videoUrl")
                val videoUrl: String
            )
        }
    }
}
