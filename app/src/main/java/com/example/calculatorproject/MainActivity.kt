package com.example.calculatorproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private var previousResult: Double = 0.0
    private var res: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pantalla
        val screen: TextView = findViewById(R.id.pantalla)
        val delete = findViewById<Button>(R.id.AC)

        //operaciones
        val suma = findViewById<Button>(R.id.mas)
        val resta = findViewById<Button>(R.id.menos)
        val multiplicacion = findViewById<Button>(R.id.multiplicacion)
        val division = findViewById<Button>(R.id.division)
        val sqrt = findViewById<Button>(R.id.raiz)
        val igual = findViewById<Button>(R.id.igual)
        val exponencial = findViewById<Button>(R.id.exponencial)
        val par = findViewById<Button>(R.id.par)
        val factorial = findViewById<Button>(R.id.factorial)
        val primo = findViewById<Button>(R.id.primo)
        val ans = findViewById<Button>(R.id.ans)

        //numeros
        val one = findViewById<Button>(R.id.uno)
        val two = findViewById<Button>(R.id.dos)
        val three = findViewById<Button>(R.id.tres)
        val four = findViewById<Button>(R.id.cuatro)
        val five = findViewById<Button>(R.id.cinco)
        val six = findViewById<Button>(R.id.seis)
        val seven = findViewById<Button>(R.id.siete)
        val eight = findViewById<Button>(R.id.ocho)
        val nine = findViewById<Button>(R.id.nueve)
        val zero = findViewById<Button>(R.id.cero)

        var operation = ""


        one.setOnClickListener{ screen.text = screen.text.toString()+"1" }
        two.setOnClickListener{ screen.text = screen.text.toString()+"2" }
        three.setOnClickListener{ screen.text = screen.text.toString()+"3" }
        four.setOnClickListener{ screen.text = screen.text.toString()+"4" }
        five.setOnClickListener{ screen.text = screen.text.toString()+"5" }
        six.setOnClickListener{ screen.text = screen.text.toString()+"6" }
        seven.setOnClickListener{ screen.text = screen.text.toString()+"7" }
        eight.setOnClickListener{ screen.text = screen.text.toString()+"8" }
        nine.setOnClickListener{ screen.text = screen.text.toString()+"9" }
        zero.setOnClickListener{ screen.text = screen.text.toString()+"0" }

        suma.setOnClickListener{
            operation = "+"
            screen.text = screen.text.toString()+"+"
        }

        resta.setOnClickListener{
            operation = "-"
            screen.text = screen.text.toString()+"-"
        }

        multiplicacion.setOnClickListener{
            operation = "*"
            screen.text = screen.text.toString()+"*"
        }

        division.setOnClickListener{
            operation = "/"
            screen.text = screen.text.toString()+"/"
        }

        sqrt.setOnClickListener{
            operation = "√"
            screen.text = screen.text.toString()+"√"
        }

        exponencial.setOnClickListener{
            operation = "²"
            screen.text = screen.text.toString()+"²"
        }



        par.setOnClickListener {
            val numero = screen.text.toString().toDoubleOrNull()

            if (numero != null) {
                if (numero % 2 == 0.0) {
                    // El número es par
                    screen.text = "Par"
                } else {
                    // El número es impar
                    screen.text = "Impar"
                }
            } else {
                screen.text = "ERROR: Entrada inválida"
            }
        }

        primo.setOnClickListener {
            val numero = screen.text.toString().toDoubleOrNull()

            if (numero != null) {
                if (esPrimo(numero.toInt())) {
                    screen.text = "Es primo"
                } else {
                    screen.text = "No es primo"
                }
            } else {
                screen.text = "ERROR: Entrada inválida"
            }
        }

        factorial.setOnClickListener {
            val numero = screen.text.toString().toIntOrNull()

            if (numero != null) {
                if (numero >= 0) {
                    val factorial = calcularFactorial(numero)
                    screen.text = "Factorial: $factorial"
                } else {
                    screen.text = "ERROR: Número negativo"
                }
            } else {
                screen.text = "ERROR: Entrada inválida"
            }
        }

        sqrt.setOnClickListener {
            val numero = screen.text.toString().toDoubleOrNull()

            if (numero != null) {
                if (numero >= 0) {
                    val raizCuadrada = calcularRaizCuadrada(numero)
                    previousResult = calcularRaizCuadrada(numero)
                    screen.text = "√$numero = $raizCuadrada"
                } else {
                    screen.text = "ERROR: Raíz cuadrada de número negativo"
                }
            } else {
                screen.text = "ERROR: Entrada inválida"
            }
        }

        delete.setOnClickListener {
            operation = "AC"
            screen.text = ""
            res= 0.0
        }

        igual.setOnClickListener {
            val inputText = screen.text.toString()

            if (endsWithOperatorError(inputText)) {
                screen.text = "ERROR: Operador al final"
                return@setOnClickListener
            }
            if (startsWithOperatorError(inputText)) {
                screen.text = "ERROR: Operador al inicio"
                return@setOnClickListener
            }
            val arr = screen.text.toString().split(operation)

            // Verificar si hay dos operadores consecutivos
            if (hayDosOperadoresConsecutivos(screen.text.toString())) {
                screen.text = "ERROR: Operadores consecutivos"
            } else {
                when (operation) {
                    "+" -> {
                        val numbers = arr.mapNotNull { it.toDoubleOrNull() }

                        if (numbers.isNotEmpty()) {
                            res = numbers.sum()
                            previousResult = res
                            screen.text = res.toString()
                        } else {
                            screen.text = "ERROR: Entrada inválida"
                        }
                    }


                    "-" -> {
                        val numbers = arr.map { it.trim() }

                        if (numbers.isNotEmpty()) {
                            var res = numbers[0].toDoubleOrNull() ?: 0.0

                            for (i in 1 until numbers.size) {
                                val num = numbers[i].toDoubleOrNull()

                                if (num != null) {
                                    res -= num
                                } else {
                                    screen.text = "ERROR: Entrada inválida"
                                    return@setOnClickListener
                                }
                            }
                            previousResult = res
                            screen.text = res.toString()
                        } else {
                            screen.text = "ERROR: Entrada inválida"
                        }
                    }




                    "*" -> {
                        res = arr[1].toDouble() * arr[0].toDouble()
                        previousResult = res
                        screen.text = res.toString()
                    }

                    "/" -> {
                        val divisor = arr[1].toDouble()

                        if (divisor == 0.0) {
                            screen.text = "ERROR: División por cero"
                        } else {
                            res = arr[0].toDouble() / divisor
                            previousResult = res
                            screen.text = res.toString()
                        }
                    }

                    "²" -> {
                        res = arr[0].toDouble() * arr[0].toDouble()
                        previousResult = res
                        screen.text = res.toString()
                    }

                    "=" -> {
                        // Guardar el resultado en previousResult
                        previousResult = res
                    }

                    else -> screen.text = "ERROR"
                }
            }
        }

        ans.setOnClickListener {
            // Mostrar el resultado anterior en la pantalla
            screen.text = previousResult.toString()
        }

    }
}
// Función para verificar si un número es primo
fun esPrimo(num: Int): Boolean {
    if (num <= 1) {
        return false
    }

    for (i in 2 until num) {
        if (num % i == 0) {
            return false
        }
    }

    return true
}

fun calcularFactorial(num: Int): Long {
    var resultado: Long = 1

    for (i in 1..num) {
        resultado *= i
    }

    return resultado
}

fun calcularRaizCuadrada(numero: Double): Double {
    return Math.sqrt(numero)
}

fun hayDosOperadoresConsecutivos(texto: String): Boolean {
    val operadores = listOf('+', '-', '*', '/')

    for (i in 0 until texto.length - 1) {
        if (operadores.contains(texto[i]) && operadores.contains(texto[i + 1])) {
            return true
        }
    }

    return false
}

fun endsWithOperatorError(input: String): Boolean {
    val operators = setOf('+', '*', '/', '-')

    val trimmedInput = input.trim()

    if (operators.any { trimmedInput.endsWith(it.toString()) }) {
        return true
    }

    return false
}

fun startsWithOperatorError(input: String): Boolean {
    val operators = setOf('*', '/')

    // Elimina cualquier espacio en blanco inicial o final de la entrada
    val trimmedInput = input.trim()

    // Comprueba si la entrada recortada comienza con alguno de los operadores.
    if (operators.any { trimmedInput.startsWith(it.toString()) }) {
        return true
    }

    return false
}
