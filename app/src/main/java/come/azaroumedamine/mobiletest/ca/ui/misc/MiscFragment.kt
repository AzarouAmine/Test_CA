package come.azaroumedamine.mobiletest.ca.ui.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import come.azaroumedamine.mobiletest.ca.databinding.FragmentMiscBinding

class MiscFragment : Fragment() {

    private var _binding: FragmentMiscBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val miscViewModel =
                ViewModelProvider(this).get(MiscViewModel::class.java)

        _binding = FragmentMiscBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMisc
        miscViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}