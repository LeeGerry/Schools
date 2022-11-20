package com.zero.schools.list

import com.zero.schools.network.model.SchoolRaw
import javax.inject.Inject

class SchoolListMapper @Inject constructor() : Function1<List<SchoolRaw>, List<SchoolItem>> {
    override fun invoke(schoolRaws: List<SchoolRaw>): List<SchoolItem> {
        return schoolRaws.map {
            SchoolItem(
                id = it.dbn,
                name = it.schoolName,
                phone = it.phone,
                overview = it.overViewParagraph,
                website = it.website,
                email = it.email,
                location = it.location.substring(0, it.location.indexOf('(')),
            )
        }
    }
}
