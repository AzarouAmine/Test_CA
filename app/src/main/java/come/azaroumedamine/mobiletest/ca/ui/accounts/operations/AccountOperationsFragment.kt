package come.azaroumedamine.mobiletest.ca.ui.accounts.operations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import come.azaroumedamine.mobiletest.ca.data.models.BankAccount
import come.azaroumedamine.mobiletest.ca.databinding.FragmentAccountOperationsBinding
import come.azaroumedamine.mobiletest.ca.ui.accounts.BanksViewModel
import come.azaroumedamine.mobiletest.ca.utils.DateUtils
import come.azaroumedamine.mobiletest.ca.utils.TextUtils

class AccountOperationsFragment : Fragment() {

    companion object {
        const val ACCOUNT_ID_EXTRA = "accountId"
    }

    private var _binding: FragmentAccountOperationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BanksViewModel by activityViewModels()
    private lateinit var adapter: AccountOperationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAccountOperationsBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(ACCOUNT_ID_EXTRA)?.let {
            viewModel.getAccountById(it)?.let { bankAccount ->
                setupRecyclerView(bankAccount)
                with(binding) {
                    accountAmount.text = TextUtils.parseAmountToEur(bankAccount.balance)
                    accountTitle.text = bankAccount.title
                }
            } ?: navigateBack()
        } ?: navigateBack()
    }

    // Show a toast error message and navigate to previous fragment.
    private fun navigateBack() {
        Toast.makeText(requireContext(), "Error encountered while setting up fragment data.", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    private fun setupRecyclerView(bankAccount: BankAccount) {
        val bankAccountsSorted = bankAccount.copy(id = bankAccount.id,
            title = bankAccount.title,
            balance = bankAccount.balance,
            operations = bankAccount.operations.sortedWith(
                compareBy({ DateUtils.getDateFromTimeStamp(it.date) }, { it.title })
            ))
        adapter = AccountOperationsAdapter(bankAccountsSorted)
        with(binding) {
            accountOperationsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            accountOperationsRecyclerview.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}