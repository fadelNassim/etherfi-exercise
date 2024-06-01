package com.example.home.presentation.viewmodels

import com.example.home.domain.usecases.FetchLeagues
import com.example.home.domain.entities.leagues.League
import com.example.home.domain.entities.leagues.LeaguesResult
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
class LeaguesViewModelTest {
    private lateinit var fetchLeagues: FetchLeagues
    private lateinit var viewModel: LeaguesViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        fetchLeagues = mockk()
        viewModel = LeaguesViewModel(fetchLeagues, dispatcher)
    }

    @Test
    fun `loadLeagues returns Success when leagues are not empty`() = runTest {
        // Given
        val leagues = listOf(League("League1"), League("League2"))
        coEvery { fetchLeagues.invoke() } returns flowOf(LeaguesResult.Success(leagues))

        // When
        viewModel.loadLeagues()
        advanceUntilIdle()

        // Then
        val expected = LeaguesUiState.Success(leagues.map { LeagueUi(it.strLeague) })
        assertEquals(expected, viewModel.leaguesState.value)
    }

    @Test
    fun `loadLeagues returns Error when leagues are empty`() = runTest {
        // Given
        coEvery { fetchLeagues.invoke() } returns flowOf(LeaguesResult.Success(emptyList()))

        // When
        viewModel.loadLeagues()
        advanceUntilIdle()

        // Then
        assertEquals(LeaguesUiState.Error, viewModel.leaguesState.value)
    }
}