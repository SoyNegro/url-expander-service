package dev.coffeecult.urlshortener

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime


// Better if it is an interface
@Document
data class Response(
   val id: ObjectId = ObjectId.get(),
   val baseUrl: String,
   val shortenUrl: String,
   val clickersInfo: MutableList<ClickerInfo> = mutableListOf()
)

data class ClickerInfo(
    val deviceDetails: String = "Unknown",
    val ip: String = "Unknown",
    val date: LocalDateTime = LocalDateTime.now()
)

interface ResponseRepository : MongoRepository<Response, String> {
    fun getResponseByShortenUrlEquals(shortenUrl: String): Response?
    fun existsResponseByShortenUrlEquals(shortenUrl: String):Boolean
    fun existsResponseByBaseUrlEquals(baseUrl: String):Boolean
}
