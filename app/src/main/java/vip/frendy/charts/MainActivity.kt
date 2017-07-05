package vip.frendy.charts

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.github.abel533.echarts.axis.CategoryAxis
import com.github.abel533.echarts.axis.ValueAxis
import com.github.abel533.echarts.code.Symbol
import com.github.abel533.echarts.code.Trigger
import com.github.abel533.echarts.data.LineData
import com.github.abel533.echarts.data.PieData
import com.github.abel533.echarts.json.GsonOption
import com.github.abel533.echarts.series.Line
import com.github.abel533.echarts.series.Pie
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import vip.frendy.chart.EChartWebView

class MainActivity : FragmentActivity(), EChartWebView.DataSource {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chartView.setType(1)
        chartView.setDataSource(this)

        button.setOnClickListener { startActivity<DemoActivity>() }
    }

    override fun markChartOptions(): GsonOption {
        return getPieChartOptions()
    }

    override fun markChartOptions01(): GsonOption {
        return getLineChartOptions01()
    }

    fun getLineChartOptions(): GsonOption {
        val option = GsonOption()
        option.legend("高度(km)与气温(°C)变化关系")

//        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage)

        option.calculable(true)
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C")

        val valueAxis = ValueAxis()
        valueAxis.axisLabel().formatter("{value} °C")
        option.xAxis(valueAxis)

        val categoryAxis = CategoryAxis()
        categoryAxis.axisLine().onZero(false)
        categoryAxis.axisLabel().formatter("{value} km")
        categoryAxis.boundaryGap(false)
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80)
        option.yAxis(categoryAxis)

        val line = Line()
        line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)")
        option.series(line)
        return option
    }

    fun getLineChartOptions01(): GsonOption {
        val option = GsonOption()
        option.tooltip().trigger(Trigger.axis)
        option.legend("邮件营销", "联盟广告", "直接访问", "搜索引擎")
        option.calculable(true)
        option.xAxis(CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"))
        option.yAxis(ValueAxis())
        option.series(Line().smooth(true).name("邮件营销").stack("总量").symbol(Symbol.none).data(120, 132, 301, 134, LineData(90, Symbol.droplet, 5), 230, 210))

        val lineData = LineData(201, Symbol.star, 15)
        lineData.itemStyle().normal().label().show(true).textStyle().fontSize(20).fontFamily("微软雅黑").fontWeight("bold")
        option.series(Line().smooth(true).name("联盟广告").stack("总量").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").symbolSize(8).data(120, 82, lineData, LineData(134, Symbol.none), 190, LineData(230, Symbol.emptypin, 8), 110))

        option.exportToHtml("line.html")
        option.view()
        return option
    }

    fun getPieChartOptions(): GsonOption {
        val option = GsonOption()
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)")
        option.legend().data("直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎");

        val pie = getPie().center("50%", "45%").radius("50%")
        pie.label().normal().show(true).formatter("{b}{c}({d}%)")
        option.series(pie)
        return option
    }

    fun getPie(): Pie {
        return Pie().name("访问来源").data(
                PieData("直接访问", 335),
                PieData("邮件营销", 310),
                PieData("联盟广告", 274),
                PieData("视频广告", 235),
                PieData("搜索引擎", 400))
    }
}
