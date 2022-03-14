package com.cherish.randomfactproject.ui.home

import android.os.Build
import android.service.autofill.TextValueSanitizer
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import com.cherish.randomfactproject.R
import com.cherish.randomfactproject.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.jar.Manifest

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class HomeFragmentTest{

   @Test
   fun testHomeFragment(){
       val fragment = launchFragmentInHiltContainer<StoreDetails>{
           assert(this.view?.findViewById<TextView>(R.id.name)?.text.toString() == "Name")
       }

   }
}