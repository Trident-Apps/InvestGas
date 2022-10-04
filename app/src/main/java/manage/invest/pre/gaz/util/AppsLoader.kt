package manage.invest.pre.gaz.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import manage.invest.pre.gaz.R

class AppsLoader(activity: Activity) {
    private val pref = activity.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getApps(activity: Activity): Flow<MutableMap<String, Any>?> = callbackFlow {
        Log.d("customTagApps", " in apps")
        AppsFlyerLib.getInstance()
            .init(
                activity.getString(R.string.apps_dev_key),
                object : AppsFlyerConversionListener {
                    @SuppressLint("CommitPrefEdits")
                    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                        trySend(data)
                        Log.d("customTagApps", " apps success")
                        pref.edit {
                            putBoolean("isStarted", true)
                        }
                        Log.d("customTagApps", "apps success is ${pref.getBoolean("isStarted", false).toString()}")

                    }

                    override fun onConversionDataFail(p0: String?) {
                        trySend(null)
                        Log.d("customTagApps", " apps fail")
                    }

                    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

                    override fun onAttributionFailure(p0: String?) {}

                },
                activity
            )
        AppsFlyerLib.getInstance().start(activity)
        awaitClose {
            cancel()
        }
    }
}