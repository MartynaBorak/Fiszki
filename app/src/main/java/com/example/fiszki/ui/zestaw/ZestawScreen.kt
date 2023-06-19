package com.example.fiszki.ui.zestaw

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


object ZestawScreenDestination: NavDestination {
    override val route = "zestaw"
    const val zestawIdArg = "zestawId"
    val routeWithArgs = "$route/{$zestawIdArg}"
}

@Composable
fun ZestawScreen(
    navigateToFiszkaEntry: (Int) -> Unit,
    navigateToFiszkaDetails: (Int) -> Unit,
    navigateToZestawEdit: (Int) -> Unit,
    navigateToLearn: (Int, Boolean) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ZestawViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val zestawUiState = viewModel.zestawUiState
    val filtered = viewModel.filterFav
    val fiszkiUiState by viewModel.fiszkiUiState.collectAsState()
    val favFiszkiUiState by viewModel.favFiszkiUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showLearnDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = zestawUiState.name,
                canNavigateBack = true,
                actiontype = "menu",
                onActionClicked = {
                    Log.d("app bar", "${zestawUiState.id}")
                    navigateToZestawEdit(zestawUiState.id) },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToFiszkaEntry(zestawUiState.id) },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj fiszkę"
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                IconButton(onClick = { viewModel.filterFav = !filtered }) {
                    Icon(
                        imageVector = if(filtered) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Filtruj ulubione/wszystkie"
                    )
                }
                Button(
                    onClick = { showLearnDialog = true }, //TODO: tu bedzie pytanie czy filtrowane czy nie
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6495ed)),
                    enabled = true
                ) {
                    Text("UCZ SIĘ", color = Color.White, fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        if (fiszkiUiState.fiszkiList.isEmpty()) {
                            Toast.makeText(context, "Brak fiszek", Toast.LENGTH_SHORT).show()
                        } else {
                            exportToPdf(context, fiszkiUiState.fiszkiList, coroutineScope, zestawUiState.name)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA07A)),
                    enabled = true
                ) {
                    Text("PDF", color = Color.White, fontSize = 20.sp)
                }
            }
            Divider()

            if(filtered){
                if(favFiszkiUiState.fiszkiList.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Text("Brak fiszek do pokazania")
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)
                    ) {
                        items(
                            items = favFiszkiUiState.fiszkiList,
                            key = { it.id }
                        ) {
                            FiszkaItem(
                                fiszka = it,
                                onFiszkaClick = { navigateToFiszkaDetails(it.id) },
                                onFavChanged = {
                                    coroutineScope.launch {
                                        viewModel.updateFiszka(it.copy(isFavourite = !it.isFavourite))
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                if(fiszkiUiState.fiszkiList.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Text("Brak fiszek do pokazania")
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)
                    ) {
                        items(
                            items = fiszkiUiState.fiszkiList,
                            key = { it.id }
                        ) {
                            FiszkaItem(
                                fiszka = it,
                                onFiszkaClick = { navigateToFiszkaDetails(it.id) },
                                onFavChanged = {
                                    coroutineScope.launch {
                                        viewModel.updateFiszka(it.copy(isFavourite = !it.isFavourite))
                                    }
                                }
                            )
                        }
                    }
                }
            }
            //
            if(showLearnDialog) {
                LearnDialog(
                    onDismiss = { showLearnDialog = false },
                    onAllClicked = {
                        showLearnDialog = false
                        navigateToLearn(zestawUiState.id, false)
                    },
                    onFilteredClicked = {
                        showLearnDialog = false
                        navigateToLearn(zestawUiState.id, true)
                    }
                )
            }
        }
    }
}

@Composable
fun FiszkaItem(
    fiszka: FiszkaUiState,
    onFiszkaClick: (FiszkaUiState) -> Unit,
    onFavChanged: (FiszkaUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onFiszkaClick(fiszka) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = fiszka.front,
                fontSize = 18.sp
            )
            IconButton(onClick = { onFavChanged(fiszka) }) {
                Icon(
                    imageVector = if (fiszka.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Dodaj lub usuń z ulubionych"
                )
            }
        }
    }
}

fun exportToPdf(
    context: Context,
    fiszki: List<FiszkaUiState>,
    viewModelScope: CoroutineScope,
    zestawName: String
) {
    viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            try {
                val file = File(context.filesDir, "fiszki_${zestawName}.pdf")

                // Inicjalizacja dokumentu PDF
                val pdfWriter = PdfWriter(file)
                val pdfDocument = PdfDocument(pdfWriter)
                val document = Document(pdfDocument)

                // Dodawanie nazwy zestawu do dokumentu
                val content1 = "Zestaw: $zestawName"
                val zestawNameParagraph = Paragraph(content1)
                document.add(zestawNameParagraph)

                // Dodawanie fiszek do dokumentu
                for (fiszka in fiszki) {
                    val content = "${fiszka.front} - ${fiszka.back}"
                    val paragraph = Paragraph(content)
                    document.add(paragraph)
                }

                // Zakończenie dokumentu
                document.close()

                // Otwieranie pliku PDF
                val uri = FileProvider.getUriForFile(context, "com.example.fiszki.fileprovider", file)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            } catch (e: Exception) {
                // Obsługa błędu podczas eksportu
                Toast.makeText(context, "Błąd podczas eksportu do PDF", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}

fun sharePdfFile(context: Context) {
    val file = File(context.filesDir, "fiszki.pdf")
    val uri = FileProvider.getUriForFile(context, "com.example.fiszki.fileprovider", file)

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "application/pdf"
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    val chooserIntent = Intent.createChooser(intent, "Udostępnij plik PDF")
    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(chooserIntent)
}

@Composable
private fun LearnDialog(
    onDismiss: () -> Unit,
    onAllClicked: () -> Unit,
    onFilteredClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("UCZ SIĘ") },
        text = { Text("Czy chcesz przejrzeć wszystkie fiszki?") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onAllClicked) {
                Text("Wszystkie")
            }
        },
        confirmButton = {
            TextButton(onClick = onFilteredClicked) {
                Text("Tylko ulubione")
            }
        }
    )
}


