package com.jtmnetwork.monitor.service

import com.jtmnetwork.monitor.service.core.usecase.file.StoredPluginFileHandlerTest
import com.jtmnetwork.monitor.service.data.service.plugin.LogServiceTest
import com.jtmnetwork.monitor.service.data.service.StoredPluginServiceTest
import com.jtmnetwork.monitor.service.data.service.plugin.PluginServiceTest
import com.jtmnetwork.monitor.service.data.service.plugin.ServerServiceTest
import com.jtmnetwork.monitor.service.data.service.plugin.SessionServiceTest
import com.jtmnetwork.monitor.service.entrypoint.controller.CommandControllerTest
import com.jtmnetwork.monitor.service.entrypoint.controller.StoredStoredPluginControllerTest
import com.jtmnetwork.monitor.service.entrypoint.controller.ServerControllerTest
import com.jtmnetwork.monitor.service.entrypoint.controller.SessionControllerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.ConnectedHandlerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.log.IncomingLogHandlerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.DisablePluginHandlerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.EnablePluginHandlerTest
import com.jtmnetwork.monitor.service.entrypoint.handler.plugin.UpdatePluginHandlerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    StoredPluginFileHandlerTest::class,

    LogServiceTest::class,
    ServerServiceTest::class,
    SessionServiceTest::class,
    StoredPluginServiceTest::class,

    SessionControllerTest::class,
    ServerControllerTest::class,
    PluginServiceTest::class,
    StoredStoredPluginControllerTest::class,
    CommandControllerTest::class,

    IncomingLogHandlerTest::class,
    ConnectedHandlerTest::class,
    UpdatePluginHandlerTest::class,
    EnablePluginHandlerTest::class,
    DisablePluginHandlerTest::class,
])
class MonitorApplicationTestSuite