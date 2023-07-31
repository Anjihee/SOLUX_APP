package com.hanjisu.surround_jisu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JoinMarketBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.join_market_bottom_sheet, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // BottomSheet 레이아웃과 상호작용하는 코드를 추가할 수 있습니다.
        val directJoinBtn = view.findViewById<RadioButton>(R.id.directJoinBtn)
        val deadlineJoinBtn = view.findViewById<RadioButton>(R.id.deadlineJoinBtn)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.directJoinBtn) {
                // '즉시 참여' 버튼이 선택된 경우
                directJoinBtn.setTextColor(resources.getColor(R.color.themeGreen)) // 선택된 버튼
                deadlineJoinBtn.setTextColor(resources.getColor(R.color.colorGrey))
            } else if (checkedId == R.id.deadlineJoinBtn) {
                // '마감일 참여' 버튼이 선택된 경우
                directJoinBtn.setTextColor(resources.getColor(R.color.colorGrey)) 
                deadlineJoinBtn.setTextColor(resources.getColor(R.color.themeGreen)) // 선택된 버튼
            } else { // 어떤 버튼도 선택되지 않음
                directJoinBtn.setTextColor(resources.getColor(R.color.colorGrey))
                deadlineJoinBtn.setTextColor(resources.getColor(R.color.colorGrey))
            }
        }
    }
}
