package mustafaozhan.github.com.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import mustafaozhan.github.com.base.BaseDBRecyclerViewAdapter
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.ui.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private val exerciseEvent: ExerciseEvent
) : BaseDBRecyclerViewAdapter<Exercise, ItemExerciseBinding>(CalculatorDiffer()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CalculatorDBViewHolder(
        ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class CalculatorDBViewHolder(itemBinding: ItemExerciseBinding) :
        BaseDBViewHolder<Exercise, ItemExerciseBinding>(itemBinding) {

        override fun onItemBind(item: Exercise) = with(itemBinding) {
            this.item = item
            this.event = exerciseEvent
        }
    }

    class CalculatorDiffer : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(old: Exercise, aNew: Exercise) =
            old == aNew

        override fun areContentsTheSame(old: Exercise, aNew: Exercise) = false
    }
}
