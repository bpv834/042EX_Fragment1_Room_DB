package com.lion.a042ex_fragment1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lion.a042ex_fragment1.DataModel
import com.lion.a042ex_fragment1.MainActivity
import com.lion.a042ex_fragment1.R
import com.lion.a042ex_fragment1.StudentDataBase
import com.lion.a042ex_fragment1.databinding.FragmentShowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentShowBinding = FragmentShowBinding.inflate(inflater)

        // TextView 구성 메서드 호출
        settingTextView()
        
        return fragmentShowBinding.root
    }

    // TextView 구성
    fun settingTextView(){
        fragmentShowBinding.apply {
            if(arguments != null) {
                // 학생 번호를 가져온다.
                val idx = arguments?.getInt("idx")!!
                // 데이터를 가져온다.
                val mainActivity = activity as MainActivity
                val studentDataBase = StudentDataBase.getInstance(mainActivity)
                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        studentDataBase?.dataDao()?.selectStudentDataOne(idx)
                    }
                    val dataModel = work1.await() as DataModel
                    // TextView에 값을 넣어준다.
                    textViewShowName.text = dataModel.name
                    textViewShowAge.text = dataModel.age.toString()
                    textViewShowKorean.text = dataModel.korean.toString()
                }
            }
        }
    }
}