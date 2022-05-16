package com.jtmnetwork.monitor.service.core.domain.model.spigot

data class SpigotResource(val external: Boolean, val file: SpigotFile, val likes: Int, val testedVersions: Array<String> = arrayOf(),
                          val links: Map<String, String> = HashMap(), val name: String, val tag: String, val version: SpigotVersion,
                          val author: SpigotLink, val category: SpigotLink, val rating: SpigotRating, val icon: SpigotIcon, val releaseDate: Long,
                          val updateDate: Long, val downloads: Int, val existenceStatus: Int, val id: Int, val premium: Boolean = false, val price: Int = 0,
                          val currency: String = "", val sourceCodeLink: String = "", val donationLink: String = "", val reviews: Array<SpigotLink>,
                          val versions: Array<SpigotLink> = arrayOf(), val updates: Array<SpigotLink> = arrayOf()
)
