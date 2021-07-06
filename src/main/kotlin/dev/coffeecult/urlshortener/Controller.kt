package dev.coffeecult.urlshortener

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletRequest

@Controller
class Controller(val urlShortenerService: UrlShortenerService) {
  private val redirectUrl  = System.getenv("Home_Url")?:"https://www.google.com.cu/"
  @GetMapping("/{shortenUrl}")
  fun getBaseUrl(@PathVariable shortenUrl:String, httpServletRequest: HttpServletRequest): RedirectView {
      val r = urlShortenerService.getResponseByShortenUrl(shortenUrl)
      return if ( r != null) {
          r.clickersInfo.add(urlShortenerService.getClickerInfo(httpServletRequest))
          urlShortenerService.save(r)
          RedirectView(r.baseUrl)
      } else RedirectView(redirectUrl)
  }
    @GetMapping("/", "/**/**")
    fun redirectToHome():RedirectView{
        return RedirectView(redirectUrl)
    }

}