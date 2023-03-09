package come.azaroumedamine.mobiletest.ca.ui.accounts.operations.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import come.azaroumedamine.mobiletest.ca.databinding.ItemHeaderViewBinding
import come.azaroumedamine.mobiletest.ca.databinding.OperationItemViewBinding
import come.azaroumedamine.mobiletest.ca.utils.DateUtils.getDateFromTimeStamp
import come.azaroumedamine.mobiletest.ca.utils.TextUtils

class OperationItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ConstraintLayout(context, attrs) {

    data class OperationItemViewUIModel (
        val title: String,
        val amount: Float,
        val date: String
    )

    private val binding = OperationItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setData(data: OperationItemViewUIModel) {
        with(binding) {
            operationAmount.text = TextUtils.parseAmountToEur(data.amount)
            operationTitle.text = data.title
            operationDate.text = getDateFromTimeStamp(data.date)
        }
    }
}