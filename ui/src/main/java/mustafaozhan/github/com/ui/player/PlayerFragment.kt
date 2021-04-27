package mustafaozhan.github.com.ui.player

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mustafaozhan.github.com.base.fragment.BaseDBFragment
import mustafaozhan.github.com.ui.R
import mustafaozhan.github.com.ui.databinding.FragmentPlayerBinding
import mustafaozhan.github.com.util.reObserve
import javax.inject.Inject

class PlayerFragment : BaseDBFragment<FragmentPlayerBinding>(), MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener {

    private val args: PlayerFragmentArgs by navArgs()

    @Inject
    lateinit var playerViewModel: PlayerViewModel

    override fun bind(container: ViewGroup?): FragmentPlayerBinding =
        FragmentPlayerBinding.inflate(layoutInflater, container, false)

    override fun onBinding(dataBinding: FragmentPlayerBinding) {
        binding.vm = playerViewModel
        binding.event = playerViewModel.getEvent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        playerViewModel.setData(args.exerciseList.toList())

        initView()
        observeEffects()
    }

    override fun onDestroyView() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onDestroyView()
    }

    private fun initView() {
        binding.videoView.setOnCompletionListener(this)
        binding.videoView.setOnPreparedListener(this)
    }

    private fun observeEffects() {
        playerViewModel.effect.reObserve(viewLifecycleOwner, { playerEffect ->
            when (playerEffect) {
                PlayerEffect.BackEffect -> requireActivity().onBackPressed()
                is PlayerEffect.PlayVideoEffect -> playVideo(playerEffect.url)
                PlayerEffect.PlaybackEnd -> requireActivity().onBackPressed()
                is PlayerEffect.OpenSummaryScreen -> navigate(
                    R.id.playerFragment,
                    PlayerFragmentDirections.actionPlayerFragmentToSummaryFragment(
                        playerEffect.skipCount, playerEffect.totalCount
                    )
                )
            }
        })
    }

    private fun playVideo(url: String) = with(binding.videoView) {
        setVideoPath(url)
        start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        playerViewModel.getEvent().videoEnd()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        playerViewModel.getEvent().videoStart()
    }
}
