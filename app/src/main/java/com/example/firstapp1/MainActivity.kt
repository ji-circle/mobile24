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
            //dynamicColor 변경 -> Theme에서 dynamiccolor가 false이기 때문에 조건문에 의해 light가 됨
            //  light는 Theme 윗부분에 정의되어있음. 그 색으로 변함
            FirstApp1Theme(dynamicColor = false) {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
//    var starting by remember { mutableStateOf(true) }
    //화면 회전 시 초기화되지 않게 하려면 remember가 아니고 rememberSaveable로 해야 함
    var starting by rememberSaveable { mutableStateOf(true) }

    Scaffold(modifier = modifier) { innerPadding ->
        if (starting) {
            OnBoardingScreen(
                onContinueClicked = { starting = false },
                modifier = modifier.padding(innerPadding)
            )
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to jetpack compose")

        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
    names: List<String> = List(1000) { "$it" } //it 은 각각의 인덱스를 나타냄
) {
    //스크린에 보일 때만 존재하면 되니까 LazyColumn 사용하기.
    //  LazyColumn 쓸 때는 items를 써야 함...
    LazyColumn(modifier = modifier.padding(4.dp)) {
        // items 선택 시 list<> 있는 거 선택
        items(items = names) { name ->
            Greeting(name = name)
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //여기도 rememberSavable 해야 함
    var expanded by rememberSaveable { mutableStateOf(false) }

    //클릭해서 패딩 늘어날 때 애니메이션 추가하기
// 원래:    val extraPadding = if (expanded) 28.dp else 0.dp
//    val extraPadding by animateDpAsState(targetValue = if (expanded) 28.dp else 0.dp)
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 28.dp else 0.dp,
        //spring : 한번 눌렀을 때 통통 튀는 효과를 줌
        // spring 하기 전까지는 coerceAtLeast를 쓰지 않아도 ㄱㅊ
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
        //이 상태로는 다시 눌렀을 떄 (show less를 눌렀을 때) 앱이 죽는 경우가 발생... -> padding이 음수가 되어서
        // bounce를 할 때, 목표치 위아래로 왔다갔다 하게 됨... 그래서 0으로 갈 때 음수가 됨
        //   --> greeting의 컬럼 부분의 패딩 부분을 수정해야 함!
    )

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
//                    .padding(bottom = extraPadding)
                    //패딩이 음수가 되지 않게 수정
                    //  0 이하로는 허용하지 않겠다는 것
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello $name!")
                Text(text = name)
                //텍스트의 스타일 변경
//                Text(text = name, style = MaterialTheme.typography.headlineMedium)
                //만약 위에서 조금 더 수정하고 싶다면 (ex. 추가로 볼드체로 바꾸는 것 외에는 다른건 그대로 카피 하겠다)
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                //테마의 색상 등은 Project > app > src > main > java > ... > ui.Theme > Theme.kt 아래에 있음
                // 거기서 dynamicColor 부분 수정하기! false로 바꿔야 함... 해당 코드 윗부분에서 false로 바꿔줘야 함.
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