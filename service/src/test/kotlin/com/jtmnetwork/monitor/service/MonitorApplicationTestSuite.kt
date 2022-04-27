package com.jtmnetwork.monitor.service

import com.jtmnetwork.monitor.service.data.service.ServerServiceTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    ServerServiceTest::class
])
class MonitorApplicationTestSuite