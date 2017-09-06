# autoscrollview
安卓跑马灯式滚动父控件，可放置TextView或ViewGroup于内实现整块自动滚动<br><br>
gradle依赖:<br>
```java
//Step 1. Add it in your root build.gradle at the end of repositories
allprojects {
	repositories {
		...
		aven { url 'https://jitpack.io' }
	}
}
        
	
	
//Step 2. Add the dependency in build.gradle for app
dependencies {
	
	compile 'com.github.hurryD:autoscrollview:v1.0'
		
}

```
<br>
使用方法：<br><br>
```java
//Xml
<com.hurry.autoscrollview.AutoScrollView
        android:id="@+id/asView_main"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        <!-- 单次滚动距离  默认值为2px -->
        app:singleScrollWidth="2"  
        <!-- 单次滚动间隔  默认值为50毫秒 -->
        app:singleDuration="50"
        <!-- 开始滚动时停顿时长  默认值为3800毫秒 -->
        app:startDuration="3500"
        <!-- 滚动完成时停顿时长  默认值为5000毫秒 -->
        app:endDuration="5000"
        <!-- 仅在repeatAble=false时生效; 滚动完成之后是停在尾部<end>还是头部<start>  默认值为尾部 -->
        app:finishState="end"
        <!-- 是否重复滚动  默认值为true -->
        app:repeatAble="true">

        <LinearLayout .../>


</com.hurry.autoscrollview.AutoScrollView>



    //Activity
    private AutoScrollView asView;
    @Override
    protected void initViews() {
        asView = findViewById(R.id.asView_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        asView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        asView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asView.onDestory();
    }

```
