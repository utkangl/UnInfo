package com.utkangul.invio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.utkangul.invio.R

class WebsiteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website)

        println(currentUrl)
        val webView = findViewById<WebView>(R.id.universityWebsiteView)
        val fixedUrl = fixUrl(currentUrl)
        if (!currentUrl.isNullOrEmpty())  webView.loadUrl(fixedUrl)

        else onBackPressedDispatcher.onBackPressed()

        println(fixedUrl)



        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)


        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                println("Web sayfası yüklenirken hata oluştu: $description")
                onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    fun fixUrl(url: String?): String {
        return if (!url?.startsWith("https://")!!) {
            "https://$url"
        } else {
            url
        }
    }
}