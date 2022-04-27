package com.jtmnetwork.monitor.service.data.service

import com.jtmnetwork.monitor.service.configuration.TestDatabaseConfiguration
import com.jtmnetwork.monitor.service.core.domain.entity.Server
import com.jtmnetwork.monitor.service.core.usecase.server.ServerRepository
import com.jtmnetwork.monitor.service.data.cache.ServerCache
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestDatabaseConfiguration::class])
class ServerServiceIntegrationTest {

    @Autowired
    lateinit var repository: ServerRepository

    @Autowired
    lateinit var cache: ServerCache

    lateinit var serverService: ServerService

    private val server = Server(index = 1)

    @Before
    fun setup() {
        serverService = ServerService(repository, cache)
    }

    @Test
    fun insert() {
        val inserted = serverService.insert(server)

        StepVerifier.create(inserted)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun update() {
        serverService.insert(server)
        val updated = serverService.update(server)

        StepVerifier.create(updated)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun findById() {
        serverService.insert(server)
        val returned = serverService.findById(server.id)

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun findAll() {
        serverService.insert(server)

        val returned = serverService.findAll()

        StepVerifier.create(returned)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }

    @Test
    fun deleteById() {
        serverService.insert(server)

        val deleted = serverService.deleteById(server.id)

        StepVerifier.create(deleted)
            .assertNext {
                assertThat(it.index).isEqualTo(1)
            }
            .verifyComplete()
    }
}