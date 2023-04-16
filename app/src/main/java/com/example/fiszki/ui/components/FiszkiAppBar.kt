package com.example.fiszki.ui.components

import android.util.Log
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lock

@Composable
fun FiszkiAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    actiontype: String? = null,
    onActionClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){
    if(canNavigateBack){
        if(actiontype != null){ //wersja z guzikami po obu stronach
            TopAppBar(
                title = {Text(title)},
                modifier = modifier,
                navigationIcon = {
                    IconButton( onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Wróć"
                        )
                    }
                },
                actions = {
                    ActionButton(type = actiontype, onClick = onActionClicked)
                }
            )
        } else { //wersja ze strzalka w tyl, bez ikony z prawej
            TopAppBar(
                title = {Text(title)},
                modifier = modifier,
                navigationIcon = {
                    IconButton( onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Wróć"
                        )
                    }
                }
            )
        }
    } else {
        if(actiontype != null){ //wersja z guzikiem z prawej, bez strzalki
            TopAppBar(
                title = {Text(title)},
                modifier = modifier,
                actions = {
                    ActionButton(type = actiontype, onClick = onActionClicked)
                }
            )
        } else { //bez niczego
            TopAppBar(
                title = {Text(title)},
                modifier = modifier
            )
        }
    }
}

@Composable
fun ActionButton(
    type: String,
    onClick: () -> Unit
){
    when(type) {
        "settings" -> IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Ustawienia"
                        )
        }
        "menu" -> IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu, //tymczasowo, bo nie ma download
                            contentDescription = "Eksportuj"
                        )
        }
        else -> IconButton(onClick = {}) { //tymczasowo, bo tego wcale ma nie byc
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
        }
    }

}