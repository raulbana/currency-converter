package com.appraulbana.currency_converter_app.services
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeAPI {
    @GET("/last/{currency}")
    suspend fun getCurrencyExchange(@Path("currency") currency: String): CurrencyExchangeResponse
}