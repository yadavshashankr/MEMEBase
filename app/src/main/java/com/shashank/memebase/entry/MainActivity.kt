package com.shashank.memebase.entry


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.likethesalad.android.aaper.api.EnsurePermissions
import com.shashank.memebase.BuildConfig
import com.shashank.memebase.R
import com.shashank.memebase.databinding.ActivityEntryBinding
import com.shashank.memebase.entry.data.UserPreferences
import com.shashank.memebase.entry.fragments.LoginFragment
import com.shashank.memebase.entry.viewModels.EntryViewModel
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.fragments.AgendaFragment
import com.shashank.memebase.meme.ui.SelectVideoActivity
import com.shashank.memebase.usecases.FragmentInflaterImpl
import com.shashank.memebase.usecases.NetworkStatus
import com.shashank.memebase.usecases.ToolbarHandlerImpl
import com.shashank.memebase.usecases.domain.FragmentInflater
import com.shashank.memebase.usecases.domain.ToolbarHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("SameParameterValue")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentInflater by FragmentInflaterImpl(), ToolbarHandler by ToolbarHandlerImpl(),  OnClickListener {
    private lateinit var viewBinding: ActivityEntryBinding
    private val viewModel: EntryViewModel by viewModels()
    private lateinit var popUpActionWindow: PopupWindow
    @Inject
    lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)
        viewBinding.lifecycleOwner = this

        setObservers()

    }

    private fun setObservers() {

        if(userPreferences.isUserLoggedIn()){
            startFragment(AgendaFragment.getInstance())
        }else{
            startFragment(LoginFragment.getInstance())
            viewModel.makeMemeApiCall().observe(this){
                if (it?.data != null){
                    Log.v("SUCCESS", "SUCCESS")
                }
            }
        }

        viewModel.networkStatusObserver.observe(this){
                when(it){
                NetworkStatus.Available -> {
                    viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_online)
                    viewBinding.layoutOnlineMode.tvOnlineMode.text = getString(R.string.text_online)
                    animate(true)
                }
                NetworkStatus.Unavailable -> {
                    viewBinding.layoutOnlineMode.drawableFirst = ContextCompat.getDrawable(this, R.drawable.ic_offline)
                    viewBinding.layoutOnlineMode.tvOnlineMode.text = getString(R.string.text_offline)
                    animate(false)
                }
            }
        }

        viewModel.agendaDialogObserver().observe(this) {
            when (it) {
                getString(R.string.video_compressor) -> startVideoCompressor()
                getString(R.string.about) -> Toast.makeText(this, String.format(getString(R.string.about_msg), BuildConfig.VERSION_NAME), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @EnsurePermissions(permissions = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    private fun startVideoCompressor(){
        startActivity(Intent(this, SelectVideoActivity::class.java))
    }

    fun setFabLocation(shiftRight : Boolean){
        if(shiftRight){
            viewBinding.fab.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.startToStart = ConstraintLayout.LayoutParams.UNSET
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }else{
            viewBinding.fab.updateLayoutParams<ConstraintLayout.LayoutParams> {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.UNSET
            }
        }
    }

    fun showFAB(iconRes : Int, tag : String){
        viewBinding.fab.setImageResource(iconRes)
        viewBinding.fab.animate().translationY(0f)
        viewBinding.fab.tag = tag
        viewBinding.fab.setOnClickListener(this)
    }

    fun hideFAB(){
        viewBinding.fab.animate().translationY(resources.getDimension(com.intuit.sdp.R.dimen._200sdp))
        viewBinding.fab.setOnClickListener(null)
    }

    private fun animate(isAvailable: Boolean) {
        val textField = viewBinding.layoutOnlineMode.tvOnlineMode
        textField.isVisible = true
        val endToStartAnimation = TranslateAnimation(500f, 0f, 0f, 0f)
        endToStartAnimation.duration = Constants.AnimationProperties.DURATION
        textField.startAnimation(endToStartAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            viewBinding.layoutOnlineMode.tvOnlineMode.text = ""
            if (isAvailable){
                textField.isVisible = false
            }
        }, 3000)
    }

    fun startFragment(fragment : Fragment) {
        setFragmentManager(supportFragmentManager)
        setTitle(getString(R.string.welcome_back))
        inflateFragment(fragment, R.id.fragment_container)
    }

    fun setTitle(text: String) {
        setToolBarText(viewBinding.toolbar, text)
    }

    fun setToolbarHeight(isBig: Boolean) {
        setToolBarHeight(viewBinding.toolbar, viewBinding.appBar, this, isBig)
    }

    private fun showAgendaPopUp(agenda : Constants.AgendaDialog){
        val calculatedHeight: Int
        val halfScreenWidth: Int
        val calculatedWidth: Int

        if(agenda == Constants.AgendaDialog.AGENDA_PRE_TIMERS || agenda == Constants.AgendaDialog.AGENDA_MEMES_MODULES){
            calculatedWidth = viewBinding.fab.x.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._150sdp).toInt()
            calculatedHeight = viewBinding.fab.y.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._90sdp).toInt()
            halfScreenWidth = resources.displayMetrics.widthPixels / 2 + 60
        }else{
            calculatedWidth = viewBinding.fab.x.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._135sdp).toInt()
            calculatedHeight = viewBinding.fab.y.toInt() - resources.getDimension(com.intuit.sdp.R.dimen._135sdp).toInt()
            halfScreenWidth = resources.displayMetrics.widthPixels / 2 - 30
        }

        popUpActionWindow = viewModel.showAgendaDialog(agenda)
        popUpActionWindow.showAsDropDown(viewBinding.fab, 0, 0, Gravity.END)
        popUpActionWindow.update(calculatedWidth, calculatedHeight, halfScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(view: View?) {
        if(view == viewBinding.fab && viewBinding.fab.tag == "fab"){
            startFragment(LoginFragment.getInstance())
        }
        if(view == viewBinding.fab && viewBinding.fab.tag == "agendaDialog"){
            showAgendaPopUp(Constants.AgendaDialog.AGENDA_MEMES_MODULES)
        }
    }
}