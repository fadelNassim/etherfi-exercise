package com.example.home.domain.usecases

import com.example.home.data.repositories.LeaguesRepository
import com.example.home.data.models.teams.TeamsResponse
import com.example.home.data.models.teams.TeamFromApi
import com.example.home.domain.entities.teams.TeamsResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchTeamsTest {
    private lateinit var leaguesRepository: LeaguesRepository
    private lateinit var fetchTeams: FetchTeams

    @Before
    fun setup() {
        leaguesRepository = mockk()
        fetchTeams = FetchTeams(leaguesRepository)
    }

    @Test
    fun `invoke returns Success when teams are not empty`() = runTest {
        // Given
        val teams = listOf(TeamFromApi("Team1", "Image1"), TeamFromApi("Team2", "Image2"))
        coEvery { leaguesRepository.fetchTeamsFromLeagueName(any()) } returns flowOf(TeamsResponse.Success(teams))

        // When
        val result = fetchTeams.invoke("League1").first()

        // Then
        assertTrue(result is TeamsResult.Success)
    }

    @Test
    fun `invoke returns Error when teams are empty`() = runBlockingTest {
        // Given
        coEvery { leaguesRepository.fetchTeamsFromLeagueName(any()) } returns flowOf(TeamsResponse.Error)

        // When
        val result = fetchTeams.invoke("League1").first()

        // Then
        assertTrue(result is TeamsResult.Error)
    }

    @Test
    fun `invoke returns teams sorted in reverse alphabetical order and skips 1 out of 2 teams`() = runTest {
        // Given
        val teams = listOf(
            TeamFromApi("TeamA", "ImageA"),
            TeamFromApi("TeamB", "ImageB"),
            TeamFromApi("TeamC", "ImageC"),
            TeamFromApi("TeamD", "ImageD")
        )
        coEvery { leaguesRepository.fetchTeamsFromLeagueName(any()) } returns flowOf(TeamsResponse.Success(teams))

        // When
        val result = fetchTeams.invoke("League1").first()

        // Then
        assertTrue(result is TeamsResult.Success)
        result as TeamsResult.Success
        assertEquals(2, result.teams.size)
        assertEquals("TeamD", result.teams[0].teamName)
        assertEquals("TeamB", result.teams[1].teamName)
    }
}