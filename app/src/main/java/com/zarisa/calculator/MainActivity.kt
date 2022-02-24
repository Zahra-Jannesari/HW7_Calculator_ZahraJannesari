package com.zarisa. calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.zarisa.calculator.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var lastNumeric: Boolean = false
    var stateError: Boolean = false
    var hasDot: Boolean = false
    var hasOneOperator:Boolean=false
    var operator=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        onClickListeners()
        reset()
    }
    private fun reset(){
        lastNumeric= false
        stateError= false
        hasDot= false
        hasOneOperator=false
        operator=""
    }
    private fun onClickListeners(){
        binding.buttonDelete?.setOnClickListener { deleteEvent() }
        binding.buttonEqual?.setOnClickListener { equalEvent() }
        binding.buttonDiv?.setOnClickListener {operatorEvent(binding.buttonDiv!!)}
        binding.buttonMul?.setOnClickListener { operatorEvent(binding.buttonMul!!) }
        binding.buttonSub?.setOnClickListener { operatorEvent(binding.buttonSub!!) }
        binding.buttonAdd?.setOnClickListener { operatorEvent(binding.buttonAdd!!) }
        binding.buttonDot?.setOnClickListener { dotEvent() }
        binding.buttonZero?.setOnClickListener { numberEvent(binding.buttonZero!!) }
        binding.buttonOne?.setOnClickListener { numberEvent(binding.buttonOne!!) }
        binding.buttonTwo?.setOnClickListener { numberEvent(binding.buttonTwo!!) }
        binding.buttonThree?.setOnClickListener { numberEvent(binding.buttonThree!!) }
        binding.buttonFour?.setOnClickListener { numberEvent(binding.buttonFour!!) }
        binding.buttonFive?.setOnClickListener { numberEvent(binding.buttonFive!!) }
        binding.buttonSix?.setOnClickListener { numberEvent(binding.buttonSix!!) }
        binding.buttonSeven?.setOnClickListener { numberEvent(binding.buttonSeven!!) }
        binding.buttonEight?.setOnClickListener { numberEvent(binding.buttonEight!!) }
        binding.buttonNine?.setOnClickListener { numberEvent(binding.buttonNine!!) }
    }

    private fun numberEvent(view: View){
        Toast.makeText(this,(view as Button).text, Toast.LENGTH_SHORT).show()
        if (stateError) {
            // If current state is Error, replace the error message
            binding.tv?.text=""
            binding.tv?.text = view.text.toString()
            stateError = false
        } else {
            // If not, already there is a valid expression so append to it
            binding.tv?.text="${binding.tv?.text}${view.text.toString()}"
        }
        // Set the flag
        lastNumeric = true
    }
    private fun dotEvent(){
        if (lastNumeric && !stateError && !hasDot) {
            Toast.makeText(this,".", Toast.LENGTH_SHORT).show()
            binding.tv?.text="${binding.tv?.text}."
            lastNumeric = false
            hasDot = true
        }
    }
    private fun operatorEvent(view: View){
        if (lastNumeric && !stateError&&!hasOneOperator) {
            Toast.makeText(this,(view as Button).text, Toast.LENGTH_SHORT).show()
            operator=view.text.toString()
            binding.tv?.text="${binding.tv?.text}${view.text}"
            lastNumeric = false
            hasDot = false    // Reset the DOT flag
            hasOneOperator=true
        }
    }
    private fun equalEvent(){
        try {
            // If the current state is error, nothing to do.
            // If the last input is a number only, solution can be found.
            if (lastNumeric && !stateError) {
                Toast.makeText(this, "=", Toast.LENGTH_SHORT).show()
                if (!hasOneOperator && !hasDot) {
                    binding.tv?.text = (binding.tv?.text as Number).toDouble().toString()
                }
                if (hasOneOperator) {
                    hasOneOperator = false
                    var text = binding.tv?.text?.split(operator)
                    var a=(text?.get(1) as? Number)?.toDouble()
                    var b=(text?.get(2) as? Number)?.toDouble()
                    hasDot = true // Result contains a dot
                    when (operator) {
                        "+" -> binding.tv?.text = (a!!.plus(b!!)).toString()
//                            (calculatorClass.addition(a,b)).toString()
                        "-" -> binding.tv?.text = (calculatorClass.subtraction(a,b)).toString()
                        "×" -> binding.tv?.text = (calculatorClass.multiplication(a,b)).toString()
                        "÷" -> {
                            if (b==0.0) {
                                binding.tv?.text = "Error"
                                stateError = true
                                lastNumeric = false
                            } else {
                                binding.tv?.text = (calculatorClass.division(a,b)).toString()
                            }
                        }
                    }
                }
            }
        }catch (e:Exception){
            binding.tv?.text = "Error"
        }
    }
    private fun deleteEvent(){
        Toast.makeText(this,"DELETE", Toast.LENGTH_SHORT).show()
        binding.tv?.text=""
        reset()
        calculatorClass.resetTheMemory()
    }



}
//with library
//    // Represent whether the lastly pressed key is numeric or not
//    var lastNumeric: Boolean = false
//
//    // Represent that current state is in error or not
//    var stateError: Boolean = false
//
//    // If true, do not allow to add another DOT
//    var lastDot: Boolean = false

//    fun numberEvent(view:View){
//        Toast.makeText(this,(view as Button).text, Toast.LENGTH_SHORT).show()
//        if (stateError) {
//            // If current state is Error, replace the error message
//            binding.tv?.text = (view as Button).text
//            stateError = false
//        } else {
//            // If not, already there is a valid expression so append to it
//            binding.tv?.text="${binding.tv?.text}${(view as Button).text}"
//        }
//        // Set the flag
//        lastNumeric = true
//    }

//    fun dotEvent(view:View){
//        Toast.makeText(this,".", Toast.LENGTH_SHORT).show()
//        if (lastNumeric && !stateError && !lastDot) {
//            binding.tv?.text="${binding.tv?.text}."
//            lastNumeric = false
//            lastDot = true
//        }
//    }


//    fun operatorEvent(view: View) {
//        Toast.makeText(this,(view as Button).text, Toast.LENGTH_SHORT).show()
//        if (lastNumeric && !stateError) {
//            binding.tv?.text="${binding.tv?.text}${(view as Button).text}"
//            lastNumeric = false
//            lastDot = false    // Reset the DOT flag
//        }
//    }

//    fun deleteEvent(view:View){
//        Toast.makeText(this,"DELETE", Toast.LENGTH_SHORT).show()
//        binding.tv?.text=""
//        lastNumeric = false
//        stateError = false
//        lastDot = false
//    }

//    fun equalEvent(view: View) {
//        Toast.makeText(this,"=", Toast.LENGTH_SHORT).show()
//        // If the current state is error, nothing to do.
//        // If the last input is a number only, solution can be found.
//        if (lastNumeric && !stateError) {
//            // Read the expression
//            val txt = binding.tv?.text.toString()
//            // Create an Expression (A class from exp4j library)
//            val expression = ExpressionBuilder(txt).build()
//            try {
//                // Calculate the result and display
//                val result = expression.evaluate()
//                binding.tv?.text = result.toString()
//                lastDot = true // Result contains a dot
//            } catch (ex: Exception) {
//                // Display an error message
//                binding.tv?.text = "Error"
//                stateError = true
//                lastNumeric = false
//            }
//        }
//    }







//    unClickAbles()
//    onClickListeners()
//    private fun onClickListeners(){
//        binding.buttonDelete?.setOnClickListener {
//            hasDot=false
//            binding.tv?.text=""
//            memory=0.0
//            toastMaker("DELETE")
//            unClickAbles()
//        }
//        binding.buttonDiv?.setOnClickListener {
//            addValueToTV(resources.getString(R.string.divide_sign))
//        }
//    }
//    private fun toastMaker(s:String){
//        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
//    }
//    fun addValueToTV(s:String){
//        binding.tv?.text="${binding.tv?.text}$s"
//    }
//    private fun unClickAbles(){
//        if(binding.tv?.text==""){
//            binding.buttonDiv?.isClickable=false
//            binding.buttonMul?.isClickable=false
//        }
//        if (hasDot){
//            binding.buttonDot?.isClickable=false
//        }
//    }