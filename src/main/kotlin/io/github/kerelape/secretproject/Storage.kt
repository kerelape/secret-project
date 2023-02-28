package io.github.kerelape.secretproject

import java.io.File

/**
 * Storage of files.
 * 
 * @since 0.0.1
 */
interface Storage {

    /**
     * Retreive all available files.
     * 
     * @return All files in the storage.
     */
    suspend fun files(): Iterable<File>

    /**
     * Add a file this the storage.
     */
    suspend fun add(file: File)
}
