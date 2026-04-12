package com.example.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormularioTecsupPro()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioTecsupPro() {
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var aceptoTerminos by remember { mutableStateOf(false) }
    var notificaciones by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Registro exitoso: $nombre")
                    }
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            },
            title = { Text("Confirmar Registro") },
            text = { Text("¿Deseas registrar a $nombre ($codigo) en $carrera?") }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TECSUP - Registro", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA)),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    // Cambiamos el color de fondo a un azul muy claro
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    // Le damos más sombra (elevación)
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Información Académica",
                            fontWeight = FontWeight.ExtraBold, // Más negrita
                            color = Color(0xFF0D47A1) // Azul más oscuro
                        )
                        Text("Ingrese sus datos para habilitar el registro.", fontSize = 14.sp)
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, null) }
                )
            }

            item {
                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Info, null) }
                )
            }

            item {
                OutlinedTextField(
                    value = carrera,
                    onValueChange = { carrera = it },
                    label = { Text("Carrera") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.AccountBox, null) }
                )
            }

            // MODIFICACIÓN DE ESTILO AL TERCER COMPONENTE (SWITCH)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Notificaciones activas",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold, // Estilo: Negrita
                        color = Color(0xFF1A237E)     // Estilo: Color azul
                    )
                    Switch(
                        checked = notificaciones,
                        onCheckedChange = { notificaciones = it },
                        // Estilo: Colores personalizados para el Switch
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF1A237E),
                            checkedTrackColor = Color(0xFFBBDEFB)
                        )
                    )
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = aceptoTerminos, onCheckedChange = { aceptoTerminos = it })
                    Text("Confirmo que los datos son correctos", fontSize = 14.sp)
                }
            }

            item {
                Button(
                    onClick = { showDialog = true },
                    enabled = nombre.isNotBlank() && codigo.isNotBlank() && carrera.isNotBlank() && aceptoTerminos,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null)
                    Spacer(Modifier.width(8.dp))
                    Text("ENVIAR REGISTRO", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}