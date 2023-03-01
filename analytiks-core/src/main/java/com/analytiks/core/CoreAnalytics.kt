package com.analytiks.core

import android.content.Context
import android.os.Bundle
import com.analytiks.core.model.EventProperty
import com.analytiks.core.model.UserProperty
import java.util.*


interface BaseAnalytics {
    fun initialize(context: Context)
}

interface EventsExtension {
    fun logEvent(name: String)
    fun logEvent(name: String, vararg properties: EventProperty)
}

interface UserProfileExtension {
    fun identify(userId: String? = UUID.randomUUID().toString())
    fun setUserProperty(property: UserProperty)
}

interface PropertiesFormatterExtension {
    fun formatProps(): Bundle
}
