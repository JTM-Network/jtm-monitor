package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.exception.SessionNotFound
import com.jtmnetwork.monitor.service.core.domain.model.CommandDTO
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CommandService @Autowired constructor(private val sessionRepository: SessionRepository) {

    fun sendCommand(dto: CommandDTO): Mono<Void> {
        val session = sessionRepository.getSession(dto.id) ?: return Mono.error { SessionNotFound() }
        return session.sendEvent("command_event", dto.command)
    }
}