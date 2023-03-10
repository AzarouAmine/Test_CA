package come.azaroumedamine.mobiletest.ca.ui.simulation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import come.azaroumedamine.mobiletest.ca.databinding.FragmentSimulationBinding

class SimulationFragment : Fragment() {

    private var _binding: FragmentSimulationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val simulationViewModel =
                ViewModelProvider(this).get(SimulationViewModel::class.java)

        _binding = FragmentSimulationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSimulation
        simulationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}