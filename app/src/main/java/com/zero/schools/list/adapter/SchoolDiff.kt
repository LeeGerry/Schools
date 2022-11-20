package com.zero.schools.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zero.schools.list.SchoolItem

class SchoolDiff(
    private val newSchoolList: List<SchoolItem>,
    private val oldSchoolList: List<SchoolItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldSchoolList.size
    }

    override fun getNewListSize(): Int {
        return newSchoolList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newSchoolList[newItemPosition].id == oldSchoolList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newSchoolList[newItemPosition] == oldSchoolList[oldItemPosition]
    }
}