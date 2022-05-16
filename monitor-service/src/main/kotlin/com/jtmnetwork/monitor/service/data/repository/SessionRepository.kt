package com.jtmnetwork.monitor.service.data.repository

import com.jtmnetwork.monitor.service.core.domain.model.Session
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession

@Component
class SessionRepository {

    private val sessions: MutableMap<String, Session> = HashMap()

    fun addSession(session: WebSocketSession): Session? {
        this.sessions[session.id] = Session(session.id, session)
        return this.sessions[session.id]
    }

    fun getSession(id: String): Session? {
        return this.sessions[id]
    }

    fun getSessions(): List<Session> {
        return this.sessions.values.toList()
    }

    fun removeSession(id: String): Session? {
        return this.sessions.remove(id)
    }
}