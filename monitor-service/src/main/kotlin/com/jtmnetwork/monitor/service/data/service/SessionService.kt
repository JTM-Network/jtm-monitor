package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SessionService @Autowired constructor(private val sessionRepository: SessionRepository) {

    fun insert(session: WebSocketSession): Mono<Session> {
        val ses = sessionRepository.addSession(session) ?: return Mono.empty()
        return Mono.just(ses)
    }

    fun findById(id: String): Mono<Session> {
        val session = sessionRepository.getSession(id) ?: return Mono.empty()
        return Mono.just(session)
    }

    fun findAll(): Flux<Session> {
        val sessions = sessionRepository.getSessions()
        return Flux.fromIterable(sessions)
    }

    fun deleteById(id: String): Mono<Session> {
        val deleted = sessionRepository.removeSession(id) ?: return Mono.empty()
        return Mono.just(deleted)
    }
}