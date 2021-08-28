# Yasunnae
대덕소프트웨어마이스터고등학교 2학년 프로젝트 실무1 과목에서 진행하는<br>
팀 프로젝트 "꼬순내 친구들"의 안드로이드 앱 입니다
 
<br>

## Develop Guideline
  
- Kotlin 네이밍 규칙
```kotlin
/*
  class & interface의 경우 UpperCamelCase
  변수의 경우에는 lowerCamelCase를 사용합니다
 */
class ExampleClass {
  var exampleVariable = 1234
}

/*
  서버와 통신을 하는 상황에는
  Gson 라이브러리의 @SerializedName Annotation을 사용하여
  위의 네이밍 규칙을 지킵니다
*/
data class ExampleResponse(
  @SerializedName("example_result")
  val exampleResult: String
)
```

<br>

- XML 네이밍 규칙
```xml
<!--
  View의 id의 경우에는 snake_case로 통일하며
  what_where_description의 구조를 갖습니다
-->
<TextView
  android:id="@+id/tv_login_example"
  android:layout_width="1dp"
  android:layout_height="1dp"
  android:text="example text" />
  
<!--
  각 View는 다음과 같이 치환합니다
  TextView → tv
  EditText → et
  Button → btn
  ImageView → iv
  RecyclerView → rv
  ViewPager → vp
  BottomNavigationView → bnv
  Constraintlayout → cl
-->
```

<br>

- Git
  1. branch → feature 단위로 개발하여 develop 브랜치로 병합합니다
  
  2. commit → [behavior] [detail]과 같은 형태로 아래의 예시처럼 작성합니다 
    - update ExampleActivity xml
    - add dependencies on Retrofit
    - change name of Entity Classes

<br>

##  Architecture

- Clean Architecture 사용
  - [우아한형제들 기술 블로그](https://techblog.woowahan.com/2602/)를 참고하여 클린 아키텍처를 적용하였습니다
  
<br>

- 네트워트 상태 노출 막기
  - 만약 네트워크 오류를 제어하기 위해 domain계층으로 Http 상태코드 등을 끌고오게 된다면 domain계층은 오염됩니다
  만약 기존에 Http통신을 하던 기능이 Room과 같은 로컬 저장소를 이용하는 방식으로 바뀌게 되면 기존에 Http 상태코드를 알고 사용하던 domain 레이어가 세부사항의 변경때문에 영향을 받기 때문입니다.
  - 따라서 [구글 앱 아키텍처 가이드 부록: 네트워크 상태 노출](https://developer.android.com/jetpack/guide?hl=ko#addendum), [송진우님의 Medium 블로그](https://songjinwoo.medium.com/clean-architecture-errorhandling-in-android-2-c3034d580529)를 
참고하여 데이터와 그 데이터의 상태를 캡슐화 하여 domain계층이 위의 상황처럼 오염되는 일을 방지하고자 합니다
