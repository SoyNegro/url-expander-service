package dev.coffeecult.urlshortener

import org.springframework.stereotype.Service
import ua_parser.Parser
import java.math.BigInteger
import java.security.MessageDigest
import javax.servlet.http.HttpServletRequest

@Service
class UrlShortenerService(val repository: ResponseRepository) {
    fun getResponseByShortenUrl(shortenUrl: String): Response? {
        return repository.getResponseByShortenUrlEquals(shortenUrl)
    }

    fun save(response: Response) {
        repository.save(response)
    }

    fun getClickerInfo(httpServletRequest: HttpServletRequest): ClickerInfo {
        val p = Parser()
        val userAgent = httpServletRequest.getHeader("user-agent")
        val xforwardedIp = httpServletRequest.getHeader("x-forwarded-for") ?: httpServletRequest.remoteAddr
        val agent = p.parseUserAgent(userAgent)
        val os = p.parseOS(userAgent)
        val device = p.parseDevice(userAgent)
        val deviceInfo = "${agent.family?:"Unknown"} ${agent.major?:"Unknown"} ${agent.minor?:"Unknown"} ${os.family?:"Unknown"} ${os.major?:"Unknown"} ${os.minor?:"Unknown"} ${device.family?:"Unknown"}"
        return ClickerInfo(deviceInfo, xforwardedIp)
    }

    //To illustrate shortening process
    fun shortenUrl(baseUrl: String): String? {
        val hashByte = MessageDigest.getInstance("SHA-256").digest(baseUrl.toByteArray(Charsets.UTF_8))
        val shortUrl = String.format("%032x", BigInteger(1, hashByte)).take(6)
        when {
            repository.existsResponseByBaseUrlEquals(baseUrl) -> return null
            repository.existsResponseByShortenUrlEquals(shortUrl) -> shortenUrl(baseUrl)
            else -> repository.save(Response(baseUrl = baseUrl,shortenUrl = shortUrl))
        }
        return shortUrl
    }
}