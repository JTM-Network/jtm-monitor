package com.jtmnetwork.monitor.service.core.domain.model

import org.springframework.web.reactive.socket.WebSocketSession

data class Session(val id: String, val socketSession: WebSocketSession)
