package manage.invest.pre.gaz

import android.app.Application
import com.onesignal.OneSignal

class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.setAppId(applicationContext.getString(R.string.onesignal_id))
        OneSignal.initWithContext(applicationContext)
    }

    companion object {
        lateinit var adId: String
    }
}