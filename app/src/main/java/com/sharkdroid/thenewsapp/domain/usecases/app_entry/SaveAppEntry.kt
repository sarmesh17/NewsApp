package com.sharkdroid.thenewsapp.domain.usecases.app_entry

import com.sharkdroid.thenewsapp.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(){

       localUserManager.saveAppEntry()
    }

}