package com.example.cicd

import android.app.Application
import com.deploygate.sdk.DeployGate

/**
 * Application class sử dụng để khởi tạo DeployGate SDK
 * Sẽ được gọi khi ứng dụng khởi động
 */
class CiCdApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Khởi tạo DeployGate SDK chỉ trong các bản build debug
        if (BuildConfig.DEBUG) {
            // Tham số true cho phép tự động báo cáo crash
            DeployGate.install(this, true)
        }
    }
}
