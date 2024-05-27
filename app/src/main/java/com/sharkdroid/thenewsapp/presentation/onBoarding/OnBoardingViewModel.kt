package com.sharkdroid.thenewsapp.presentation.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkdroid.thenewsapp.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val appEntryUseCases: AppEntryUseCases)  :ViewModel() {

 fun onEvent(event: OnBoardingEvent){

     when(event){
         OnBoardingEvent.SaveAppEntry -> {
            saveAppEntry()
         }
     }

 }

   private fun saveAppEntry(){

       viewModelScope.launch {
           appEntryUseCases.saveAppEntry()
       }
   }
}