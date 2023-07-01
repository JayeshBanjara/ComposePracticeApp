package com.example.demoappcompose.network

import java.io.IOException

class ApiException(message: String) : IOException(message)
class UnAuthorisedException(message: String) : IOException(message)