package com.jtmnetwork.monitor.service.data.service.plugin

import com.jtmnetwork.monitor.service.core.domain.model.Session
import com.jtmnetwork.monitor.service.data.repository.LogRepository
import com.jtmnetwork.monitor.service.data.repository.SessionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class SessionService @Autowired constructor(private val sessionRepository: SessionRepository, private val logRepository: LogRepository) {

    /**
     * Insert a web socket session.
     *
     * @param session               the socket session
     * @return session              the inserted socket session
     */
    fun insert(session: WebSocketSession): Mono<Session> {
        val ses = sessionRepository.addSession(session) ?: return Mono.empty()
        logRepository.addSink(ses.id)
        return Mono.just(ses)
    }

    /**
     * Find the socket session by identifier.
     *
     * @param id                    the identifier of the session
     * @return session              the session found.
     */
    fun findById(id: String): Mono<Session> {
        val session = sessionRepository.getSession(id) ?: return Mono.empty()
        return Mono.just(session)
    }

    /**
     * Find all the socket sessions
     *
     * @return session              the socket sessions found.
     */
    fun findAll(): Flux<Session> {
        val sessions = sessionRepository.getSessions()
        return Flux.fromIterable(sessions)
    }

    /**
     * Delete the socket session by identifier
     *
     * @param id                    the socket session identifier
     * @return session              the socket session found.
     */
    fun deleteById(id: String): Mono<Session> {
        val deleted = sessionRepository.removeSession(id) ?: return Mono.empty()
        logRepository.removeSink(id)
        return Mono.just(deleted)
    }
}