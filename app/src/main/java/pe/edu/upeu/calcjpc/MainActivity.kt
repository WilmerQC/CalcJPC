package pe.edu.upeu.calcjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upeu.calcjpc.ui.theme.CalcJPCTheme


// La clase MainActivity hereda de ComponentActivity
class MainActivity : ComponentActivity() {
    // Sobrescribimos el método onCreate para definir lo que ocurre al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicamos el tema CalcJPCTheme
            CalcJPCTheme {
                // Una superficie que usa el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicializamos la función composable Calculadora
                    Calculadora()
                }
            }
        }
    }
}

// Función composable Greeting con parámetros opcionales name y modifier
@Composable
fun Greeting(name: String = "Android", modifier: Modifier = Modifier) {
    // Muestra un texto que saluda al nombre dado
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Función de previsualización para la función composable Greeting
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Aplica el tema CalcJPCTheme y muestra el saludo
    CalcJPCTheme {
        Greeting("Android")
    }
}

// Función que verifica si una cadena es numérica usando una expresión regular
fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

// Función de previsualización para la calculadora
@Preview()
@Composable
fun Calculadora() {
    // Variables de estado para el valor antiguo, operador y resultado
    val (oldVal, setOldVal) = remember { mutableStateOf("") }
    val (operador, setOperador) = remember { mutableStateOf("") }
    val (result, setResult) = remember { mutableStateOf("") }

    // Definimos la estructura de la interfaz usando un Column
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para mostrar el resultado
        OutlinedTextField(value = result, onValueChange = {})

        // Listas de botones de la calculadora
        val listaA = listOf<String>("AC", ".", "%", "/")
        val listaB = listOf<String>("7", "8", "9", "*")
        val listaC = listOf<String>("6", "5", "4", "+")
        val listaD = listOf<String>("3", "2", "1", "-")
        val listaE = listOf<String>("0", "=", "", "")
        val lista = listOf<List<String>>(listaA, listaB, listaC, listaD, listaE)

        // Creamos las filas de botones
        lista.forEach { lisX ->
            Row {
                lisX.forEach { it ->
                    // Botón para cada elemento en la lista
                    if (it.isNotEmpty()) {
                        Button(onClick = {
                            // Verifica si el botón es un número
                            if (isNumeric(it)) {
                                setResult(result + it)
                            }
                            // Verifica si el botón es un operador
                            if (it in listOf("/", "*", "+", "-")) {
                                setOldVal(result)
                                setOperador(it)
                                setResult("")
                            }
                            // Verifica si el botón es "AC" para limpiar el resultado
                            if (it == "AC") {
                                setOldVal("")
                                setOperador("")
                                setResult("")
                            }
                            // Verifica si el botón es "%" para calcular el porcentaje
                            if (it == "%") {
                                val num = result.toDouble()
                                setResult((num / 100).toString())
                            }
                            // Verifica si el botón es "=" para realizar la operación
                            if (it == "=") {
                                val num1 = oldVal.toDouble()
                                val num2 = result.toDouble()
                                when (operador) {
                                    "/" -> { setResult((num1 / num2).toString()) }
                                    "*" -> { setResult((num1 * num2).toString()) }
                                    "+" -> { setResult((num1 + num2).toString()) }
                                    "-" -> { setResult((num1 - num2).toString()) }
                                }
                                setOldVal("")
                                setOperador("")
                            }
                        }) {
                            // Texto del botón
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}