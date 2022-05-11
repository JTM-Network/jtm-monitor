package com.jtmnetwork.monitor.service

import com.jtmnetwork.monitor.service.data.service.LogServiceTest
import com.jtmnetwork.monitor.service.data.service.ServerServiceTest
import com.jtmnetwork.monitor.service.data.service.SessionServiceTest
import com.jtmnetwork.monitor.service.entrypoint.controller.ServerControllerTest
import com.jtmnetwork.monitor.service.entrypoint.controller.SessionControllerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.ConnectedHandlerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.log.IncomingLogHandlerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    LogServiceTest::class,
    ServerServiceTest::class,
    SessionServiceTest::class,

    SessionControllerTest::class,
    ServerControllerTest::class,

    IncomingLogHandlerTest::class,
    ConnectedHandlerTest::class
])
class MonitorApplicationTestSuite