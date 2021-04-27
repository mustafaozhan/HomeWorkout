package mustafaozhan.github.com.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import mustafaozhan.github.com.base.BaseDBRecyclerViewAdapter
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.ui.databinding.ItemFavoriteExercisesBinding

class FavoriteExercisesAdapter(
    private val favoriteExercisesEvent: FavoriteExercisesEvent
) : BaseDBRecyclerViewAdapter<Exercise, ItemFavoriteExercisesBinding>(CalculatorDiffer()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CalculatorDBViewHolder(
        ItemFavoriteExercisesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class CalculatorDBViewHolder(itemBinding: ItemFavoriteExercisesBinding) :
        BaseDBViewHolder<Exercise, ItemFavoriteExercisesBinding>(itemBinding) {

        override fun onItemBind(item: Exercise) = with(itemBinding) {
            this.item = item
            this.event = favoriteExercisesEvent
        }
    }

    class CalculatorDiffer : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(old: Exercise, aNew: Exercise) = old == aNew

        override fun areContentsTheSame(old: Exercise, aNew: Exercise) = false
    }
}
