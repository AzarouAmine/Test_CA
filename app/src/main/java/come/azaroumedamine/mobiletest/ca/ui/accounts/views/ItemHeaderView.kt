package come.azaroumedamine.mobiletest.ca.ui.accounts.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import come.azaroumedamine.mobiletest.ca.databinding.ItemHeaderViewBinding
import come.azaroumedamine.mobiletest.ca.utils.TextUtils

class ItemHeaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ConstraintLayout(context, attrs) {

    data class ItemHeaderViewUIModel (
        val title: String,
        val amount: Float
    )

    private val binding = ItemHeaderViewBinding.inflate(LayoutInflater.from(context), this, true)
    val expandArrow by binding::expandArrow

    init {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setData(data: ItemHeaderViewUIModel) {
        with(binding) {
            title.text = data.title
            amount.text = TextUtils.parseAmountToEur(data.amount)
        }
    }
}