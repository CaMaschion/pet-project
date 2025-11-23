package com.camila.pet_project.domain.usecase

import com.camila.pet_project.domain.model.UserDomain
import com.camila.pet_project.domain.repository.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit tests for LoginUserUseCase
 * Following best practices for unit testing with mocks
 */
class LoginUserUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var loginUserUseCase: LoginUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginUserUseCase = LoginUserUseCase(userRepository)
    }

    @Test
    fun `invoke with valid credentials returns success`() = runTest {
        // Given
        val userName = "testuser"
        val password = "password123"
        val expectedUser = UserDomain(id = 1, userName = userName, password = password)
        `when`(userRepository.authenticateUser(userName, password)).thenReturn(expectedUser)

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
        verify(userRepository).authenticateUser(userName, password)
    }

    @Test
    fun `invoke with invalid credentials returns failure`() = runTest {
        // Given
        val userName = "testuser"
        val password = "wrongpassword"
        `when`(userRepository.authenticateUser(userName, password)).thenReturn(null)

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Invalid username or password", result.exceptionOrNull()?.message)
        verify(userRepository).authenticateUser(userName, password)
    }

    @Test
    fun `invoke with empty username returns failure`() = runTest {
        // Given
        val userName = ""
        val password = "password123"

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Username cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).authenticateUser(anyString(), anyString())
    }

    @Test
    fun `invoke with blank username returns failure`() = runTest {
        // Given
        val userName = "   "
        val password = "password123"

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Username cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).authenticateUser(anyString(), anyString())
    }

    @Test
    fun `invoke with empty password returns failure`() = runTest {
        // Given
        val userName = "testuser"
        val password = ""

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Password cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).authenticateUser(anyString(), anyString())
    }

    @Test
    fun `invoke with blank password returns failure`() = runTest {
        // Given
        val userName = "testuser"
        val password = "   "

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Password cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).authenticateUser(anyString(), anyString())
    }

    @Test
    fun `invoke with repository exception returns failure`() = runTest {
        // Given
        val userName = "testuser"
        val password = "password123"
        val exception = RuntimeException("Database error")
        `when`(userRepository.authenticateUser(userName, password)).thenThrow(exception)

        // When
        val result = loginUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Database error", result.exceptionOrNull()?.message)
        verify(userRepository).authenticateUser(userName, password)
    }
}

