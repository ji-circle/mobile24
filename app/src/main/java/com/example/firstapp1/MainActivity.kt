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


// 상태 호이스팅 : onboardingscreen 안에 상태가 따로 있거나 받는 것이 아니고, onContinueClicked라는 파라미터로 함수 자체를 콜백을 전달하면서
//  상위 함수(starting)에 들어올려짐
// onboardingscreen에서는 상태에 직접 접근하지 못함
// 재사용성은 올라감
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    //start가 true가 되면 특정 온보딩이 보여지고, false가 되면 greetings가 보이게 하고 싶을 때

//    val starting = remember { mutableStateOf(true) }
    // 위 코드로 하려면 매번 starting.value 로 접근해야 함
    var starting by remember { mutableStateOf(true) }

    if (starting) {
        //아래 코드들을 함수로 빼냄
        OnBoardingScreen(onContinueClicked = { starting = false })
        //그냥 빼면 starting에서 문제가 남! --> onclick이라는 함수를 여기서 저기로 넘겨줘야 함. 변수가 아니고

//        Scaffold(modifier = modifier) { innerPadding ->
//            //정렬은 컬럼 뒤 괄호에 넣기
//            Column(
//                modifier = modifier.padding(innerPadding),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Welcome to jetpack compose")
//
//                Button(
//                    // 버튼과 위의 것 사이에 패딩 주기 -> 버튼 뒤 괄호에 modifier 추가하고 패딩 주기
//                    //여기서 Modifier를 대문자로 쓴 이유 : 아니면 위의 innerpadding이 그대로 들어옴
//                    modifier = Modifier.padding(vertical = 24.dp),
//                    onClick = { starting.value = !starting.value }) {
//                    Text("Continue")
//                }
//            }
//        }
    } else {
        Greetings()
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit, //아무 파라미터도 받지 않는데 특별히 아무것도 리턴하지 않는... = 함수를 받는..?
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        //정렬은 컬럼 뒤 괄호에 넣기
        Column(
            //범위 정해주기. 전체를 다 사용할거면 fillMaxSize 추가해야 함
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to jetpack compose")

            Button(
                // 버튼과 위의 것 사이에 패딩 주기 -> 버튼 뒤 괄호에 modifier 추가하고 패딩 주기
                //여기서 Modifier를 대문자로 쓴 이유 : 아니면 위의 innerpadding이 그대로 들어옴
                modifier = Modifier.padding(vertical = 24.dp),
//                onClick = { starting.value = !starting.value }) {
                // onclick 에 넣을 함수를 argument로 받을 것임
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}


//MyApp이었음
@Composable
fun Greetings(
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
                Greeting(name = name)
            }
        }
    }
}

// 각각의 greeting 사이에 padding 주려면 여기서 줘야 함 (surface 뒤에!)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //아래 코드처럼 하면 매번 .value를 써 줘야 함
//    val expanded = remember { mutableStateOf(false) }
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
            // 버튼 클릭 시 expanded의 상태를 변경함
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