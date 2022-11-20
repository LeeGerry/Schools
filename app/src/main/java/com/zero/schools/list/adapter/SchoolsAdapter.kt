package com.zero.schools.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zero.schools.databinding.SchoolItemBinding
import com.zero.schools.list.SchoolItem

class SchoolsAdapter(
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<SchoolsAdapter.SchoolViewHolder>() {

    private val schools = mutableListOf<SchoolItem>()

    fun setData(schoolList: List<SchoolItem>) {
        val schoolDiff = SchoolDiff(schoolList, schools)
        val difRes = DiffUtil.calculateDiff(schoolDiff)
        schools.clear()
        schools.addAll(schoolList)
        difRes.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = SchoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bindData(schools[position])
    }

    override fun getItemCount() = schools.size

    inner class SchoolViewHolder(private val binding: SchoolItemBinding) :
        ViewHolder(binding.root) {
        fun bindData(school: SchoolItem) {
            with(binding) {
                name.text = school.name
                website.text = school.website
                phone.text = school.phone
                email.text = school.email
                location.text = school.location
                root.setOnClickListener { listener(school.id) }
            }
        }
    }
}