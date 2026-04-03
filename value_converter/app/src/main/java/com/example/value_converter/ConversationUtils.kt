package com.example.value_converter

object ConversationUtils {
    private val convertedCurrencyValues = mapOf(
        "UAH" to 0.024,
        "RUB" to 0.012,
        "CZK" to 0.048,
        "BYN" to 0.30,
        "EUR" to 1.17,
        "USD" to 1.0,
    )

    private val convertedLengthValues = mapOf(
        "km" to 1000.0,
        "m" to 1.0,
        "cm" to 0.01,
        "mm" to 0.001,
        "ft" to 0.328084,
        "in" to 0.0254,
        "mi" to 1609.34,
        "yd" to 0.9144
    )

    fun calculateUnits(unitsFromSpinner: String, unitsToSpinner: String, valueFrom: String, value: String): String {
        var currentList = convertedCurrencyValues
        if (value == "0") {currentList = convertedLengthValues}

        var fromRate = currentList[unitsFromSpinner]
        var toRate = currentList[unitsToSpinner]

        val valueFromDouble = valueFrom.replace(',', '.').toDoubleOrNull()
        if (valueFromDouble == null || fromRate == null || toRate == null){
            return ""
        }

        val result = (valueFromDouble * fromRate) / toRate
        return "%.2f".format(result)
    }
}
