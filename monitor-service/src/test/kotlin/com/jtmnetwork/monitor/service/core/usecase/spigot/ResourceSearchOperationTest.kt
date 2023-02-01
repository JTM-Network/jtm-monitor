package com.jtmnetwork.monitor.service.core.usecase.spigot

import com.jtmnetwork.monitor.service.core.usecase.operation.OperationExecutor
import com.jtmnetwork.monitor.service.data.operation.OperationObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class ResourceSearchOperationTest {

    private val searchOperation = ResourceSearchOperation("economy")
    private val observer: OperationObserver = mock()
    private val executor = OperationExecutor(observer = observer, operation = searchOperation)

    @Test
    fun execute_shouldSearch() {
        searchOperation.execute(executor)

        verify(observer, times(1)).sendMessage(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(observer)
    }
}