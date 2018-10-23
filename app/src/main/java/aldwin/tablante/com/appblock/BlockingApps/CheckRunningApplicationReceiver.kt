package aldwin.tablante.com.appblock.BlockingApps

import aldwin.tablante.com.appblock.Activity.MainActivity
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

import android.R.attr.key
import android.app.usage.UsageStats

import android.preference.PreferenceManager
import android.content.SharedPreferences

import java.lang.reflect.Type
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.os.CountDownTimer
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_phone_lock.*
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ResolveInfo
import android.content.Context.ACTIVITY_SERVICE






class CheckRunningApplicationReceiver : BroadcastReceiver() {

    val TAG = "CRAR" // CheckRunningApplicationReceiver
    var id =""
    val ONE_MINUTE_TABLE_NAME = "oneMin"
    val FIVE_MINUTE_TABLE_NAME = "fiveMin"
    val TEN_MINUTE_TABLE_NAME = "tenMin"
    val TWENTY_MINUTE_TABLE_NAME = "twentyMin"
    val THIRTY_MINUTE_TABLE_NAME = "thirtyMin"
    var serial = Build.SERIAL
    var context: CheckRunningApplicationReceiver = this
    var BlockList: ArrayList<String> = arrayListOf()

    override fun onReceive(aContext: Context, anIntent: Intent) {
        var appDBHelper = AppDBHelper(aContext)
        var firestore = FirebaseFirestore.getInstance()
        var  noterefs = firestore.collection("Devices").document(serial)
        val am = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val alltasks = am.runningAppProcesses
        var current = alltasks[0].processName

        val pm = aContext.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED)
        var mmap: HashMap<String, Any?> = HashMap()
        for (rInfo in list) {
            mmap.put(rInfo.activityInfo.applicationInfo.packageName.toString(), rInfo.activityInfo.applicationInfo.loadLabel(pm).toString())
        }



        try {
            var res = appDBHelper.getLock()
            var stringBuilder = StringBuilder()
            if (res!= null && res.count > 0 ) {
                while (res.moveToNext()) {
                    stringBuilder.append(res.getString(1))
                }
                var item = stringBuilder.toString()
                if (item == "one") {
                    var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                        override fun onFinish() {
                            appDBHelper.deleteLock("one")
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockScreen", "")
                            noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }

                        override fun onTick(p0: Long) {
                            if (current.contains("launcher")) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(intent)
                            }
                            else if (current.contains("launcher") && current!= "aldwin.tablante.com.appblock"){
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }
                    }.start()
                } else if (item == "five") {
                    var countDownTimer = object : CountDownTimer(60 * 5000, 1000) {
                        override fun onFinish() {
                            appDBHelper.deleteLock("five")
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockScreen", "")
                            noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }

                        override fun onTick(p0: Long) {
                            if (current.contains("launcher")) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                            else if (current.contains("launcher") && current!= "aldwin.tablante.com.appblock"){
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }
                    }.start()
                } else if (item == "ten") {
                    var countDownTimer = object : CountDownTimer(60 * 10000, 1000) {
                        override fun onFinish() {
                            appDBHelper.deleteLock("ten")
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockScreen", "")
                            noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }

                        override fun onTick(p0: Long) {
                            if (current.contains("launcher")) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                                Toast.makeText(aContext, "" + p0 / 1000, Toast.LENGTH_SHORT).show()
                            }else if (current.contains("launcher") && current!= "aldwin.tablante.com.appblock"){
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }
                    }.start()
                } else if (item == "twenty") {
                    var countDownTimer = object : CountDownTimer(60 * 20000, 1000) {
                        override fun onFinish() {
                            appDBHelper.deleteLock("twenty")
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockScreen", "")
                            noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }

                        override fun onTick(p0: Long) {
                            if (current.contains("launcher")) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                                Toast.makeText(aContext, "" + p0 / 1000, Toast.LENGTH_SHORT).show()
                            }else if (current.contains("launcher") && current!= "aldwin.tablante.com.appblock"){
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }
                    }.start()
                } else if (item == "thirty") {
                    var countDownTimer = object : CountDownTimer(60 * 30000, 1000) {
                        override fun onFinish() {
                            appDBHelper.deleteLock("thirty")
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockScreen", "")
                            noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }

                        override fun onTick(p0: Long) {
                            if (current.contains("launcher")) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                                Toast.makeText(aContext, "" + p0 / 1000, Toast.LENGTH_SHORT).show()
                            }else if (current.contains("launcher") && current!= "aldwin.tablante.com.appblock"){
                                val startMain = Intent(Intent.ACTION_MAIN)
                                startMain.addCategory(Intent.CATEGORY_HOME)
                                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                aContext.startActivity(startMain)
                            }
                        }
                    }.start()
                }
        }
        var ressurect = appDBHelper.getAllData()
        var bobBuilder = StringBuilder()
        if (ressurect != null && ressurect.count > 0) {
            while (ressurect.moveToNext()) {
                bobBuilder.append(ressurect.getString(1))
            }

            val jsonObj = JSONObject(bobBuilder.toString())
            var jsonArray = jsonObj.getJSONArray("Apps")

            if (jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    BlockList.add(jsonArray.getString(i))
                }
            }
            var label = mmap.get(current)
            for (i in 0 until BlockList.size) {
                var app = BlockList[i]
                if (app == label) {
                    var intent = Intent(aContext, PhoneLock::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("key", "on")
                    aContext.startActivity(intent)
                }
            }
        }

            var ressu = appDBHelper.getTime(ONE_MINUTE_TABLE_NAME)
            var bob = StringBuilder()
            if (ressu!= null && ressu.count > 0 ) {
                while (ressu.moveToNext()) {
                    bob.append(ressu.getString(1))
                }
                val jsonObj = JSONObject(bob.toString())
                var jsonOne = jsonObj.getJSONArray("OneMinute")
                var oneList: ArrayList<String> = arrayListOf()
                if (jsonOne != null) {
                    for (i in 0 until jsonOne.length()) {
                        oneList.add(jsonOne.getString(i))
                    }
                }
                if (oneList.isNotEmpty()){
                    var app = ""
                    val a = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val tasks = a.runningAppProcesses
                    var cur = tasks[0].processName
                    for (e in 0 until oneList.size){
                        app = oneList[e].replace(" ", "").toLowerCase()
                    }
                    if (cur.contains(app)){
                        var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                            override fun onFinish() {
                                appDBHelper.deleteTime(ONE_MINUTE_TABLE_NAME)
                                oneList.clear()
                                var mmap: HashMap<String, Any?> = HashMap()
                                mmap.put("OneMinute", FieldValue.delete())
                                noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                    val startMain = Intent(Intent.ACTION_MAIN)
                                    startMain.addCategory(Intent.CATEGORY_HOME)
                                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    aContext.startActivity(startMain)
                                }
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                        }.start()
                    }
                }
            }

            var ress1 = appDBHelper.getTime(FIVE_MINUTE_TABLE_NAME)
            var bob1 = StringBuilder()
            if (ress1!= null && ress1.count > 0 ) {
                while (ress1.moveToNext()) {
                    bob1.append(ress1.getString(1))
                }
                val json1 = JSONObject(bob1.toString())
                var jsonFive = json1.getJSONArray("FiveMinutes")
                var fiveList: ArrayList<String> = arrayListOf()
                if (jsonFive != null) {
                    for (i in 0 until jsonFive.length()) {
                        fiveList.add(jsonFive.getString(i))
                    }
                }
                if(fiveList.isNotEmpty()) {
                    var app = ""
                    val a = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val tasks = a.runningAppProcesses
                    var cur = tasks[0].processName
                    for (e in 0 until fiveList.size){
                        app = fiveList[e].replace(" ", "").toLowerCase()
                    }
                    if (cur.contains(app)){
                        var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                            override fun onFinish() {
                                appDBHelper.deleteTime(FIVE_MINUTE_TABLE_NAME)
                                fiveList.clear()
                                var mmap: HashMap<String, Any?> = HashMap()
                                mmap.put("OneMinute", FieldValue.delete())
                                noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                    val startMain = Intent(Intent.ACTION_MAIN)
                                    startMain.addCategory(Intent.CATEGORY_HOME)
                                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    aContext.startActivity(startMain)
                                }
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                        }.start()
                    }
                }
            }

            var ress2 = appDBHelper.getTime(TEN_MINUTE_TABLE_NAME)
            var bob2 = StringBuilder()
            if (ress2!= null && ress2.count > 0 ) {
                while (ress2.moveToNext()) {
                    bob2.append(ress2.getString(1))
                }
                val json2 = JSONObject(bob2.toString())
                var jsonTen = json2.getJSONArray("TenMinutes")
                var tenList: ArrayList<String> = arrayListOf()
                if (jsonTen != null) {
                    for (i in 0 until jsonTen.length()) {
                        tenList.add(jsonTen.getString(i))
                    }
                }
                if (tenList.isNotEmpty()) {
                    var app = ""
                    val a = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val tasks = a.runningAppProcesses
                    var cur = tasks[0].processName
                    for (e in 0 until tenList.size){
                        app = tenList[e].replace(" ", "").toLowerCase()
                    }
                    if (cur.contains(app)){
                        var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                            override fun onFinish() {
                                appDBHelper.deleteTime(TEN_MINUTE_TABLE_NAME)
                                tenList.clear()
                                var mmap: HashMap<String, Any?> = HashMap()
                                mmap.put("OneMinute", FieldValue.delete())
                                noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                    val startMain = Intent(Intent.ACTION_MAIN)
                                    startMain.addCategory(Intent.CATEGORY_HOME)
                                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    aContext.startActivity(startMain)
                                }
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                        }.start()
                    }
                }
            }
            var ress3 = appDBHelper.getTime(TWENTY_MINUTE_TABLE_NAME)
            var bob3 = StringBuilder()
            if (ress3!= null && ress3.count > 0 ) {
                while (ress3.moveToNext()) {
                    bob3.append(ress3.getString(1))
                }
                val json3 = JSONObject(bob3.toString())
                var jsonTwenty = json3.getJSONArray("TwentyMinutes")
                var twentyList: ArrayList<String> = arrayListOf()
                if (jsonTwenty != null) {
                    for (i in 0 until jsonTwenty.length()) {
                        twentyList.add(jsonTwenty.getString(i))
                    }
                }
                if(twentyList.isNotEmpty()){
                    var app = ""
                    val a = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val tasks = a.runningAppProcesses
                    var cur = tasks[0].processName
                    for (e in 0 until twentyList.size){
                        app = twentyList[e].replace(" ", "").toLowerCase()
                    }
                    if (cur.contains(app)){
                        var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                            override fun onFinish() {
                                appDBHelper.deleteTime(TWENTY_MINUTE_TABLE_NAME)
                                twentyList.clear()
                                var mmap: HashMap<String, Any?> = HashMap()
                                mmap.put("OneMinute", FieldValue.delete())
                                noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                    val startMain = Intent(Intent.ACTION_MAIN)
                                    startMain.addCategory(Intent.CATEGORY_HOME)
                                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    aContext.startActivity(startMain)
                                }
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                        }.start()
                    }
                }
            }
            var ress4 = appDBHelper.getTime(THIRTY_MINUTE_TABLE_NAME)
            var bob4 = StringBuilder()
            if (ress4!= null && ress4.count > 0 ) {
                while (ress4.moveToNext()) {
                    bob4.append(ress4.getString(1))
                }
                val json4 = JSONObject(bob4.toString())
                var jsonThirty = json4.getJSONArray("ThirtyMinute")
                var thirtyList: ArrayList<String> = arrayListOf()
                if (jsonThirty != null) {
                    for (i in 0 until jsonThirty.length()) {
                        thirtyList.add(jsonThirty.getString(i))
                    }
                }
                if (thirtyList.isNotEmpty()){
                    var app = ""
                    val a = aContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val tasks = a.runningAppProcesses
                    var cur = tasks[0].processName
                    for (e in 0 until thirtyList.size){
                        app = thirtyList[e].replace(" ", "").toLowerCase()
                    }
                    if (cur.contains(app)){
                        var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
                            override fun onFinish() {
                                appDBHelper.deleteTime(THIRTY_MINUTE_TABLE_NAME)
                                thirtyList.clear()
                                var mmap: HashMap<String, Any?> = HashMap()
                                mmap.put("OneMinute", FieldValue.delete())
                                noterefs.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                    val startMain = Intent(Intent.ACTION_MAIN)
                                    startMain.addCategory(Intent.CATEGORY_HOME)
                                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    aContext.startActivity(startMain)
                                }
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                var intent = Intent(aContext, PhoneLock::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("key", "on")
                                aContext.startActivity(intent)
                            }
                        }.start()
                    }
                }
            }
        val packageName = "com.example.checkcurrentrunningapplication"

        if (current == "$packageName.Main") {
            Toast.makeText(aContext, "Current Example Screen.", Toast.LENGTH_LONG).show()
        }

        Log.i(TAG, "===============================")
        Log.i(TAG, "aTask.baseActivity: " + current)
        Log.i(TAG, "===============================")

        } catch (t: Throwable) {
            Log.i(TAG, "Throwable caught: " + t.message, t)
        }

    }

    private fun getAppNameFromPackage(packageName:String, context:Context): String? {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList = context.getPackageManager()
                .queryIntentActivities(mainIntent, 0)
        for (app in pkgAppsList)
        {
            if (app.activityInfo.packageName.equals(packageName))
            {
                return app.activityInfo.loadLabel(context.getPackageManager()).toString()
            }
        }
        return null
    }
}
