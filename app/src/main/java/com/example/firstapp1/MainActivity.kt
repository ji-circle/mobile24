package com.example.firstapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.example.firstapp1.ui.theme.FirstApp1Theme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstApp1Theme(dynamicColor = false) {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var starting by rememberSaveable { mutableStateOf(true) }

    Scaffold(modifier = modifier) { innerPadding ->
        if (starting) {
            OnBoardingScreen(
                onContinueClicked = { starting = false },
                modifier = modifier.padding(innerPadding)
            )
        } else {
            Greetings(
                //여기 코드 추가함
                onBackClicked = { starting = true },
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        //과제 제출 위해 수정한 부분들
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Text("Welcome to")
        Text("jetpack compose!")

        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

//greetings 에도 onBackClicked를 만들어서 받아옴
@Composable
fun Greetings(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "Student $it" }
) {
    Column(
        //modifier 부분을 여기로 다 빼냄
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(4.dp)
    ) {
        Button(
            //modifier = modifier.padding(bottom = 5.dp),
            onClick = onBackClicked
        ) {
            Text("Back to the starting window")
        }

//        LazyColumn(modifier = modifier.padding(4.dp)) {
// 괄호 안의 modifier padding 부분을 위로 같이 빼냄
        LazyColumn() {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 28.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
//            modifier = modifier.padding((24.dp))
            modifier = modifier
                .padding((24.dp))
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
//                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Mobile App Development")
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
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
//        Greetings(onBackClicked = )
        //위와 같은 경우 어떻게 넣여야 프리뷰 보는거지...?
    }
}