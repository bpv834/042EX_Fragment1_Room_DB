package com.lion.a042ex_fragment1.fragment

import android.inputmethodservice.Keyboard.Row
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.MaterialSharedAxis
import com.lion.a042ex_fragment1.DataModel
import com.lion.a042ex_fragment1.FragmentName
import com.lion.a042ex_fragment1.MainActivity
import com.lion.a042ex_fragment1.R
import com.lion.a042ex_fragment1.StudentDataBase
import com.lion.a042ex_fragment1.databinding.FragmentMainBinding
import com.lion.a042ex_fragment1.databinding.FragmentShowBinding
import com.lion.a042ex_fragment1.databinding.RowMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    // RecyclerView 구성을 위한 임시 데이터
//    val dataList = Array(50){
//        "항목 $it"
//    }

    // 데이터를 담을 리스트
    var dataList = mutableListOf<DataModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)

        // RecyclerView 구성
        settingRecyclerViewMain()
        // FAB 구성
        settingfabMain()
        // 데이터를 읽어와 RecyclerView를 갱신한다
        refreshRecyclerView()

        return fragmentMainBinding.root
    }

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

    // FAB 구성
    fun settingfabMain(){
        fragmentMainBinding.apply {
            fabMain.setOnClickListener {
                // InputFragment로 변경한다.
                val a1 = activity as MainActivity
                a1.replaceFragment(FragmentName.INPUT_FRAGMENT, true, null)
            }
        }
    }

    // 데이터베이스에서 데이터를 가져와 리스트 뷰를 구성하는 메서드
    fun refreshRecyclerView(){
        val mainActivity = activity as MainActivity
        val studentDataBase = StudentDataBase.getInstance(mainActivity)
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                studentDataBase?.dataDao()?.selectStudentDataAll()
            }
            dataList = work1.await() as MutableList<DataModel>

            // recyclerView 갱신
            fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }


    // RecyclerView의 어뎁터 클래스
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>(){
        // ViewHolder
        inner class MainViewHolder(var rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root), OnClickListener{
            override fun onClick(v: View?) {
                // ShowFragment가 보이도록 한다.
                val a1 = activity as MainActivity
                // Fragment에게 전달할 데이터를 담을 Bundle 객체를 생성한다.
                val dataBundle = Bundle()
                // 데이터를 담는다
                // 사용자가 누른 학생의 idx 값
                dataBundle.putInt("idx", dataList[adapterPosition].idx)
                a1.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowMainViewHolder = RowMainBinding.inflate(layoutInflater)
            val mainViewHolder = MainViewHolder(rowMainViewHolder)

            rowMainViewHolder.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowMainViewHolder.root.setOnClickListener(mainViewHolder)

            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowMainBinding.textViewMainRow.text = dataList[position].name
        }
    }

}