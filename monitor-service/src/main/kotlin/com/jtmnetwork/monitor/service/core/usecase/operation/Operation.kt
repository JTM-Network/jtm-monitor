package com.jtmnetwork.monitor.service.core.usecase.operation

interface Operation {

    fun execute(executor: OperationExecutor)
}