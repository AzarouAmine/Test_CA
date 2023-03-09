package come.azaroumedamine.mobiletest.ca.ui.accounts.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import come.azaroumedamine.mobiletest.ca.data.models.BankAccount
import come.azaroumedamine.mobiletest.ca.databinding.ItemContentViewBinding
import come.azaroumedamine.mobiletest.ca.databinding.ItemHeaderViewBinding
import come.azaroumedamine.mobiletest.ca.utils.TextUtils

class ItemContentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ConstraintLayout(context, attrs) {

    data class ItemContentViewUIModel (
        val accountId: String,
        val title: String,
        val amount: Float
    )

    private val binding = ItemContentViewBinding.inflate(LayoutInflater.from(context), this, true)

    var onItemClickedListener: ((accountId: String) -> Unit)? = null
    init {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setData(data: ItemContentViewUIModel) {
        with(binding) {
            title.text = data.title
            amount.text = TextUtils.parseAmountToEur(data.amount)
            setOnClickListener {
                onItemClickedListener?.invoke(data.accountId)
            }
        }
    }
}