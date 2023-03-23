package com.github.eudaimonist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { w -> w.replaceFirstChar { c -> c.uppercaseChar() } }

@Composable
fun NamesAndFamousFor() {
    var name by remember { mutableStateOf("") }
    val validName by remember {
        derivedStateOf { name.isNotEmpty() }
    }
    var famousFor by remember { mutableStateOf("") }
    val validFamousFor by remember {
        derivedStateOf { famousFor.isNotEmpty() }
    }
    val namesAndFamousForMap = remember {
        mutableStateMapOf(
            "Caesar" to "War",
            "M.K Ghandhi" to "Freedom fight",
            "Hamlet" to "Books",
            "Albert Einstein" to "Science",
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .drawBehind {
                    val strokeWidth = 2 * density
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        Color.LightGray,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth,
                    )
                }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Name and Famous for....", fontStyle = FontStyle.Italic, fontSize = 20.sp)
            OutlinedTextField(
                value = name,
                onValueChange = { name = it.capitalizeWords() },
            )
            OutlinedTextField(
                value = famousFor,
                onValueChange = { famousFor = it },
            )
            val buttonEnabledStrategy = validName && validFamousFor
            Button(
                enabled = buttonEnabledStrategy,
                onClick = {
                    namesAndFamousForMap[name] = famousFor
                },
            ) {
                Text("+")
            }
            Button(
                enabled = buttonEnabledStrategy,
                onClick = { namesAndFamousForMap.remove(name) },
            ) {
                Text("-")
            }
        }

        Column {
            val sortedNamesAndFamousForMap = namesAndFamousForMap.toSortedMap()
            for (entry in sortedNamesAndFamousForMap) {
                Text("${entry.key} Famous for '${entry.value}'")
            }
        }
    }
}
