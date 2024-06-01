package com.example.home.domain.usecases

import com.example.home.data.repositories.LeaguesRepository
import com.example.home.data.models.leagues.LeaguesResponse
import com.example.home.data.models.leagues.LeagueFromApi
import com.example.home.domain.entities.leagues.LeaguesResult
import io.mockk.coEvery

import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchLeaguesTest {
    private lateinit var leaguesRepository: LeaguesRepository
    private lateinit var fetchLeagues: FetchLeagues

    @Before
    fun setup() {
        leaguesRepository = mockk()
        fetchLeagues = FetchLeagues(leaguesRepository)
    }

    @Test
    fun `invoke returns Success when leagues are not empty`() = runTest {
        // Given
        val leagues = listOf(LeagueFromApi("League1"), LeagueFromApi("League2"))
        coEvery { leaguesRepository.fetchLeagues() } returns flowOf(LeaguesResponse.Success(leagues))

        // When
        val result = fetchLeagues.invoke().first()

        // Then
        assertTrue(result is LeaguesResult.Success)
    }

    @Test
    fun `invoke returns Error when leagues are empty`() = runBlockingTest {
        // Given
        coEvery { leaguesRepository.fetchLeagues() } returns flowOf(LeaguesResponse.Error)

        // When
        val result = fetchLeagues.invoke().first()

        // Then
        assertTrue(result is LeaguesResult.Error)
    }
}