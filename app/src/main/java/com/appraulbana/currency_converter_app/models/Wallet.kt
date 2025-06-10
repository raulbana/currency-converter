package com.appraulbana.currency_converter_app

object Wallet {
    var saldoBRL: Double = 100_000.00
    var saldoUSD: Double = 50_000.00
    var saldoBTC: Double = 0.5

    fun hasSufficientBalance(currency: String, amount: Double): Boolean {
        return when (currency) {
            "BRL" -> saldoBRL >= amount
            "USD" -> saldoUSD >= amount
            "BTC" -> saldoBTC >= amount
            else -> false
        }
    }

    fun updateBalances(from: String, to: String, amountFrom: Double, amountTo: Double) {
        when (from) {
            "BRL" -> saldoBRL -= amountFrom
            "USD" -> saldoUSD -= amountFrom
            "BTC" -> saldoBTC -= amountFrom
        }
        when (to) {
            "BRL" -> saldoBRL += amountTo
            "USD" -> saldoUSD += amountTo
            "BTC" -> saldoBTC += amountTo
        }
    }
}