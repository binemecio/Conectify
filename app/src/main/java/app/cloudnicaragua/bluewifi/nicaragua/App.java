//package com.example.binemecio.conectify;
//
//import android.app.Application;
//import android.util.Log;
//
//import android.arch.lifecycle.ProcessLifecycleOwner
///**
// * Created by binemecio on 6/5/2019.
// */
//
//public class App extends Application {
//
//
//    interface SampleLifecycleListerner {
//        void SampleLifecycleListener();
//    }
//
//    SampleLifecycleListerner lifecycleListener;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        addFinalSetup();
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        val lifeCycleHandler = AppLifecycleHandler(this)
//        registerLifecycleHandler(lifeCycleHandler)
//    }
//
//    private void addFinalSetup()
//    {
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleListener);
//    }
//
//}
