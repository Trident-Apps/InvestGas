package manage.invest.pre.gaz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import manage.invest.pre.gaz.R
import manage.invest.pre.gaz.databinding.SecondQuestionFragmentBinding

class SecondQFragment : Fragment() {
    private var _binding: SecondQuestionFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SecondQuestionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            q3Iv.setOnClickListener {
                q3Iv.visibility = View.INVISIBLE
                q3Btn.visibility = View.VISIBLE
                q3Text.visibility = View.VISIBLE
            }

            q3Btn.setOnClickListener {
                findNavController().navigate(R.id.thirdQFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}