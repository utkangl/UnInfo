package com.utkangul.invio.viewModel

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.utkangul.invio.api.IUniversitiesApi
import com.utkangul.invio.model.UniversityData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val _universityData = MutableStateFlow<UniversityData?>(null)
val universityData: StateFlow<UniversityData?> = _universityData.asStateFlow()

class SplashViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()



    private val api: IUniversitiesApi = createApi()

    init {
        fetchUniversitiesData(1)
    }

    private fun createApi(): IUniversitiesApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(IUniversitiesApi::class.java)
    }

    var isFetching = false
    fun fetchUniversitiesData(page: Int) {
        if (!isFetching){
            isFetching = true
            val call = api.getUniversities("usg-challenge", "universities-at-turkey", page)

            call.enqueue(object : Callback<UniversityData> {
                override fun onResponse(
                    call: Call<UniversityData>,
                    response: Response<UniversityData>
                ) {
                    isFetching = false
                    if (response.isSuccessful) {
                        _universityData.value = response.body()
                        _isLoading.value = false
                    } else {
                        println("Error : ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UniversityData>, t: Throwable) {
                    isFetching = false
                    println("Error: ${t.message}")
                }
            })
        }
    }
}