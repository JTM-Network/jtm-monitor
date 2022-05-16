package com.jtmnetwork.monitor.service.core.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Plugin already found.")
class PluginFound: RuntimeException()