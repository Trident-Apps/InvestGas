package manage.invest.pre.gaz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import manage.invest.pre.gaz.CustomApp.Companion.adId
import manage.invest.pre.gaz.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            adId = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id.toString()
            OneSignal.setExternalUserId(adId)
        }
    }


}