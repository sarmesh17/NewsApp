package com.sharkdroid.thenewsapp.domain.usecases.app_entry

import com.sharkdroid.thenewsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localUserManager: LocalUserManager) {

     operator fun invoke():Flow<Boolean>{

      return  localUserManager.readAppEntry()
    }

}