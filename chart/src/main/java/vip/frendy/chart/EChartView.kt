package vip.frendy.chart

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar



/**
 * Created by frendy on 2017/7/5.
 */
class EChartView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(mContext, attrs, defStyle) {

    internal var mWebView: EChartWebView? = null
    internal var mProgressBar: ProgressBar? = null

    var url: String? = null

    init {
        initView(mContext)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.chart_view, this)
        mWebView = view.findViewById(R.id.web_view) as EChartWebView
        mProgressBar = view.findViewById(R.id.progress_bar) as ProgressBar
    }


    fun setType(type: Int) {
        mWebView!!.setType(type)
    }

    fun setDataSource(data: EChartWebView.DataSource) {
        mWebView!!.setDataSource(data)
    }

    fun loadUrl(url: String?) {
        if (url == null) return
        initWebview(url)
    }


    private fun initWebview(url: String) {

        mWebView!!.loadUrl(url)

        // 设置WebViewClient
        mWebView!!.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                view?.loadUrl(url)
                // 相应完成返回true
                return true
                // return super.shouldOverrideUrlLoading(view, url);
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                mProgressBar!!.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                mProgressBar!!.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//				view.loadData(errorHtml, "text/html; charset=UTF-8", null);
                super.onReceivedError(view, request, error)
            }
        })

        // 设置WebChromeClient
        mWebView!!.setWebChromeClient(object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mProgressBar!!.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        })
        mWebView!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 表示按返回键
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView!!.canGoBack()) {
                        mWebView!!.goBack() // 后退
                        return true // 已处理
                    }
                }
                return false
            }
        })
    }

    fun canBack(): Boolean {
        if (mWebView!!.canGoBack()) {
            mWebView!!.goBack()
            return false
        }
        return true
    }
}