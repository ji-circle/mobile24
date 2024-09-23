package com.example.firstapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapp1.ui.theme.FirstApp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstApp1Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    //Scaffold 는 틀을 말함... 전체 틀. 그 안에 컨텐트들이 들어감
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            for (name in names) {
                Greeting(
                    name = name,
                    modifier = Modifier
                    // 만약 아래 greeting에서 modifier 기본값을 갖고 있다면 위 코드 생략 가능
                )
            }
        }
    }
}

// 각각의 greeting 사이에 padding 주려면 여기서 줘야 함 (surface 뒤에!)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(
                (24.dp)
            )
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
            ) {
                Text(text = "Hello $name!")
                Text(text = name)
                Text(text = name)
            }
            // 버튼
            ElevatedButton(onClick = {/*TODO*/ }) {
                Text("show more")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstApp1Theme {
        MyApp()
    }
}