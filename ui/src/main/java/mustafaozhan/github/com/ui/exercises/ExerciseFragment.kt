package mustafaozhan.github.com.ui.exercises

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import mustafaozhan.github.com.base.fragment.BaseDBFragment
import mustafaozhan.github.com.ui.R
import mustafaozhan.github.com.ui.databinding.FragmentExerciseBinding
import mustafaozhan.github.com.util.reObserve
import javax.inject.Inject

class ExerciseFragment : BaseDBFragment<FragmentExerciseBinding>() {

    @Inject
    lateinit var exerciseViewModel: ExerciseViewModel

    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun bind(container: ViewGroup?): FragmentExerciseBinding =
        FragmentExerciseBinding.inflate(layoutInflater, container, false)

    override fun onBinding(dataBinding: FragmentExerciseBinding) {
        binding.vm = exerciseViewModel
        exerciseViewModel.getEvent().let {
            binding.event = it
            exerciseAdapter = ExerciseAdapter(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeEffects()
    }

    private fun observeEffects() {
        exerciseViewModel.effect.reObserve(viewLifecycleOwner, { exerciseEffect ->
            when (exerciseEffect) {
                is ExerciseEffect.PlayExercise -> navigate(
                    R.id.exerciseFragment,
                    ExerciseFragmentDirections.actionExerciseFragmentToPlayerFragment(
                        exerciseEffect.exercise.toTypedArray()
                    )
                )
                is ExerciseEffect.OpenFavoriteExercises -> navigate(
                    R.id.exerciseFragment,
                    ExerciseFragmentDirections.actionExerciseFragmentToFavoriteExercisesFragment()
                )
            }
        })
    }

    private fun initView() {
        binding.exerciseRecyclerview.adapter = exerciseAdapter
        exerciseViewModel.state.observe(viewLifecycleOwner, {
            exerciseAdapter.submitList(it.exerciseList)
        })
    }
}
