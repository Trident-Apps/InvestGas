package manage.invest.pre.gaz.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import manage.invest.pre.gaz.R
import manage.invest.pre.gaz.databinding.FinalFragmentBinding
import manage.invest.pre.gaz.databinding.FirstQuestionFragmentBinding

class FinalFragment : Fragment() {
    private var _binding: FinalFragmentBinding? = null
    private val binding get() = _binding!!

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
        _binding = FinalFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finalBtn.setOnClickListener {
            findNavController().navigate(R.id.introductionFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}