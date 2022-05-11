package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.exception.ConsoleNotFound
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class LogService @Autowired constructor(private val logRepository: LogRepository) {

    /**
     * Receive a Server Sent Event text stream of console messages.
     *
     * @param id        the id of the socket session
     * @return          ServerSentEvent<String>
     */
    fun getConsoleLogs(id: String): Flux<ServerSentEvent<String>> {
        val sink = logRepository.getSink(id) ?: return Flux.error(ConsoleNotFound())
        return sink.asFlux().map { ServerSentEvent.builder(it).build() }
    }

    /**
     * Get all console sessions created.
     */
    fun getConsoles(): Flux<String> {
        return logRepository.getSinks()
    }
}