package com.appraulbana.currency_converter_app

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.appraulbana.currency_converter_app.services.AwesomeAPIService
import kotlinx.coroutines.launch

class ConverterActivity : AppCompatActivity() {
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var edtAmount: EditText
    private lateinit var btnConvert: Button
    private lateinit var btnBuy: Button
    private lateinit var progressBar: ProgressBar
    private val service = AwesomeAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        spinnerFrom = findViewById(R.id.spinner_from)
        spinnerTo = findViewById(R.id.spinner_to)
        edtAmount = findViewById(R.id.edt_amount)
        btnConvert = findViewById(R.id.btn_convert)
        btnBuy = findViewById(R.id.btn_buy)
        progressBar = findViewById(R.id.progress_bar)

        val moedas = arrayOf("BRL", "USD", "BTC")
        spinnerFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moedas)
        spinnerTo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moedas)

        btnConvert.setOnClickListener {
            handleConversion(isBuy = false)
        }
        btnBuy.setOnClickListener {
            handleConversion(isBuy = true)
        }
    }

    private fun showMessage(msg: String, onOk: (() -> Unit)? = null) {
    AlertDialog.Builder(this)
        .setMessage(msg)
        .setPositiveButton("OK") { _, _ -> onOk?.invoke() }
        .show()
}

private fun handleConversion(isBuy: Boolean) {
    val from = spinnerFrom.selectedItem.toString()
    val to = spinnerTo.selectedItem.toString()
    val amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0

    if (from == to) {
        showMessage("Escolha moedas diferentes.")
        return
    }
    if (amount <= 0) {
        showMessage("Informe um valor válido.")
        return
    }

    val origem = if (isBuy) to else from
    val destino = if (isBuy) from else to

    if (!Wallet.hasSufficientBalance(origem, amount)) {
        showMessage("Saldo insuficiente.")
        return
    }

    progressBar.visibility = View.VISIBLE
    lifecycleScope.launch {
        try {
            val directPairKey = "${origem}-${destino}"
            val inversePairKey = "${destino}-${origem}"
            val directPair = "${origem}${destino}"
            val inversePair = "${destino}${origem}"

            val directResponse = service.getCurrencyExchange(directPairKey)
            val directExchange = directResponse?.get(directPair)
            if (directExchange != null) {
                val rate = directExchange.bid.toDoubleOrNull() ?: throw Exception("Cotação inválida")
                val converted = amount * rate
                Wallet.updateBalances(origem, destino, amount, converted)
                showMessage(
                    "Convertido: %s".format(
                        when (destino) {
                            "BRL" -> "R$ %.2f".format(converted)
                            "USD" -> "$ %.2f".format(converted)
                            "BTC" -> "BTC %.4f".format(converted)
                            else -> "%.2f".format(converted)
                        }
                    )
                ) { finish() }
                return@launch
            }

            val inverseResponse = service.getCurrencyExchange(inversePairKey)
            val inverseExchange = inverseResponse?.get(inversePair)
            if (inverseExchange != null) {
                val inverseRate = inverseExchange.bid.toDoubleOrNull() ?: throw Exception("Cotação inválida")
                if (inverseRate == 0.0) throw Exception("Cotação inválida")
                val rate = 1 / inverseRate
                val converted = amount * rate
                Wallet.updateBalances(origem, destino, amount, converted)
                showMessage(
                    "Convertido: %s".format(
                        when (destino) {
                            "BRL" -> "R$ %.2f".format(converted)
                            "USD" -> "$ %.2f".format(converted)
                            "BTC" -> "BTC %.4f".format(converted)
                            else -> "%.2f".format(converted)
                        }
                    )
                ) { finish() }
                return@launch
            }

            showMessage("Conversão não disponível para este par de moedas.")
        } catch (e: Exception) {
            showMessage("Erro: ${e.message}")
        } finally {
            progressBar.visibility = View.GONE
        }
    }
}
}