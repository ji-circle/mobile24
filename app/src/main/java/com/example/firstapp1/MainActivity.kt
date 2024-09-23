package com.example.firstapp1

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
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapp1.ui.theme.FirstApp1Theme
import kotlin.math.exp

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

//onboardingscreen이나 greetings의 scaffold는 뺴내서 MyApp에서 관리하는 게 더 좋음
//  (scaffold는 틀 자체이니까) 각각만을 나타내는 것이 재활용하기 좋음...
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var starting by remember { mutableStateOf(true) }

    Scaffold(modifier = modifier) { innerPadding ->
        if (starting) {
            OnBoardingScreen(
                onContinueClicked = { starting = false },
                modifier = modifier.padding(innerPadding))
        } else {
            Greetings(modifier = modifier.padding(innerPadding))
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
//        modifier = modifier.padding(innerPadding).fillMaxSize(),
        //MyApp에서 innerpadding을 이미 넣었음.
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to jetpack compose")

        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            // onclick 에 넣을 함수를 argument로 받을 것임
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}


//MyApp이었음
@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(
        modifier = modifier
//            .padding(innerPadding) MyApp에서 이미 innerpadding 줌
            .padding(4.dp)
    ) {
        for (name in names) {
            Greeting(name = name)
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding = if (expanded) 28.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding((24.dp))
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello $name!")
                Text(text = name)
                Text(text = name)
            }
            // 버튼
            ElevatedButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "show less" else "show more")
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