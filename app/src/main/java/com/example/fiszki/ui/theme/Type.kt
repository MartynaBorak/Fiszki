package com.example.fiszki.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.example.fiszki.R

val montserratRegular = FontFamily(Font(R.font.montserrat))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = montserratRegular,
        fontSize = 16.sp
    )
)

val ralewayRegular = FontFamily(Font(R.font.raleway))

val Typo = Typography(
    body2 = TextStyle(
        fontFamily = ralewayRegular,
        fontSize = 16.sp
    )
)

// Set of Material typography styles to start with

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
