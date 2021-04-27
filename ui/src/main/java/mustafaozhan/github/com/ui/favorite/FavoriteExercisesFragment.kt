package mustafaozhan.github.com.ui.favorite

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import mustafaozhan.github.com.base.fragment.BaseDBFragment
import mustafaozhan.github.com.ui.R
import mustafaozhan.github.com.ui.databinding.FragmentFavoriteExercisesBinding
import mustafaozhan.github.com.util.reObserve
import javax.inject.Inject

class FavoriteExercisesFragment : BaseDBFragment<FragmentFavoriteExercisesBinding>() {

    @Inject
    lateinit var favoriteExercisesViewModel: FavoriteExercisesViewModel

    private lateinit var favoriteExercisesAdapter: FavoriteExercisesAdapter

    override fun bind(container: ViewGroup?): FragmentFavoriteExercisesBinding =
        FragmentFavoriteExercisesBinding.inflate(layoutInflater, container, false)

    override fun onBinding(dataBinding: FragmentFavoriteExercisesBinding) {
        binding.vm = favoriteExercisesViewModel
        favoriteExercisesViewModel.getEvent().let {
            binding.event = it
            favoriteExercisesAdapter = FavoriteExercisesAdapter(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeEffects()
    }

    private fun observeEffects() {
        favoriteExercisesViewModel.effect.reObserve(viewLifecycleOwner, { favoriteListEffect ->
            when (favoriteListEffect) {
                is FavoriteExercisesEffect.PlayExercise -> navigate(
                    R.id.favoriteExercisesFragment,
                    FavoriteExercisesFragmentDirections.actionFavoriteExercisesFragmentToPlayerFragment(
                        favoriteListEffect.exercise.toTypedArray()
                    )
                )
                is FavoriteExercisesEffect.BackEffect -> requireActivity().onBackPressed()
            }
        })
    }

    private fun initView() {
        binding.favoritesRecyclerview.adapter = favoriteExercisesAdapter
        with(favoriteExercisesViewModel) {
            state.observe(viewLifecycleOwner, {
                favoriteExercisesAdapter.submitList(it.exerciseList)
            })
        }
    }
}
