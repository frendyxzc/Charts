

$(function() {
    init();
});

function toast(msg){
    Android.showToast(msg);
}

function init() {
    initChartView();
}

function initChartView() {
	// 必须加JOSN.parse 转换数据类型
    var option = JSON.parse(Android.getChartOptions());
    var chartDoc = document.getElementById('chart');
    /**
     * 设置lineChart的高度为Android中控件WebView的高度
     * var height = document.documentElement.clientHeight;
     * var height = window.innerHeight
     * 建议选择第二个获取高度的方法
     */
    var height = window.innerHeight;
    $(chartDoc).css('height', height);

    var chart = echarts.init(chartDoc);
    chart.setOption(option);
}
