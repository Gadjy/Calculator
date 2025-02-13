package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {
    private val _eguationText = MutableLiveData("")
    val equationText: LiveData<String> = _eguationText

    private val _resultText = MutableLiveData("")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String) {

        _eguationText.value?.let {
            if (btn == "AC") {
                _eguationText.value = ""
                _resultText.value = "0"
                return
            }
            if (btn=="c"){
                if (it.isNotEmpty()){
                    _eguationText.value = it.substring(0,it.length-1)

                }
                return
            }
            if (btn=="="){
                _eguationText.value = _resultText.value
                return
            }

            _eguationText.value = it + btn

            try {
                _resultText.value = calculateResult(_eguationText.value.toString())
            }catch (_: Exception){

            }
        }
    }

    fun calculateResult(equation: String) : String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable,equation,"Javascript", 1, null).toString()
        if (finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        return finalResult
    }
}