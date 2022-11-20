package com.zero.schools.details

import com.zero.schools.network.model.SchoolDetailsRaw
import javax.inject.Inject

class SchoolDetailMapper @Inject constructor() : Function1<SchoolDetailsRaw, SchoolDetail> {
    override fun invoke(raw: SchoolDetailsRaw): SchoolDetail {
        val avg = (raw.mathScore.toDouble() + raw.readingScore.toDouble() + raw.writingScore.toDouble()) / 3.0
        val formatScore = String.format("%.2f", avg)
        return SchoolDetail(
            dbn = raw.dbn,
            name = raw.schoolName,
            numberOfTestTaker = raw.numberOfTestTakers,
            avgScore = formatScore
        )
    }
}