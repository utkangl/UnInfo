package com.utkangul.invio.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.utkangul.invio.model.UniversityInfo

class FavouritesViewModel : ViewModel(){

    fun stringToUniversityInfo(data: String): UniversityInfo {
        val regex = Regex("""UniversityInfo\(name=(.*?), phone=(.*?), fax=(.*?), website=(.*?), email=(.*?), address=(.*?), rector=(.*?)\)""")
        val matchResult = regex.find(data)

        return if (matchResult != null) {
            UniversityInfo(
                name = matchResult.groupValues[1],
                phone = matchResult.groupValues[2],
                fax = matchResult.groupValues[3],
                website = matchResult.groupValues[4],
                email = matchResult.groupValues[5],
                address = matchResult.groupValues[6],
                rector = matchResult.groupValues[7]
            )
        } else {
            println("data $data")
            UniversityInfo(null, null, null, null, null, null, null)
        }
    }

    fun stringsToUniversityInfoList(dataList: List<String>): List<UniversityInfo> {
        val universityInfoList = mutableListOf<UniversityInfo>()

        for (data in dataList) {
            val universityInfo = stringToUniversityInfo(data)
            universityInfoList.add(universityInfo)
        }

        return universityInfoList
    }

}