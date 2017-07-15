# RippleRelativeLayout
<h1>Android RelativeLayout That Ripple With out Any Code</h1>
<img src="https://github.com/YSaeid/RippleView/blob/master/ScreenShot.gif">
<h1>Installation</h1>
<span>Add this line to your project build.gradle</span>

```javascript
allprojects {
    repositories {
        maven { url 'https://jitpack.io' } //add this
        jcenter()
    }
}
```

<span>Add this line to your Module build.gradle</span>

```javascript
dependencies {
    compile 'com.github.YSaeid:RippleView:1.0.1'
}
```

<span>Add The RippleRelativeLayout to your layout xml file</span>

```javascript
<yazdany.saeid.view.rippleview.RippleRelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</yazdany.saeid.view.rippleview.RippleRelativeLayout>
```

<span>use this attribute</span>

```javascript
    app:autoStart="true"
    app:rippleColor="6"
    app:ripplaCount="6"
    app:rippleAnimateDuration="3000"
    app:rippleDelay="500"
    app:rippleRadius="200dp"
    app:rippleScale="4"
    app:rippleType="fill"
    app:rippleStokeWidth="4dp"
 ```
 
 ```javascript
<yazdany.saeid.view.rippleview.RippleRelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:autoStart="true"
    app:rippleColor="6"
    app:ripplaCount="6"
    app:rippleAnimateDuration="3000"
    app:rippleDelay="500"
    app:rippleRadius="200dp"
    app:rippleScale="4"
    app:rippleType="fill"
    app:rippleStokeWidth="4dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</yazdany.saeid.view.rippleview.RippleRelativeLayout>
```

<span>use method if you want</span>

```javascript
ripple.playRipple();
ripple.stopRipple();
```

<h1>THATS IT :)</h1>
