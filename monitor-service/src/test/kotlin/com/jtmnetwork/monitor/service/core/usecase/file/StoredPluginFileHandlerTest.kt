package com.jtmnetwork.monitor.service.core.usecase.file

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.springframework.http.codec.multipart.FilePart
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.io.File

@RunWith(SpringRunner::class)
class StoredPluginFileHandlerTest {

    private val fileHandler = PluginFileHandler()
    private val part: FilePart = mock()

    @Before
    fun setup() {
        fileHandler.path = "/home/michelle/plugins"
        val folder = File(fileHandler.path + "/test")
        folder.mkdirs()

        val file = File(fileHandler.path + "/test/test-1.0.jar")
        file.createNewFile()
    }

    @After
    fun tearDown() {
        val file = File(fileHandler.path)
        file.deleteRecursively()
    }

    @Test
    fun save() {
        `when`(part.transferTo(any(File::class.java))).thenReturn(Mono.empty())

        val returned = fileHandler.save("test", "1.0", part)

        verify(part, times(1)).transferTo(any(File::class.java))
        verifyNoMoreInteractions(part)

        StepVerifier.create(returned)
            .verifyComplete()
    }

    @Test
    fun fetch() {
        val returned = fileHandler.fetch("/test/test-1.0.jar")

        StepVerifier.create(returned)
            .assertNext { assertThat(it.filename).isEqualTo("test-1.0.jar") }
            .verifyComplete()
    }

    @Test
    fun fetchPlugins() {
        val returned = fileHandler.fetchPlugins()

        StepVerifier.create(returned)
            .assertNext { assertThat(it).isEqualTo("test") }
            .verifyComplete()
    }

    @Test
    fun fetchVersions() {
        val returned = fileHandler.fetchVersions("test")

        StepVerifier.create(returned)
            .assertNext { assertThat(it).isEqualTo("test-1.0.jar") }
            .verifyComplete()
    }

    @Test
    fun deletePlugin() {
        val returned = fileHandler.deletePlugin("test")

        StepVerifier.create(returned)
            .assertNext { assertThat(it).isEqualTo("test") }
            .verifyComplete()
    }

    @Test
    fun deleteVersion() {
        val returned = fileHandler.deleteVersion("test", "1.0")

        StepVerifier.create(returned)
            .assertNext { assertThat(it).isEqualTo("test-1.0.jar") }
            .verifyComplete()
    }
}