package com.appraulbana.currency_converter_app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listAtivos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAtivos = findViewById(R.id.list_ativos)

        updateAtivosList()

        findViewById<Button>(R.id.btn_converter).setOnClickListener {
            startActivity(Intent(this, ConverterActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateAtivosList()
    }

    private fun updateAtivosList() {
        val ativos = listOf(
            "Real (R$): %.2f".format(Wallet.saldoBRL),
            "Dólar ($): %.2f".format(Wallet.saldoUSD),
            "Bitcoin (BTC): %.4f".format(Wallet.saldoBTC)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ativos)
        listAtivos.adapter = adapter
    }
}