package com.example.projeto_calculadora_de_gorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto_calculadora_de_gorjeta.ui.theme.ProjetocalculadoradegorjetaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetocalculadoradegorjetaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenResult(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//@Composable
//fun TextFieldValue(title: String, modifier: Modifier = Modifier): String {
//    var placeholder by remember { mutableStateOf("") }
//    Column (
////        modifier = modifier,
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
//    ){
//        Text(
//            text = title,
//            modifier = modifier.padding(bottom = 10.dp),
//            fontWeight = FontWeight.Bold,
//            fontSize = 17.sp,)
//        TextField(value = placeholder, onValueChange = {
//            placeholder = it
//        },
//            singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.padding(bottom = 10.dp),
//            label = { Text(text = stringResource(R.string.text_field_placeholder)) }
//        )
//    }
//
//    return placeholder
//}

@Composable
fun TextField(title: String, label: Int, icon: Int, modifier: Modifier = Modifier): String {
    var value by remember { mutableStateOf("") }
    Column(

    ) {
        Text(
            text = title,
            modifier = modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
        )
        TextField(
            value = value, onValueChange = {
                value = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(bottom = 10.dp),
            label = { Text(text = stringResource(label)) },
            leadingIcon = { Icon(
                painter = painterResource(icon),
                contentDescription = null
            ) }
        )
    }

    return value
}

@Composable
fun ResultAmount(textResult: String, modifier: Modifier = Modifier) {
    Text(
        textAlign = TextAlign.End,
        text = textResult,
        modifier = modifier.padding(top = 24.dp, bottom = 8.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}


@Composable
fun AroundedKey(
    modifier: Modifier = Modifier,

) : Boolean {
    var checkedState by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            stringResource(R.string.arounded_key_text),
        )
        Switch(
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            modifier = modifier
                .wrapContentWidth(Alignment.End)
        )
    }
    return checkedState
}
@Composable
@Preview(showSystemUi = true)
fun ScreenResult(modifier: Modifier = Modifier){
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        var value: String = TextField(
            title = stringResource(R.string.text_field_text),
            R.string.text_field_placeholder,
            R.drawable.outline_attach_money_24)
        var porcent: String = TextField(
            title = stringResource(R.string.text_field_porcent_placeholder),
            R.string.text_field_porcent_placeholder,
            R.drawable.baseline_percent_24)
        var checked: Boolean = AroundedKey(modifier)
        var tripValue : String = calculateTip(value.toDoubleOrNull() ?: 0.0,
            porcent.toDoubleOrNull() ?: 0.0,
            checked)
        ResultAmount(stringResource(R.string.result_text, tripValue))
    }
}

private fun calculateTip(amount: Double, tipPercent: Double, checked: Boolean = false) : String{
    var tip = amount * (tipPercent / 100)
    if (checked){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}