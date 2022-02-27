package com.zarisa. calculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zarisa.calculator.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var isOperationAllowed = false
    private var isDotAllowed = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        if(savedInstanceState!= null){
            val configText = savedInstanceState.getString("tv")
            binding.tv.text = configText
            isOperationAllowed=savedInstanceState.getBoolean("operator")
            isDotAllowed=savedInstanceState.getBoolean("dot")
        }
        onClickListeners()
    }
    private fun onClickListeners(){
        binding.buttonDelete.setOnClickListener { deleteEvent() }
        binding.buttonEqual.setOnClickListener { equalEvent() }
        binding.buttonDiv.setOnClickListener {operatorEvent("÷")}
        binding.buttonMul.setOnClickListener { operatorEvent("×") }
        binding.buttonSub.setOnClickListener { operatorEvent("-") }
        binding.buttonAdd.setOnClickListener { operatorEvent("+") }
        binding.buttonDot.setOnClickListener { numberEvent(".") }
        binding.buttonZero.setOnClickListener { numberEvent("0") }
        binding.buttonOne.setOnClickListener { numberEvent("1") }
        binding.buttonTwo.setOnClickListener { numberEvent("2") }
        binding.buttonThree.setOnClickListener { numberEvent("3") }
        binding.buttonFour.setOnClickListener { numberEvent("4") }
        binding.buttonFive.setOnClickListener { numberEvent("5") }
        binding.buttonSix.setOnClickListener { numberEvent("6") }
        binding.buttonSeven.setOnClickListener { numberEvent("7") }
        binding.buttonEight.setOnClickListener { numberEvent("8") }
        binding.buttonNine.setOnClickListener { numberEvent("9") }
    }
    private fun deleteEvent() {
        binding.tv.text = ""
        isOperationAllowed = false
        isDotAllowed = true
        Toast.makeText(this,"DELETE", Toast.LENGTH_SHORT).show()
    }
    private fun numberEvent(value:CharSequence) {
        if (value == ".") {
            if (isOperationAllowed&&isDotAllowed){
                binding.tv.append(value)
                isDotAllowed = false
                isOperationAllowed = false
                Toast.makeText(this,value, Toast.LENGTH_SHORT).show()
            }
        }else{
            binding.tv.append(value)
            isOperationAllowed = true
            Toast.makeText(this,value, Toast.LENGTH_SHORT).show()
        }

    }
    private fun operatorEvent(operator:CharSequence) {
        if (isOperationAllowed) {
            Toast.makeText(this,operator, Toast.LENGTH_SHORT).show()
            binding.tv.append(operator)
            isOperationAllowed = false
            isDotAllowed = true
        }
    }
    private fun equalEvent() {
        val firstFilter = detachmentTheDigitsAndOperators()
        val mulDivFilter = calMulDiv(firstFilter)
        when {
            firstFilter.isEmpty() -> {
                binding.tv.text=""
            }
            mulDivFilter.isEmpty() -> {
                binding.tv.text=""
            }
            else -> {
                binding.tv.text=calAddSub(mulDivFilter).toString()
            }
        }
        Toast.makeText(this,"=", Toast.LENGTH_SHORT).show()
    }
    private fun detachmentTheDigitsAndOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (element in binding.tv.text) {
            if (element.isDigit()||element == '.')
                currentDigit += element
            else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(element)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }
    private fun calAddSub(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float
        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if(operator =='+')
                    result +=nextDigit
                if(operator =='-')
                    result -=nextDigit
            }
        }
        return result
    }
    private fun calMulDiv(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while( list.contains('×')||list.contains('÷')){
            val temporaryList = mutableListOf<Any>()
            var lengthOfList = list.size
            for (i in list.indices){
                if( list[i] is Char && i != list.lastIndex && i < lengthOfList){//list[i] is the operation we should work on
                    val numberBeforeOperator = list[i-1] as Float
                    val numberAfterOperator = list[i+1] as Float
                    when(list[i]){
                        '×' ->
                        {
                            temporaryList.add(numberBeforeOperator*numberAfterOperator)
                            lengthOfList = i+1
                        }
                        '÷' ->
                        {
                            temporaryList.add(numberBeforeOperator/numberAfterOperator)
                            lengthOfList = i+1
                        }
                        else ->
                        {
                            temporaryList.add(numberBeforeOperator)
                            temporaryList.add(list[i])
                        }
                    }
                }
                if (i > lengthOfList )
                    temporaryList.add(list[i])
            }
            list = temporaryList
        }
        return  list
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("tv" , binding.tv.text.toString())
        outState.putBoolean("operator" , isOperationAllowed)
        outState.putBoolean("dot" , isDotAllowed)
        super.onSaveInstanceState(outState)
    }
}