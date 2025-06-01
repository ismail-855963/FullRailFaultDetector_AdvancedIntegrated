# DAT – Demiryolu Arıza Tespit

## Proje Kurulumu ve APK Oluşturma

### 1. Projeyi Aç ve Sync Et
- Android Studio’da **File > Open** ile bu klasörü açın.
- **File > Sync Project with Gradle Files** ile bağımlılıkları yükleyin.

### 2. ViewBinding Etkinleştirme
- `app/build.gradle` içinde:
```gradle
android {
    ...
    buildFeatures {
        viewBinding true
    }
}
```
- Dosyayı kaydederek **Sync** edin.

### 3. Signed APK Oluşturma (Release)
- **Build > Generate Signed Bundle / APK...** seçin.
- “APK” seçip **Next**.
- Yeni veya varolan bir keystore (.jks) dosyası seçin ve gerekli bilgileri girin.
- Variant olarak **release** seçin ve **Finish**.

APK dosyası oluşturulduktan sonra:
```
app/build/outputs/apk/release/app-release.apk
```
yolundan erişebilirsiniz.

### 4. Komut Satırından APK Oluşturma
Terminalde proje kökünde:
```bash
./gradlew assembleRelease
```
ile release APK elde edebilirsiniz.

## Unit Testler (SimpleAnomalyDetector)
`app/src/test/java/com/example/railfalldetector/SimpleAnomalyDetectorTest.kt` dosyasını ekleyip aşağıdaki testi çalıştırabilirsiniz:
```kotlin
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SimpleAnomalyDetectorTest {
    @Before
    fun setup() {
        SimpleAnomalyDetector.calibrationMean = 10.0
        SimpleAnomalyDetector.thresholdMultiplier = 2.0
    }

    @Test
    fun testNoAnomaly_whenValueBelowThreshold() {
        assertFalse(SimpleAnomalyDetector.isAnomaly(19.9))
    }

    @Test
    fun testIsAnomaly_whenValueAboveThreshold() {
        assertTrue(SimpleAnomalyDetector.isAnomaly(20.1))
    }

    @Test
    fun testThresholdMultiplierChange() {
        SimpleAnomalyDetector.thresholdMultiplier = 3.0
        assertFalse(SimpleAnomalyDetector.isAnomaly(29.9))
        assertTrue(SimpleAnomalyDetector.isAnomaly(30.1))
    }
}
```

## Proje Özellikleri
- Sensör tabanlı dresaj ve düşük tespiti
- Kalibrasyon & eşik belirleme
- Canlı grafik, harita, raporlama, ML etiketleme, ayarlar, hakkında
- Otomatik kurp ve hat algılama
- Firebase offline persistence & senkronizasyon
