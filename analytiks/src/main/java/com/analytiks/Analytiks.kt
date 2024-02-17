package com.analytiks

import android.content.Context
import com.analytiks.core.AnalyticsDataTransmitterExtension
import com.analytiks.core.AnalytiksAppVisorInterceptor
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventLog
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.VisorEvent
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty

class Analytiks private constructor(
    private val clients: List<CoreAddon>,
    private var interceptor: AnalytiksAppVisorInterceptor?
) : CoreAnalytiks {

    /*
    * AnalytiksBuilder is a builder class for that holds the clients and the App Visor Interceptor.
    * */
    class Builder {
        private val clients = mutableListOf<CoreAddon>()
        private var analytiksAppVisorInterceptor: AnalytiksAppVisorInterceptor? = null

        fun addInterceptor(interceptor: AnalytiksAppVisorInterceptor) = apply {
            analytiksAppVisorInterceptor = interceptor
        }

        fun addClient(client: CoreAddon) = apply {
            clients.add(client)
        }

        fun build(): Analytiks {
            return Analytiks(clients, analytiksAppVisorInterceptor)
        }
    }

    override fun initialize(context: Context) {
        clients.forEach { it.initialize(context) }
    }

    override fun logEvent(name: String, excludedAddons: Set<Class<out CoreAddon>>?) {
        interceptAndLog(EventLog.Event(emptyList())) {
            clients
                .filter { addon ->
                    excludedAddons == null || addon.javaClass !in excludedAddons
                }
                .filterIsInstance<EventsExtension>()
                .forEach {
                    it.logEvent(name)
                }
        }
    }

    override fun logEvent(
        name: String,
        properties: List<Param>,
        excludedAddons: Set<Class<out CoreAddon>>?
    ) {
        interceptAndLog(EventLog.Event(properties)) {
            clients
                .excludeAddon(excludedAddons)
                .filterIsInstance<EventsExtension>()
                .forEach {
                    //TODO migrate EventsExtension::logEvent to use List instead of vararg in properties
                    it.logEvent(name, *properties.toTypedArray())
                }
        }
    }

    override fun identify(userId: String) {
        clients
            .filterIsInstance<UserProfileExtension>()
            .forEach {
                it.identify(userId)
            }
    }

    override fun setUserProperty(
        property: UserProperty,
        excludedAddons: Set<Class<out CoreAddon>>?
    ) {
        clients
            .excludeAddon(excludedAddons)
            .filterIsInstance<UserProfileExtension>()
            .forEach {
                it.setUserProperty(property)
            }
    }

    override fun setUserPropertyOnce(
        property: UserProperty,
        excludedAddons: Set<Class<out CoreAddon>>?
    ) {
        clients
            .excludeAddon(excludedAddons)
            .filterIsInstance<UserProfileExtension>()
            .forEach {
                it.setUserPropertyOnce(property)
            }
    }

    override fun pushAll() {
        clients
            .filterIsInstance<AnalyticsDataTransmitterExtension>()
            .forEach {
                it.pushAll()
            }
    }

    override fun reset() {
        interceptAndLog(EventLog.Reset) {
            clients.forEach(CoreAddon::reset)
        }
    }

    fun List<CoreAddon>.excludeAddon(
        excludedAddons: Set<Class<out CoreAddon>>?
    ): List<CoreAddon> {
        return this.filter { addon ->
            excludedAddons == null || addon.javaClass !in excludedAddons
        }
    }

    private fun interceptAndLog(methodName: EventLog, block: () -> Unit) {
        interceptor?.intercept(VisorEvent(methodName))
        block()
    }
}