package com.jtmnetwork.monitor.service.core.domain.exception.plugin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Failed to delete plugin version file.")
class FailedVersionDeletion: RuntimeException()