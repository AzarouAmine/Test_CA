package come.azaroumedamine.mobiletest.ca.ui.accounts.operations

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import come.azaroumedamine.mobiletest.ca.data.models.BankAccount
import come.azaroumedamine.mobiletest.ca.ui.accounts.operations.views.OperationItemView
import come.azaroumedamine.mobiletest.ca.utils.toFloatUnformatted

class AccountOperationsAdapter(private val bankAccount: BankAccount)
    : RecyclerView.Adapter<AccountOperationsAdapter.ViewHolder>() {

    companion object {
        private const val OPERATION_ITEM_VIEW_TYPE = 1
    }

    override fun getItemCount() = bankAccount.operations.size

    override fun getItemViewType(position: Int): Int = OPERATION_ITEM_VIEW_TYPE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        when(viewType) {
            OPERATION_ITEM_VIEW_TYPE -> ViewHolder.OperationViewHolder(OperationItemView((parent.context)))
            else -> throw IllegalArgumentException("Unsupported view type: $viewType")
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = when(holder) {
        is ViewHolder.OperationViewHolder -> holder.bind(
            bankAccount.operations.get(position).title,
            bankAccount.operations.get(position).amount.toFloatUnformatted(),
            bankAccount.operations.get(position).date)
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class OperationViewHolder(private val item: OperationItemView) : ViewHolder(item) {
            fun bind(title: String, amount: Float, date: String) {
                item.setData(OperationItemView.OperationItemViewUIModel(title, amount, date))
            }
        }
    }
}