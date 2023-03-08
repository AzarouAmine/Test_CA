package come.azaroumedamine.mobiletest.ca.ui.accounts

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

// Taken from: https://github.com/OHoussein/Android-Expandable-ConcatAdapter/blob/master/app/src/main/java/dev/ohoussein/mergeadaptertutorial/ExpandableItemAnimator.kt
class ExpandableItemAnimator : DefaultItemAnimator() {

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        return if (viewHolder is BankAccountExpandableAdapter.ViewHolder.HeaderViewHolder) {
            HeaderItemInfo().also {
                it.setFrom(viewHolder)
            }
        } else {
            super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
        }
    }

    override fun recordPostLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder
    ): ItemHolderInfo {
        return if (viewHolder is BankAccountExpandableAdapter.ViewHolder.HeaderViewHolder) {
            HeaderItemInfo().also {
                it.setFrom(viewHolder)
            }
        } else {
            super.recordPostLayoutInformation(state, viewHolder)
        }
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        holder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        if (preInfo is HeaderItemInfo && postInfo is HeaderItemInfo && holder is BankAccountExpandableAdapter.ViewHolder.HeaderViewHolder) {
            ObjectAnimator
                .ofFloat(
                    holder.expandArrow,
                    View.ROTATION,
                    preInfo.arrowRotation,
                    postInfo.arrowRotation
                )
                .also {
                    it.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            holder.expandArrow.rotation = postInfo.arrowRotation
                            dispatchAnimationFinished(holder)
                        }
                    })
                    it.start()
                }
        }
        return super.animateChange(oldHolder, holder, preInfo, postInfo)
    }

    //It means that for animation we don’t need to have separated objects of ViewHolder (old and new holder)
    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }
}

class HeaderItemInfo : RecyclerView.ItemAnimator.ItemHolderInfo() {

    internal var arrowRotation: Float = 0F

    override fun setFrom(holder: RecyclerView.ViewHolder): RecyclerView.ItemAnimator.ItemHolderInfo {
        if (holder is BankAccountExpandableAdapter.ViewHolder.HeaderViewHolder) {
            arrowRotation = holder.expandArrow.rotation
        }
        return super.setFrom(holder)
    }
}