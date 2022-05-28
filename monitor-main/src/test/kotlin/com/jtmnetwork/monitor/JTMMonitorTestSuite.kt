package com.jtmnetwork.monitor

import com.jtmnetwork.monitor.entrypoint.event.EventDispatcher
import com.jtmnetwork.monitor.entrypoint.event.EventDispatcherTest
import com.jtmnetwork.monitor.entrypoint.handler.CommandHandlerTest
import com.jtmnetwork.monitor.entrypoint.handler.ConnectedHandlerTest
import com.jtmnetwork.monitor.entrypoint.log.LogAppenderTest
import com.jtmnetwork.monitor.entrypoint.socket.MonitorConnectionTest
import com.jtmnetwork.monitor.entrypoint.socket.MonitorListenerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    CommandHandlerTest::class,
    ConnectedHandlerTest::class,

    EventDispatcherTest::class,
    LogAppenderTest::class,

    MonitorConnectionTest::class,
    MonitorListenerTest::class
])
class JTMMonitorTestSuite