package vip.frendy.chart

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.github.abel533.echarts.json.GsonOption

/**
 * Created by frendy on 2017/7/4.
 */
class EChartWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val webSettings = settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportZoom(true)
        webSettings.displayZoomControls = true

        addJavascriptInterface(EChartInterface(context), "Android")
//        loadUrl("file:///android_asset/echart/biz/echart.html")
    }

    fun setType(type: Int) {
        var index = type
        if(type < 0 || type > 4) index = 0
        loadUrl("file:///android_asset/echart/biz/echart-$index.html")
    }

    /**
     * data source
     */
    private var dataSource: DataSource? = null

    fun setDataSource(data: DataSource) {
        dataSource = data
        reload()
    }

    interface DataSource {
        fun markChartOptions(): GsonOption
        fun markChartOptions01(): GsonOption
    }

    /**
     * interface
     */
    internal inner class EChartInterface(var context: Context) {

        val chartOptions: String?
            @JavascriptInterface
            get() {
                if (dataSource != null) {
                    val option = dataSource!!.markChartOptions()
                    return option.toString()
                }
                return null
            }

        val chartOptions01: String?
            @JavascriptInterface
            get() {
                if (dataSource != null) {
                    val option = dataSource!!.markChartOptions01()
                    return option.toString()
                }
                return null
            }

        @JavascriptInterface
        fun showToast(msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}