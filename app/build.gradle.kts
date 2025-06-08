import java.util.Properties
import java.io.FileInputStream
import java.util.Base64

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// Nạp thông tin ký APK từ tệp local hoặc biến môi trường
val keystorePropertiesFile = rootProject.file("keystore.properties")
val useKeystoreProperties = keystorePropertiesFile.exists()
val keystoreProperties = Properties()
if (useKeystoreProperties) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.example.cicd"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cicd"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"  // Đã điều chỉnh theo semantic versioning

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            if (useKeystoreProperties) {
                // Sử dụng tệp keystore.properties trong môi trường phát triển
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            } else {
                // Sử dụng biến môi trường trong CI
                keyAlias = System.getenv("KEYSTORE_KEY_ALIAS") ?: ""
                keyPassword = System.getenv("KEYSTORE_KEY_PASSWORD") ?: ""
                storePassword = System.getenv("KEYSTORE_STORE_PASSWORD") ?: ""
                val keystoreBase64 = System.getenv("KEYSTORE_BASE64")
                if (!keystoreBase64.isNullOrEmpty()) {
                    val keyStoreFile = file("$buildDir/keystore/release.keystore")
                    keyStoreFile.parentFile.mkdirs()
                    keyStoreFile.createNewFile()
                    // Sửa lại cách decode base64 để tránh lỗi unresolved reference
                    val decoded = Base64.getDecoder().decode(keystoreBase64)
                    keyStoreFile.writeBytes(decoded)
                    storeFile = keyStoreFile
                }
            }
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // Thêm flavor cho các môi trường khác nhau
    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            // Thiết lập tên ứng dụng cho môi trường dev
            manifestPlaceholders["appName"] = "CICD Dev"
            // Ghi đè giá trị resource strings
            resValue("string", "app_name", "CICD Dev")
        }
        create("qa") {
            dimension = "environment"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            // Thiết lập tên ứng dụng cho môi trường qa
            manifestPlaceholders["appName"] = "CICD QA"
            // Ghi đè giá trị resource strings
            resValue("string", "app_name", "CICD QA")
        }
        create("prod") {
            dimension = "environment"
            // Không có hậu tố cho phiên bản production
            // Thiết lập tên ứng dụng cho môi trường prod
            manifestPlaceholders["appName"] = "CICD"
            // Ghi đè giá trị resource strings
            resValue("string", "app_name", "CICD")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true  // Cho phép tạo BuildConfig để truy cập thông tin build
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Cấu hình lint
    lint {
        abortOnError = false     // Không làm trượt build trên lỗi lint trong quá trình phát triển
        checkReleaseBuilds = true // Kiểm tra cho các bản build release
        warningsAsErrors = false  // Không xem warning như lỗi
        baseline = file("lint-baseline.xml") // Tệp baseline cho phép bỏ qua một số lỗi
        disable += "ObsoleteLintCustomCheck"
        // Không thất bại khi tạo baseline mới
        checkDependencies = true
        ignoreTestSources = true
    }

    // Cấu hình test
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Firebase Analytics và Crashlytics
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")

    // DeployGate SDK - Thêm hỗ trợ cho DeployGate trong ứng dụng
    implementation("com.deploygate:sdk:4.5.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

