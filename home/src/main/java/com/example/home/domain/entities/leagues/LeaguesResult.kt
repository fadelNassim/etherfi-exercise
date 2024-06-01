package com.example.home.domain.entities.leagues

sealed class LeaguesResult {
    data object Error : LeaguesResult()
    data class Success(val leagues: List<League>) : LeaguesResult()
}