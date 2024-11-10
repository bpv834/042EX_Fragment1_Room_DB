# Fragment 생성

### build.gradle.kts 에 다음 라이브러리를 추가한다.


### fragment 패키지를 추가하고 다음 Fragment들을 만들어준다.

- InputFragment
- MainFragment
- ShowFragment

### 각 프래그먼트들의 kt 파일의 기본코드를 수정한다.

[InputFragment.kt]

```kt
class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentInputBinding = FragmentInputBinding.inflate(inflater)
        return fragmentInputBinding.root
    }
}
```

[ShowFragment.kt]

```kt
class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentShowBinding = FragmentShowBinding.inflate(inflater)
        return fragmentShowBinding.root
    }
}
```

[MainFragment.kt]

```kt
    lateinit var fragmentMainBinding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        return fragmentMainBinding.root
    }
```

### 프래그먼트들을 나타내는 값들을 정의한다.

[MainActivity.kt]

```kt
// 프래그먼트들을 나타내는 값들
enum class FragmentName(var number:Int, var str:String){
    MAIN_FRAGMENT(1, "MainFragment"),
    INPUT_FRAGMENT(2, "InputFragment"),
    SHOW_FRAGMENT(3, "ShowFragment")
}
```

### activity_main.xml에 FragmentContainerView를 배치한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragmentContainerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity에 프래그먼트 교체 메서드를 만들어준다.

```kt
    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack:Boolean){
        // 프래그먼트 객체
        val newFragment = when(fragmentName){
            FragmentName.MAIN_FRAGMENT -> MainFragment()
            FragmentName.INPUT_FRAGMENT -> InputFragment()
            FragmentName.SHOW_FRAGMENT -> ShowFragment()
        }
        // 프래그먼트 교체
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, newFragment)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }
```

### 첫 프래그먼트로 MainFragment를 설정한다.

[MainActivity.kt]
```kt
        // MainFragment를 설정한다.
        replaceFragment(FragmentName.MAIN_FRAGMENT, false)
```

---

# MainFragment 구성

### fragment_main.xml을 작업한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragment.MainFragment" >

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabMain"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="20dp"
        android:layout_marginBottom="20dp"
        android:clickable="true"
        android:src="@android:drawable/ic_input_add"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewMain"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### 리사이클러의 항목으로 사용할 layout 파일을 만들어준다.

[row_main.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="10dp">

    <TextView
        android:id="@+id/textViewMainRow"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="TextView"
        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
</LinearLayout>
```

### Adapter 클래스를 작성해준다.

[MainFragment.kt]

```kt
    // RecyclerView의 어뎁터 클래스
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>(){
        // ViewHolder
        inner class MainViewHolder(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            TODO("Not yet implemented")
        }
    }
```

### 리사이클로 뷰 구성을 위한 임시 데이터를 만들어준다.

[MainFragment.kt]
```kt
    // RecyclerView 구성을 위한 임시 데이터
    val dataList = Array(50){
        "항목 $it"
    }
```

### 어뎁터의 각 메서드를 구현해준다.

[MainFramgent.kt - RecyclerViewMainAdapter]

```kt
    // RecyclerView의 어뎁터 클래스
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>(){
        // ViewHolder
        inner class MainViewHolder(var rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowMainViewHolder = RowMainBinding.inflate(layoutInflater)
            val mainViewHolder = MainViewHolder(rowMainViewHolder)

            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowMainBinding.textViewMainRow.text = dataList[position]
        }
    }
```

### RecyclerView 구성 메서드를 구현해준다.
[MainFragment.kt]
```kt
    // RecyclerView  구성
    fun settingRecyclerViewMain(){
        fragmentMainBinding.apply {
            // 어뎁터 셋팅
            recyclerViewMain.adapter = RecyclerViewMainAdapter()
            // 보여주는 형식
            recyclerViewMain.layoutManager = LinearLayoutManager(activity)
            // 구분선
            val deco = MaterialDividerItemDecoration(requireActivity(), MaterialDividerItemDecoration.VERTICAL)
            recyclerViewMain.addItemDecoration(deco)
        }
    }
```

### RecyclerView 구성 메서드를 호출해준다.

[MainFragment.kt - onCreateView()]
```kt
        // RecyclerView 구성
        settingRecyclerViewMain()
```

### FAB 구성 메서드를 구현해준다.
[MainFragment.kt]
```kt
    // FAB 구성
    fun settingfabMain(){
        fragmentMainBinding.apply {
            fabMain.setOnClickListener {
                // InputFragment로 변경한다.
                val a1 = activity as MainActivity
                a1.replaceFragment(FragmentName.INPUT_FRAGMENT, true)
            }
        }
    }
```

### FAB 구성 메서드를 호출해준다.
[MainActivity - onCreateView()]

```kt
        // FAB 구성
        settingfabMain()
```

### fragment_input.xml 을 구성해준다.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="10dp"
    tools:context=".fragment.InputFragment" >

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textFieldInputName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="이름"
        app:endIconMode="clear_text"
        app:startIconDrawable="@android:drawable/ic_btn_speak_now">

        <com.google.android.material.textfield.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:singleLine="true" />
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textFieldInputAge"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:hint="나이"
        app:endIconMode="clear_text"
        app:startIconDrawable="@android:drawable/ic_delete">

        <com.google.android.material.textfield.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="number"
            android:singleLine="true" />
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textFieldINputKorean"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:hint="국어점수"
        app:endIconMode="clear_text"
        app:startIconDrawable="@android:drawable/ic_dialog_alert">

        <com.google.android.material.textfield.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:imeOptions="actionDone"
            android:inputType="number"
            android:singleLine="true" />
    </com.google.android.material.textfield.TextInputLayout>

    <Button
        android:id="@+id/buttonInputSubmit"
        style="@style/Widget.Material3.Button.OutlinedButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="입력 완료" />
</LinearLayout>
```

### BackStack에서 프래그먼트를 제거하는 메서드를 만들어준다.

[MainActivity.kt]

```kt
    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: FragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
```

### 버튼과 엔터키 구성을 위한 메서드를 구현해준다.

```kt

    // 입력 완료 버튼
    fun settingButtonInputSubmit(){
        fragmentInputBinding.apply {
            buttonInputSubmit.setOnClickListener {
                inputDone()
            }
        }
    }

    // 입력 요소 셋팅
    fun settingTextFieldInput(){
        fragmentInputBinding.apply {
            // 마지막 입력 요소에서 Enter를 눌렀을 경우
            textFieldINputKorean.editText?.setOnEditorActionListener { v, actionId, event ->
                inputDone()
                false
            }
        }
    }
```

### 각 메서드를 호출해준다.

[InputFragment.kt - onCreateView()]

```kt
        // 입력 완료 버튼
        settingButtonInputSubmit()
        // 입력 요소
        settingTextFieldInput()
```

### 입력 완료 메서드를 구현해준다.

```kt
    // 입력 완료 처리 메서드
    fun inputDone(){
        // 이전으로 돌아간다.
        val a1 = activity as MainActivity
        a1.removeFragment(FragmentName.INPUT_FRAGMENT)
    }
```

---

# ShowFragment 구성

### 리사이클러뷰의 항목을 누르면 ShowFragment로 이동하도록 구현한다.

[MainFragment.kt - RecyclerViewMainAdapter - MainViewHolder]

```kt
        // ViewHolder
        inner class MainViewHolder(var rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root), OnClickListener{
            override fun onClick(v: View?) {
                // ShowFragment가 보이도록 한다.
                val a1 = activity as MainActivity
                a1.replaceFragment(FragmentName.SHOW_FRAGMENT, true)
            }
        }
```

### 항목에 OnClickListener를 적용해준다.

[MainFragment.kt - RecyclerViewMainAdapter - onCreateViewHolder()]

```kt
            rowMainViewHolder.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowMainViewHolder.root.setOnClickListener(mainViewHolder)
```

--- 

# 입력 기능 구현


### 데이터를 담을 클래스를 구현한다.

[DataModel.kt]

```kt
package com.lion.a042ex_fragment1

data class DataModel(var name:String, var age:Int, var korean:Int)
```

### 데이터를 담을 리스트를 만들어준다.

[MainActivity.kt]

```kt
    // 데이터를 담을 리스트
    val dataList = mutableListOf<DataModel>()
```

### 사용자가 입력한 데이터를 저장하낟.

[InputFragment.kt]

```kt
    // 입력 완료 처리 메서드
    fun inputDone(){
        fragmentInputBinding.apply {
            // 입력한 데이터를 가져온다.
            val name = textFieldInputName.editText?.text.toString()
            val age = textFieldInputAge.editText?.text.toString().toInt()
            val korean = textFieldINputKorean.editText?.text.toString().toInt()
            // 객체에 담는다
            val dataModel = DataModel(name, age, korean)
            // 리스트에 담는다.
            val a1 = activity as MainActivity
            a1.dataList.add(dataModel)
            // 이전으로 돌아간다.
            a1.removeFragment(FragmentName.INPUT_FRAGMENT)
        }
    }
```

---

# MainFragment 구성

### RecyclerView 구성을 위해 사용한 임시 데이터를 주석처리 한다.

[MainFragment.kt]

```kt
    // RecyclerView 구성을 위한 임시 데이터
//    val dataList = Array(50){
//        "항목 $it"
//    }
```

### RecyclerView의 어뎁터의 메서드를 수정한다.

[MainFragment.kt - RecyclerViewMainAdapter]

```kt
        override fun getItemCount(): Int {
            // return dataList.size
            val a1 = activity as MainActivity
            return a1.dataList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            // holder.rowMainBinding.textViewMainRow.text = dataList[position]
            val a1 = activity as MainActivity
            holder.rowMainBinding.textViewMainRow.text = a1.dataList[position].name
        }
```

### Fragment 에 Bundle 객체가 전달될 수 있도록 메서드를 수정한다.

[MainActivity.kt]

```kt

    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack:Boolean, dataBundle: Bundle?){
        // 프래그먼트 객체
        val newFragment = when(fragmentName){
            FragmentName.MAIN_FRAGMENT -> MainFragment()
            FragmentName.INPUT_FRAGMENT -> InputFragment()
            FragmentName.SHOW_FRAGMENT -> ShowFragment()
        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment.arguments = dataBundle
        }
```

### replaceFragment를 호출하는 곳을 수정해준다.

[MainActivity.kt - onCreateView()]
```kt
        // MainFragment를 설정한다.
        replaceFragment(FragmentName.MAIN_FRAGMENT, false, null)
```

[MainFragment.kt - settingfabMain()]

```kt
                a1.replaceFragment(FragmentName.INPUT_FRAGMENT, true, null)
```

### 사용자가 항목을 눌렀을 때 순서값을 담아 전달한다.

[MainFragment.kt - RecyclerViewMainAdapter - MainViewHolder - onClick()]
```kt
                // Fragment에게 전달할 데이터를 담을 Bundle 객체를 생성한다.
                val dataBundle = Bundle()
                // 데이터를 담는다
                // 사용자가 누른 항목의 순서값
                dataBundle.putInt("position", adapterPosition)
                a1.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)
```

### 데이터를 TextView에 넣어주는 메서드를 구현한다.

[ShowFragment.kt]

```kt
    // TextView 구성
    fun settingTextView(){
        fragmentShowBinding.apply {
            if(arguments != null) {
                // 순서값을 가져온다.
                val position = arguments?.getInt("position")!!
                // TextView에 값을 넣어준다.
                val a1 = activity as MainActivity
                textViewShowName.text = a1.dataList[position].name
                textViewShowAge.text = a1.dataList[position].age.toString()
                textViewShowKorean.text = a1.dataList[position].korean.toString()
            }
        }
    }
```

### TextView를 구성하는 메서드를 구현한다.

[ShowFragment.kt - onCreateView()]

```kt
        // TextView 구성 메서드 호출
        settingTextView()
```

---

# 나머지 처리

### 키보드를 내려주는 메서드를 구현한다.

[MainActivity.kt]

```kt
    // 키보드를 내려주는 메서드
    fun hideSoftInput(){
        // 현재 포커스를 가지고 있는 view가 있다면
        if(currentFocus != null){
            // 키보드를 내린다.
            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            // 포커스를 제거한다.
            currentFocus?.clearFocus()
        }
    }
```

