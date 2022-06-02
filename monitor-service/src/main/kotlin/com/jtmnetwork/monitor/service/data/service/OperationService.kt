package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.exception.FailedStartOperation
import com.jtmnetwork.monitor.service.core.usecase.spigot.ResourceSearchOperation
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OperationService @Autowired constructor(private val observer: OperationObserver) {

    /**
     * Start search query and return a progress sink.
     *
     * @param query                     the search query
     * @return                          the progress stream to alert the user.
     * @see                             ServerSentEvent<String>
     */
    fun search(query: String): Flux<ServerSentEvent<String>> {
        val sink = observer.addOperation(ResourceSearchOperation(query)) ?: return Flux.error(FailedStartOperation())
        return sink.asFlux().map { ServerSentEvent.builder(it).build() }
    }

    /**
     * Start installing a plugin selected on to the selected server.
     *
     * @param server                    the server id
     * @param pluginId                  the plugin id
     * @return                          the progress sink
     * @see                             ServerSentEvent<String>
     */
    fun installPlugin(server: String, pluginId: String): Flux<ServerSentEvent<String>> {
        return Flux.empty()
    }
}