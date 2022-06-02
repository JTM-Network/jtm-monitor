package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.exception.SessionNotFound
import com.jtmnetwork.monitor.service.core.domain.model.CommandDTO
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CommandService @Autowired constructor(private val sessionRepository: SessionRepository) {

    /**
     * Send a command to the web socket session connected.
     *
     * @param dto                   the data transfer object.
     * @return                      an empty {@link Mono} if successful.
     */
    fun sendCommand(dto: CommandDTO): Mono<Void> {
        val session = sessionRepository.getSession(dto.id) ?: return Mono.error { SessionNotFound() }
        return session.sendEvent("command_event", dto.command)
    }
}