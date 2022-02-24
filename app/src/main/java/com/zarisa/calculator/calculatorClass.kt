package com.zarisa.calculator

object calculatorClass {
    private var memory:Double?=0.0
    fun addition(a:Double?,b:Double?):Double?{
        if (a != null) {
            memory= b?.let { a.plus(it) }
        }
        return memory
    }
    fun subtraction(a:Double?,b:Double?):Double?{
        if (a != null) {
            memory= b?.let { a.minus(it) }
        }
        return memory
    }
    fun multiplication(a:Double?,b:Double?):Double?{
        if (a != null) {
            memory= b?.let { a.times(it) }
        }
        return memory
    }
    fun division(a:Double?,b:Double?):Double?{
        if (a != null) {
            memory= b?.let { a.div(it) }
        }
        return memory
    }
    fun resetTheMemory(){
        memory=0.0
    }
}
//fun addition(a:Double?):Double?{
//    if (a != null) {
//        memory= memory?.let { a.plus(it) }
//    }
//    return memory
//}
//fun subtraction(a:Double?):Double?{
//    memory= a?.let { memory?.minus(it) }
//    return memory
//}
//fun division(a:Double?):Double?{
//    memory= a?.let { memory?.div(it) }
//    return memory
//}
//fun multiplication(a:Double?):Double?{
//    if (a != null) {
//        memory= memory?.let { a.times(it) }
//    }
//    return memory
//}