package com.example.demoappcompose.di.network

import java.io.IOException

class ApiException(message: String) : IOException(message)
class UnAuthorisedException(message: String) : IOException(message)