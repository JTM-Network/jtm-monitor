package com.jtmnetwork.monitor.data.service

import com.google.inject.Inject
import com.google.inject.Singleton
import com.jtm.framework.Framework
import com.jtm.framework.core.usecase.database.DatabaseConnector
import com.jtm.framework.data.service.Service
import com.jtmnetwork.monitor.core.domain.entity.ServerInfo

@Singleton
class ServerInfoService @Inject constructor(framework: Framework, connector: DatabaseConnector): Service<ServerInfo, String>(framework, connector, ServerInfo::class.java)