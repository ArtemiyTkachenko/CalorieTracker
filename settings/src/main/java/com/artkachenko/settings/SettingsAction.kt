package com.artkachenko.settings

import com.artkachenko.core_api.base.ViewHolderActions

interface SettingsActions: ViewHolderActions<Any> {

    fun calorieCountClicked(amount: Int)
}