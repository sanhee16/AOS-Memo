package com.example.mvvmpattern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmpattern.databinding.MemoListItemBinding
import com.example.mvvmpattern.entity.Memo
import com.example.mvvmpattern.viewmodel.MemoActivityViewModel

/*
viewholder = view를 가지고 있는 것

 */
class MemoListAdapter(private var data: List<Memo>, private val vm: MemoActivityViewModel) :
    RecyclerView.Adapter<MemoListAdapter.ViewHolder>() {

    /*
    두번째로 실행, viewHolder를 할당한다.
    뷰 홀더는 실제 레이아웃 파일과 매핑되어야 함.
    한번에 보이는 itemView개수 + 3개정도(여분) 으로 호출되고 더이상 호출되지 않는다.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoListAdapter.ViewHolder {
        val b = MemoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // viewholder가 이 return 을 받는다.
        return MemoListAdapter.ViewHolder(b) // viewHolder 객체 생성
    }

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    // binding ? 프로그램에 사용된 구성 요소의 실제 "값" 또는 프로퍼티를 결정짓는 행위
    override fun onBindViewHolder(holder: MemoListAdapter.ViewHolder, position: Int) {
        holder.b.data = data[position]
        holder.b.checkBox.visibility = View.GONE
        holder.b.memoItem.setOnClickListener { vm.onClickItem(data[position].id) }
    }

    // 가장 먼저 실행되는 함수 - data의 개수 반환
    override fun getItemCount() = data.size

    fun setData(item: List<Memo>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.data, item))
        this.data = item
        diffResult.dispatchUpdatesTo(this)
    }

    fun showCheckbox() {
        // TODO: 2021-12-17 체크박스 어떻게 나오게 할 것인지?
        // 모든 item 의 체크박스가 나와야 한다.
    }

    fun hideCheckbox() {

    }

    class ViewHolder(val b: MemoListItemBinding) : RecyclerView.ViewHolder(b.root)
    /*
    diffUtil
    oldData, newData 가 있을 때 + 두 리스트에 있는 아이템들이 다를 때 새로운 아이템으로 업데이트 해준다.
    main thread를 block 하지 않고 background에서 "계산"한다.
     */
    class DiffCallback(
        private val oldData: List<Memo>,
        private val newData: List<Memo>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldData.size

        override fun getNewListSize() = newData.size

        // oldItem 과 newItem 이 같은가?를 비교하는 함수 - id로 비교하라고 한 것 (고유 id)
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition].id == newData[newItemPosition].id

        // 모든 oldItem 과 newItem 의 content 가 같은가?
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition] == newData[newItemPosition]
    }
}