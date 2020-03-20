package dev.mem.rocket.sanya.inapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.mem.rocket.sanya.ABConfig
import dev.mem.rocket.sanya.MainActivity
import dev.mem.rocket.sanya.R
import dev.mem.rocket.sanya.SubscriptionProvider
import kotlinx.android.synthetic.main.diamond_act.view.btnPay
import kotlinx.android.synthetic.main.diamond_act.view.tvNext

class DiamondFrag : Fragment() {

  companion object{
    private const val TAG_FROM = "TAG_FROM"

    fun newInstance(from : String) : DiamondFrag{
      var bundle = Bundle()
      bundle.putString(TAG_FROM, from)
      var fragment = DiamondFrag()
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    var view = inflater.inflate(R.layout.diamond_act, container, false)
    return  view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val from = arguments?.getString(TAG_FROM)
    val abVersion = activity?.getSharedPreferences(
        ABConfig.KEY_FOR_SAVE_STATE,
        Context.MODE_PRIVATE
    )?.getString(ABConfig.KEY_FOR_SAVE_STATE, "")
    view.btnPay.setOnClickListener { _ ->
      activity?.let { it1 -> SubscriptionProvider.startChoiseSub(it1, abVersion!!, object : InAppCallback{
        override fun trialSucces() {
          handlInApp()
        }
      }) }
    }

    view.tvNext.setOnClickListener { _ ->
      startActivity(Intent(activity, MainActivity::class.java))
      activity?.finish()
    }
    if (activity is MainActivity){
      view.tvNext.visibility = View.INVISIBLE
    }
  }

  private fun handlInApp() {
    SubscriptionProvider.setSuccesSubscription()
    activity?.let {
      startActivity(Intent(it, MainActivity::class.java))
    }
    activity?.finish()
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser && activity is MainActivity) {
      MainActivity.setInfo(R.string.remove_ads)
    }
  }
}