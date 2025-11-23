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
 * Unit tests for RegisterUserUseCase
 * Following best practices for unit testing with mocks
 */
class RegisterUserUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        registerUserUseCase = RegisterUserUseCase(userRepository)
    }

    @Test
    fun `invoke with valid data returns success`() = runTest {
        // Given
        val userName = "newuser"
        val password = "password123"
        val expectedUser = UserDomain(id = 1, userName = userName, password = password)
        `when`(userRepository.getUserByUserName(userName)).thenReturn(null)
        `when`(userRepository.registerUser(userName, password)).thenReturn(Result.success(expectedUser))

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
        verify(userRepository).getUserByUserName(userName)
        verify(userRepository).registerUser(userName, password)
    }

    @Test
    fun `invoke with existing username returns failure`() = runTest {
        // Given
        val userName = "existinguser"
        val password = "password123"
        val existingUser = UserDomain(id = 1, userName = userName, password = "oldpassword")
        `when`(userRepository.getUserByUserName(userName)).thenReturn(existingUser)

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Username already exists", result.exceptionOrNull()?.message)
        verify(userRepository).getUserByUserName(userName)
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with empty username returns failure`() = runTest {
        // Given
        val userName = ""
        val password = "password123"

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Username cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).getUserByUserName(anyString())
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with blank username returns failure`() = runTest {
        // Given
        val userName = "   "
        val password = "password123"

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Username cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).getUserByUserName(anyString())
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with empty password returns failure`() = runTest {
        // Given
        val userName = "newuser"
        val password = ""

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Password cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).getUserByUserName(anyString())
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with blank password returns failure`() = runTest {
        // Given
        val userName = "newuser"
        val password = "   "

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Password cannot be empty", result.exceptionOrNull()?.message)
        verify(userRepository, never()).getUserByUserName(anyString())
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with short password returns failure`() = runTest {
        // Given
        val userName = "newuser"
        val password = "123"

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Password must be at least 4 characters", result.exceptionOrNull()?.message)
        verify(userRepository, never()).getUserByUserName(anyString())
        verify(userRepository, never()).registerUser(anyString(), anyString())
    }

    @Test
    fun `invoke with repository exception returns failure`() = runTest {
        // Given
        val userName = "newuser"
        val password = "password123"
        val exception = RuntimeException("Database error")
        `when`(userRepository.getUserByUserName(userName)).thenReturn(null)
        `when`(userRepository.registerUser(userName, password)).thenThrow(exception)

        // When
        val result = registerUserUseCase(userName, password)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Database error", result.exceptionOrNull()?.message)
        verify(userRepository).getUserByUserName(userName)
        verify(userRepository).registerUser(userName, password)
    }
}
