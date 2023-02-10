// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

const val WINDOW_WIDTH = 420
const val WINDOW_HEIGHT = 360
@Composable
@Preview
fun App() {
    var startUrl by remember { mutableStateOf(TextFieldValue("https://w.bielecki.ru")) }
    var listUrls: MutableList<String> = mutableListOf()
    val composableScope = rememberCoroutineScope()
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                Row(modifier=Modifier.padding(10.dp,5.dp), verticalAlignment = Alignment.CenterVertically){

                    TextField(modifier = Modifier.padding(5.dp,0.dp).weight(1f),
                        value=startUrl, onValueChange = { newText-> startUrl = newText},
                        label = { Text(text = "Your start link") },)
                    Button(modifier = Modifier.width(90.dp), onClick = {
                        listUrls.add(element = startUrl.text)
                        composableScope.launch { listUrls =  getUrls(listUrls) }
                    }){
                        Text(text="GO")
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(
                        listUrls
                    ){ index, string ->
                        Text(text = string, fontSize = 18.sp, modifier = Modifier.fillMaxWidth().padding(10.dp,3.dp))
                    }
                }
                Row(modifier=Modifier.padding(10.dp,5.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.weight(1f), text="statistics")
                    Button(onClick = {}){
                        Text(text="STOP")
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        title = "Webs",
        state = WindowState(size = DpSize(WINDOW_WIDTH.dp,WINDOW_HEIGHT.dp), position = WindowPosition(alignment = Alignment.Center)),
        onCloseRequest = ::exitApplication) {
        App()
    }
}
fun getUrls(urls:MutableList<String>): MutableList<String> {

    return urls
}
