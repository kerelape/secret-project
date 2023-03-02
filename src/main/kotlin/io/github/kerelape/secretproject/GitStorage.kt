package io.github.kerelape.secretproject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.eclipse.jgit.api.Git
import java.io.File
import java.io.FilenameFilter
import java.nio.file.Files
import java.util.*

class GitStorage(private val repository: Git, private val filter: FilenameFilter) : Storage {

    override suspend fun files(): Iterable<File> = withContext(Dispatchers.IO) {
        val repository = this@GitStorage.repository
        repository.fetch().call()
        repository.pull().call()
        repository.repository
            .directory
            .parentFile
            .listFiles(this@GitStorage.filter)
            .toList()
            .sortedBy { it.name.split(".")[1].toLong() }
    }

    override suspend fun add(file: File) {
        withContext(Dispatchers.IO) {
            val repository = this@GitStorage.repository
            repository.fetch().call()
            repository.pull().call()
            val filename = "${UUID.randomUUID()}.${System.currentTimeMillis()}.${file.extension}"
            Files.copy(file.toPath(), File(repository.repository.directory.parentFile, filename).toPath())
            repository.add().addFilepattern(".").call()
            repository.commit()
                .setCommitter("secret-project", "secret-project-mod@outlook.com")
                .setAuthor("secret-project", "secret-project-mod@outlook.com")
                .call()
            repository.push().call()
        }
    }
}
