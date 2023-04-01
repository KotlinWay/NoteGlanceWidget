package com.example.note_glance_widget.widget

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.action.ActionParameters
import androidx.preference.Preference

object WidgetKeys {
    object Prefs {
        val noteIdPK = longPreferencesKey("noteIdPK")
        val noteTitlePK = stringPreferencesKey("noteTitlePK")
        val noteTextPK = stringPreferencesKey("noteTextPK")
        val noteLastUpdatePK = stringPreferencesKey("noteLastUpdatePK")
    }

    object Params {
        val noteIdParam = ActionParameters.Key<Long>("noteIdParam")
    }
}