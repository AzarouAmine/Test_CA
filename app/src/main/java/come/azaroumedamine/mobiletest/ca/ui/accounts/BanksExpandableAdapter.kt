package come.azaroumedamine.mobiletest.ca.ui.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import come.azaroumedamine.mobiletest.ca.R
import come.azaroumedamine.mobiletest.ca.data.models.Bank
import come.azaroumedamine.mobiletest.ca.data.models.BankAccount
import come.azaroumedamine.mobiletest.ca.ui.accounts.views.ItemContentView
import come.azaroumedamine.mobiletest.ca.ui.accounts.views.ItemHeaderView
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class BankAccountExpandableAdapter(private val bank: Bank) :
    RecyclerView.Adapter<BankAccountExpandableAdapter.ViewHolder>() {

    companion object {
        private const val ITEM_VIEW_TYPE = 1
        private const val HEADER_VIEW_TYPE = 2

        private const val EXPANDED_ROTATION_DEGREE = 0F
        private const val COLLAPSED_ROTATION_DEGREE = 180F
    }

    // Initial value is not expanded
    private var isExpanded: Boolean by Delegates.observable(false) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, bank.accounts.size)
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, bank.accounts.size)
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) HEADER_VIEW_TYPE else ITEM_VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         when (viewType) {
            HEADER_VIEW_TYPE -> ViewHolder.HeaderViewHolder(
                ItemHeaderView(parent.context)
            )
            ITEM_VIEW_TYPE ->  ViewHolder.ItemViewHolder(
                ItemContentView(parent.context)
            )
            else -> throw IllegalArgumentException("Unsupported view type: $viewType")
        }


    override fun getItemCount(): Int = if (isExpanded) bank.accounts.size + 1 else 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = when (holder) {
        is ViewHolder.ItemViewHolder -> holder.bind(bank.accounts.get(position - 1))
        is ViewHolder.HeaderViewHolder -> {
            holder.bind(bank, isExpanded, onHeaderClickListener)
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class ItemViewHolder(private val item: ItemContentView) : ViewHolder(item) {

            fun bind(content: BankAccount) {
                item.setData(ItemContentView.ItemContentViewUIModel(content.title, content.balance))
            }
        }

        class HeaderViewHolder(private val item: ItemHeaderView) : ViewHolder(item) {
            internal val expandArrow = item.expandArrow
            fun bind(bank: Bank, expanded: Boolean, onClickListener: View.OnClickListener?) {
                val banAccountSumAmount = bank.accounts.map { it.balance }.reduce { sum, element -> sum + element }
                item.setData(ItemHeaderView.ItemHeaderViewUIModel(bank.name, banAccountSumAmount))
                expandArrow.rotation =
                    if (expanded) EXPANDED_ROTATION_DEGREE else COLLAPSED_ROTATION_DEGREE
                itemView.setOnClickListener(onClickListener)
            }
        }
    }
}