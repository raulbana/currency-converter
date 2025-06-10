package com.appraulbana.currency_converter_app.services

import com.appraulbana.currency_converter_app.models.CurrencyExchange

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

typealias CurrencyExchangeResponse = Map<String, CurrencyExchange>

class AwesomeAPIService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://economia.awesomeapi.com.br/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: AwesomeAPI = retrofit.create(AwesomeAPI::class.java)

    var saldoBRL: Double = 100_000.00
    var saldoUSD: Double = 50_000.00
    var saldoBTC: Double = 0.5

    suspend fun getCurrencyExchange(currency: String): CurrencyExchangeResponse? {
        return try {
            api.getCurrencyExchange(currency)
        } catch (e: Exception) {
            null
        }
    }

    fun hasSufficientBalance(moedaOrigem: String, valor: Double): Boolean {
        return when (moedaOrigem) {
            "BRL" -> saldoBRL >= valor
            "USD" -> saldoUSD >= valor
            "BTC" -> saldoBTC >= valor
            else -> false
        }
    }

    fun updateBalances(moedaOrigem: String, moedaDestino: String, valorOrigem: Double, valorDestino: Double) {
        when (moedaOrigem) {
            "BRL" -> saldoBRL -= valorOrigem
            "USD" -> saldoUSD -= valorOrigem
            "BTC" -> saldoBTC -= valorOrigem
        }
        when (moedaDestino) {
            "BRL" -> saldoBRL += valorDestino
            "USD" -> saldoUSD += valorDestino
            "BTC" -> saldoBTC += valorDestino
        }
    }
}