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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

//greeting은 안드로이드 시스템에서 관리함... 상태가 바뀌면 greeting 함수를 다시 호출하게 됨 (리컴포지션이 일어난다)
// 다시 호출되면 greeting의 구현 부분이 다시 새롭게 실행됨 --> expanded도 새로고침 됨

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //버튼 클릭에 대해... 변수가 필요
//    var expanded = false 이러면 안 됨!! 이유는 위에 ^
    // 그래서 mutableStateOf 로 해줘야 함 --> 다시 그리더라도 기억해야 한다는 것
    //  그럼 var일 필요 없음 (이 greeting 안에서 그 자체가 바뀌지 않으니까. 상태만 바뀌는 것)
    val expanded = remember { mutableStateOf(false) }

    //상태는 expanded 하나로 하고, 그거에 의존하게 코드를 짤 것임
    //  상태에 따라, 각 컬럼에 extra padding을 주고 버튼이 가운데 정렬이 되게 하려면 컬럼에 코드 추가
    //    (centervertically보다 밑이니 맞춰서 가니까)
    //  버튼은 가만히 있고 아래에 그냥 extra padding을 주고 싶으면 row에 코드 주가
    //    (row는 완성이 됐고 그 아래에 padding이 추가가 되었기 때문. top에 해도 버튼이 가운데정렬되진 않음)
    val extraPadding = if (expanded.value) 28.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding((24.dp))
//            modifier = modifier.padding((24.dp)).padding(top = extraPadding)
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
            ElevatedButton(onClick = { expanded.value = !expanded.value}) {
                Text(if (expanded.value) "show less" else "show more")
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