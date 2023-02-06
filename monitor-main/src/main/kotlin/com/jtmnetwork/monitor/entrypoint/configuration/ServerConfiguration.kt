package com.jtmnetwork.monitor.entrypoint.configuration

/**
 * Interface for fetching server identifier information.
 */
interface ServerConfiguration {

    /**
     * Fetches the server identifier.
     *
     * @return  the server identifier found.
     */
    fun getServerId(): String

    /**
     * Sets the server identifier.
     *
     * @param id    the identifier.
     */
    fun setServerId(id: String)
}