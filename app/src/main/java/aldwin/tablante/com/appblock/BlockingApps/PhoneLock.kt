package aldwin.tablante.com.appblock.BlockingApps

import aldwin.tablante.com.appblock.Activity.MainActivity
import aldwin.tablante.com.appblock.Model.ConsoleCommand
import aldwin.tablante.com.appblock.R
import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_phone_lock.*
import android.content.pm.PackageManager
import android.os.Build
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class PhoneLock : AppCompatActivity() {
    val DATABASE_NAME = "Apps.db"
    var name = ""
    var device = Build.SERIAL
    var activityA: PhoneLock? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_lock)
        }
}
