package come.azaroumedamine.mobiletest.ca.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import come.azaroumedamine.mobiletest.ca.R
import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.databinding.FragmentAccountsBinding
import come.azaroumedamine.mobiletest.ca.ui.accounts.operations.AccountOperationsFragment.Companion.ACCOUNT_ID_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BanksViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = FragmentAccountsBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.caBanks().observe(viewLifecycleOwner) {
            binding.caBanksRecyclerview.adapter = getAdapter(it)
        }
        viewModel.otherBanks().observe(viewLifecycleOwner) {
            binding.otherBanksRecyclerview.adapter = getAdapter(it)
        }
    }

    private fun getAdapter(banks: List<Bank>?): ConcatAdapter {
        val adapters: List<BankAccountExpandableAdapter> = banks?.map { bank ->
            val adapter = BankAccountExpandableAdapter(bank)
            adapter.onItemClickedListener = { accountId ->
                findNavController().navigate(
                    R.id.action_accountsFragment_to_accountOperationsFragment,
                    bundleOf(ACCOUNT_ID_EXTRA to accountId)
                )
            }
            adapter
        } ?: emptyList()
        val concatAdapterConfig = ConcatAdapter.Config.Builder()
            .build()
        return ConcatAdapter(concatAdapterConfig, adapters)
    }

    private fun setupRecyclerView() {
        with(binding) {
            caBanksRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            caBanksRecyclerview.itemAnimator = ExpandableItemAnimator()

            otherBanksRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            otherBanksRecyclerview.itemAnimator = ExpandableItemAnimator()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}