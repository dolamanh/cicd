package com.example.cicd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cicd.ui.theme.CicdTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CicdTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { innerPadding ->
                        MainScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var counter by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CI/CD Demo App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Số lần nhấn: $counter",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { counter++ },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Tăng số")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Hiển thị thông tin phiên bản
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Thông tin phiên bản",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Phiên bản: ${BuildConfig.VERSION_NAME}")
                Text(text = "Mã phiên bản: ${BuildConfig.VERSION_CODE}")
                Text(text = "Build Type: ${BuildConfig.BUILD_TYPE}")
                Text(text = "Flavor: ${getBuildFlavor()}")
            }
        }
    }
}

// Hàm để xác định build flavor
private fun getBuildFlavor(): String {
    return when {
        BuildConfig.APPLICATION_ID.endsWith(".dev") -> "Development"
        BuildConfig.APPLICATION_ID.endsWith(".qa") -> "QA"
        BuildConfig.APPLICATION_ID.endsWith(".debug") -> "Debug"
        else -> "Production"
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CicdTheme {
        MainScreen()
    }
}

