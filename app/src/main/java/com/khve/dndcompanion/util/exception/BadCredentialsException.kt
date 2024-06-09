package com.khve.dndcompanion.util.exception

class BadCredentialsException(private val errorMessage: String) : Exception(errorMessage)