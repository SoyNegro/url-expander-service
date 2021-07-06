package dev.coffeecult.urlshortener

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class UrlshortenerApplication

fun main(args: Array<String>) {
    runApplication<UrlshortenerApplication>(*args)
}

// To illustrate the shortening process
@Service
class Initializer(val urlShortenerService: UrlShortenerService):CommandLineRunner{
    override fun run(vararg args: String?) {
        val a = urlShortenerService.shortenUrl("http://localhost:8080/thelargest/biggest/greatest/path/ever")
        val b = urlShortenerService.shortenUrl("http://localhost:8080/thelargest/notthatbiggest/greatest/path/ever")
        val c = urlShortenerService.shortenUrl("http://localhost:8080/thelargest/notthatbiggest/notthatgreatest/path/ever")
        println("$a \n$b \n$c")
    }

}