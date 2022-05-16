package com.jtmnetwork.monitor.service.core.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Failed to start operation.")
class FailedStartOperation: RuntimeException()