package com.example.fiszki.ui.components

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*

@Composable
fun FiszkiAppBar( // TODO: dokończyć
    //cos o nawigacji i ikonie pobierania (?)
    title: String,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(title)}
    )
}