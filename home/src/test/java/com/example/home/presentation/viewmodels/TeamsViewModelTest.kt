package com.example.home.presentation.viewmodels

import com.example.home.domain.entities.teams.Team
import com.example.home.domain.entities.teams.TeamsResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TeamsViewModelTest {
    private lateinit var fetchTeams: FetchTeams
    private lateinit var viewModel: TeamsViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        fetchTeams = mockk()
        viewModel = TeamsViewModel(fetchTeams, dispatcher)
    }

    @Test
    fun `loadTeams returns Success when teams are not empty`() = runTest {
        // Given
        val teams = listOf(Team("Team1", "Image1"), Team("Team2", "Image2"))
        coEvery { fetchTeams.invoke(any()) } returns flowOf(TeamsResult.Success(teams))

        // When
        viewModel.loadTeams("League1")
        advanceUntilIdle()

        // Then
        val expected = TeamsUiState.Success(teams.map { TeamUi(it.teamImage) })
        assertEquals(expected, viewModel.teamsState.value)
    }

    @Test
    fun `loadTeams returns Error when teams are empty`() = runTest {
        // Given
        coEvery { fetchTeams.invoke(any()) } returns flowOf(TeamsResult.Success(emptyList()))

        // When
        viewModel.loadTeams("League1")
        advanceUntilIdle()

        // Then
        assertEquals(TeamsUiState.Error, viewModel.teamsState.value)
    }
}