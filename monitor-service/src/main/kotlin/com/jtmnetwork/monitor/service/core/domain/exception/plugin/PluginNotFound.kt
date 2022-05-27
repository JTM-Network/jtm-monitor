package com.jtmnetwork.monitor.service.core.domain.exception.plugin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "StoredPlugin not found.")
class PluginNotFound: RuntimeException()