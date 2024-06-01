package com.example.home.domain.entities.teams

sealed class TeamsResult {
    data object Error : TeamsResult()
    data class Success(val teams: List<Team>) : TeamsResult()
}