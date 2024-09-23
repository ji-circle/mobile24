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
        //이 아래의 많은 컴포저블 함수들이 실행됨
        setContent {
            FirstApp1Theme {
                //아래를 자주 사용하게 될 것이라면, MyApp composable 함수를 만들어서 사용하기
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }

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
    //modifier로 받았기 때문에 괄호 안의 Modifier 대문자를 소문자로 바꿔주기
//    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
    //위에서 fillMaxSize같은 것들은 밖에서 지정되는 게 좋다면 그 부분을 지우고 위에서 설정해주기
    //            MyApp(modifier = Modifier.fillMaxSize()) 이렇게 전달해주기

    //greeting 안의 name을 하드코딩이 아니라 받는 것으로 변경하려면 위의 MyApp() 에서 받아줘야 함
    //Scaffold 는 틀을 말함... 전체 틀. 그 안에 컨텐트들이 들어감
    Scaffold(modifier = modifier) { innerPadding ->
        // 여기서 전달되는 innerpadding(아래에서 modifier를 전달받아, 소문자로 쓰는 부분들 포함)...
        // 근데 Greeting 안의 Row 부분에서는 innerpadding이 필요가 없음... 그럼 대문자 Modifier를 써야 함
        //  Column 부분에서도!
        // (그 위 surface 부분까지도 확인하기.)
        // 제대로 설정한 뒤에는 다시 modifier 라고 해도 됨.

//        Column {
//            Greeting(
//                name = "World",
//                modifier = Modifier.padding(innerPadding)
//            )
//            Greeting(
//                name = "Compose",
//                modifier = Modifier.padding(innerPadding)
//            )
//        }

//        //아래는 for문 사용한 것
//        Column {
//            for (name in names){
//                Greeting(
//                    name = name,
//                    modifier = Modifier.padding(innerPadding)
//                )
//            }
//        }

        //column에 padding주는 코드
        // padding 뒤의 괄호에 (vertical = 4.dp라고 하면 위에 패딩, horizontal은 왼쪽에 패딩)
//        Column(modifier = modifier.padding(4.dp)) {
        //위와 다르게, innerpadding을 먼저 패딩하고 추가적으로 패딩하는 것으로 코드 변경
        Column(modifier = modifier.padding(innerPadding).padding(4.dp)) {
            for (name in names) {
                Greeting(
                    name = name
//                    , modifier = Modifier.padding(innerPadding)
                    // 여기서 modifier를 주는 게 아니라(각각의 원소에 inner padding을 줄 필요는 없으니까)
                    //  scaffold 바로 아래의 column에 inner padding을 전달하는 거로 코드 변경
                    , modifier = Modifier
                    // 만약 아래 greeting에서 modifier 기본값을 갖고 있다면 위 코드 생략 가능
                )
            }
        }
    }

}

//컴포저블 함수. 위에서 호출되고 있음
// 각각의 greeting 사이에 padding 주려면 여기서 줘야 함 (surface 뒤에!)
// 각각의 길이를(꽉 채우기 등) 변경하려면 여기의 surface 안의 column 뒤의 괄호에 써야 함
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //텍스트를 둘러싸고 있는 음영을 주는 법 : surface 안에 text를 넣어 감싸기
    // 테마는 괄호 안에서 설정함

    //(...){} 인 것으로 선택
    //import androidx.compose.material3.Surface
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    ) {
        //color = Materi.. --> 함수에 argument를 전달할 때, color라는 파라미터를 명시하면서 넘기고 있음


        //세로 형식으로 보여줄 때
//        Row {
        // 세로 위치 정렬 - 가운데 정렬
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding((24.dp)
            )
        ) {
            //세로로 배치할 때 사용, 이 안에 텍스트를 넣어줌
            // padding을 추가로 줄 수도 있음
            // 길이 등을 조절할 때에도 이 뒤에 사용
//            Column(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(24.dp)
//            ) {

            // 그냥 전체가 아니라 weight로 한 것. 그리고 row 에서 전체 padding을 주려고 padding 부분 뺌
            Column(
                modifier = modifier
                    .weight(1f)
//                    .padding(24.dp)
            ) {
//        Column(modifier = modifier.padding(24.dp)) {

                Text(
                    //이 안에서도 파라미터들을 명시하면서 넘기고 있어서, 순서가 별로 중요하지 않음
                    text = "Hello $name!"
//                , modifier = modifier.padding(24.dp)
                    // modifier = 자잘한 특성들을 바꾸려고 할 때 사용하게 됨
                    //modifier = modifier 이었음
                    //padding을 주는 것!
                    //greeting의 surface 안에서도 테마를 바꿀 수 있음
                )
                Text(text = name)
                Text(text = name)
                //Text도 함수임! for 문 사용할 수 있음

            }
            // 버튼
            ElevatedButton(onClick = {/*TODO*/ }) {
                Text("show more")
            }
        }


    }
    // 위는 surface 함수를 구현하고 있는 게 아니라, surface의 파라미터들 안에 초기값들을 대부분 갖고 있는데, content에 대해서는 기본값이 없음.
    // content는 composable 함수를 받고 있으며, 특별히 리턴값이 없는 함수를 받고 있음. (커서 올려 확인하기)
    // content를 argument로 전달해야 하는데, 전달하려는 함수의 구현부를 그 뒤의 { } 안에 넣어서 전달함.
    // surface를 구현하는 게 아니라, 거기에 전달할 argument의 함수를 말하는 것임

}

//이거를 통해 split 했을 때 상태가 보이게 됨
@Preview(showBackground = true)
@Composable
//함수 선언
fun GreetingPreview() {
    FirstApp1Theme {
        //이 greeging 함수를 android 와 함께...
        //greeting 이라는 컴포저블 함수가 앱에서 어떻게 보일지를 보여줌
//        Greeting("Android")
        //윗줄 코드가 아니라 MyApp() 으로 하면 전체가 다 보임
        MyApp()
    }
}