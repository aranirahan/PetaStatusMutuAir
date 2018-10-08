package com.example.aran.petastatusmutuair


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.view.*

open class MainActivity : AppCompatActivity() {

    private val url = "https://www.petastatusmutuair.com/"

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = "Peta Status Mutu Air"

        // Get the web view settings instance
        val settings = web_view.settings

        // Enable java script in web view
        settings.javaScriptEnabled = true

        // Enable and setup web view cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)

        // Enable zooming in web view
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false

        // Zoom web view text
        settings.textZoom = 125

        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true

        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }

        //settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        // More optional settings, you can enable it by yourself
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true

        // WebView settings
        web_view.fitsSystemWindows = true

        /*
            if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration
        */
        web_view.setLayerType(View.LAYER_TYPE_HARDWARE, null)


        // Set web view client
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something
//                toast("Page loading.")

                // Enable disable back forward button
//                button_back.isEnabled = web_view.canGoBack()
//                button_forward.isEnabled = web_view.canGoForward()
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                // Display the loaded page title in a toast message
//                toast("Page loaded: ${view.title}")

                // Enable disable back forward button
//                button_back.isEnabled = web_view.canGoBack()
//                button_forward.isEnabled = web_view.canGoForward()
            }
        }


        // Set web view chrome client
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progress_bar.visibility = View.VISIBLE
                progress_bar.progress = newProgress
                if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                }
            }
        }

        web_view.loadUrl(url)


//        // Load button click listener
//        button_load.setOnClickListener{
//            // Load url in a web view
//            web_view.loadUrl(web_view.url.toString())
//        }
//
//
//        // Back button click listener
//        button_back.setOnClickListener{
//            if(web_view.canGoBack()){
//                // Go to back history
//                web_view.goBack()
//            }
//        }
//
//
//        // Forward button click listener
//        button_forward.setOnClickListener{
//            if(web_view.canGoForward()){
//                // Go to forward history
//                web_view.goForward()
//            }
//        }

//        swipe_container.setOnRefreshListener {
//            swipe_container.visibility = View.VISIBLE
//            web_view.reload()
//            if (swipe_container.isRefreshing)
//                swipe_container.isRefreshing = false
//        }
    }


    // Method to show app exit dialog
    private fun showAppExitDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Please Confirm")
        builder.setMessage("Do you want to exit the app?")
        builder.setCancelable(true)

        builder.setPositiveButton("Yes") { _, _ ->
            // Do something when user want to exit the app
            // Let allow the system to handle the event, such as exit the app
            super@MainActivity.onBackPressed()
        }

        builder.setNegativeButton("No") { _, _ ->
            // Do something when want to stay in the app
//            toast("thank you.")
        }

        // Create the alert dialog using alert dialog builder
        val dialog = builder.create()

        // Finally, display the dialog when user press back button
        dialog.show()
    }


    // Handle back button press in web view
    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            // If web view have back history, then go to the web view back history
//            toast("Going to back history")
            web_view.goBack()
        } else {
            // Ask the user to exit the app or stay in here
            showAppExitDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.refresh)
            web_view.reload()
        return true
    }

//    fun Context.toast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
}


