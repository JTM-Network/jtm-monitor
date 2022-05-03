package com.jtmnetwork.monitor.service

import com.jtmnetwork.monitor.service.data.service.ServerServiceTest
import com.jtmnetwork.monitor.service.data.service.SessionServiceTest
import com.jtmnetwork.monitor.service.entrypoint.controller.SessionControllerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    ServerServiceTest::class,
    SessionServiceTest::class,

    SessionControllerTest::class,
])
class MonitorApplicationTestSuite