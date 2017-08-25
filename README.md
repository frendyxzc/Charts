# AndroidCharts

[![](https://jitpack.io/v/frendyxzc/Charts.svg)](https://jitpack.io/#frendyxzc/Charts)

Android Charts base on ECharts


## Demo:

![001.jpg](http://upload-images.jianshu.io/upload_images/6306778-2a949dc16ab46a28.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Usage:

### 1. Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### 2. Add the dependency:

```
dependencies {
	compile 'com.github.frendyxzc:Charts:0.0.2'
}
```

### 3. Draw charts:

#### 3.1 Handle datas on Android:

```android
chartView.setType(1)
chartView.setDataSource(this)
```

```android
override fun markChartOptions(): GsonOption {
	return getPieChartOptions()
}
```

```android
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
```

```js
var option = JSON.parse(Android.getChartOptions());
chart.setOption(option);
```

#### 3.2 Handle datas on JS (load ready-made ECharts directly):

```android
chartView.loadUrl("file:///android_asset/echart/biz/map.html")
```

```js
function load() {
    option = {
        title: {
            text: 'iphone销量',
            subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:['iphone3','iphone4','iphone5']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text: ['高','低'],           // 文本，默认为数值文本
            calculable: true
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            ...
        ]
    };

    chart.setOption(option);
}
```

-----

## Todo:

* Wrap with Kotlin DSL
* ...

-----

## More info:

* [轻松实现 Android 图表 • ECharts](http://www.jianshu.com/p/e13da6f85927)

