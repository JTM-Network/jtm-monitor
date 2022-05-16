package com.jtmnetwork.monitor.service

import com.jtmnetwork.monitor.service.data.service.LogServiceTest
import com.jtmnetwork.monitor.service.data.service.PluginServiceTest
import com.jtmnetwork.monitor.service.data.service.ServerServiceTest
import com.jtmnetwork.monitor.service.data.service.SessionServiceTest
import com.jtmnetwork.monitor.service.entrypoint.controller.CommandControllerTest
import com.jtmnetwork.monitor.service.entrypoint.controller.PluginControllerTest
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
    PluginServiceTest::class,

    SessionControllerTest::class,
    ServerControllerTest::class,
    PluginControllerTest::class,
    CommandControllerTest::class,

    IncomingLogHandlerTest::class,
    ConnectedHandlerTest::class
])
class MonitorApplicationTestSuite