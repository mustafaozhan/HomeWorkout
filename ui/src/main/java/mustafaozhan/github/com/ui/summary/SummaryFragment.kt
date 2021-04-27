package mustafaozhan.github.com.ui.summary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import mustafaozhan.github.com.base.fragment.BaseVBFragment
import mustafaozhan.github.com.ui.R
import mustafaozhan.github.com.ui.databinding.FragmentSummaryBinding

class SummaryFragment : BaseVBFragment<FragmentSummaryBinding>() {

    private val args: SummaryFragmentArgs by navArgs()

    override fun bind() {
        binding = FragmentSummaryBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() = with(binding) {
        summaryText.text = getString(R.string.summary_text, args.skipped, args.total)
        btnFinishTraining.setOnClickListener {
            navigate(
                R.id.summaryFragment,
                SummaryFragmentDirections.actionSummaryFragmentToExerciseFragment()
            )
        }
    }
}
