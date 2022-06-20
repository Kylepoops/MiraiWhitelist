package cn.cubegarden.miraiwhitelist.manager

import cn.cubegarden.miraiwhitelist.entity.UUIDQueryResponse
import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.regex.Pattern

object UUIDManager {
    private val UUID_FIX = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})")
    private val gson = Gson()

    val httpClient = HttpClient.newHttpClient()

    fun queryUUIDAsync(playerName: String): CompletableFuture<UUID> {
        val request = HttpRequest
            .newBuilder(URI.create("https://api.mojang.com/users/profiles/minecraft/$playerName"))
            .build()

        return httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply {
            if (it.statusCode() == 204) {
                throw IllegalArgumentException("Player not found")
            }
            val id = gson.fromJson(it.body(), UUIDQueryResponse::class.java).id
            formatUUID(id)
        }
    }

    private fun formatUUID(id: String): UUID {
        return UUID.fromString(UUID_FIX.matcher(id.replace("-", "")).replaceAll("$1-$2-$3-$4-$5"))
    }
}
