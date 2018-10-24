package aldwin.tablante.com.appblock.Service

import aldwin.tablante.com.appblock.Activity.*
import aldwin.tablante.com.appblock.BlockingApps.AppDBHelper
import aldwin.tablante.com.appblock.BlockingApps.BlockApplications
import aldwin.tablante.com.appblock.Commands.*
import aldwin.tablante.com.appblock.BlockingApps.DataBaseHelper
import aldwin.tablante.com.appblock.Model.*
import android.app.ActivityManager
import android.app.Service
import android.content.*
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.*
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import android.os.PowerManager
import org.json.JSONArray
import org.json.JSONObject
import android.app.Activity
import android.content.Intent
import android.util.Log
import java.lang.Compiler.disable
import java.lang.Compiler.enable
import android.content.ComponentName
import android.app.admin.DevicePolicyManager
import java.util.*


class TrackerService : Service() {
    lateinit var db: DataBaseHelper
    val DATABASE_NAME = "Apps.db"
    val APPS_TABLE_NAME = "apps"
    lateinit var  activityManager: ActivityManager
    var deviceManger: DevicePolicyManager? = null
    var activityManagers: ActivityManager? = null
    var compName: ComponentName? = null
    val RESULT_ENABLE = 1
    lateinit var RAP : List<ActivityManager.RunningAppProcessInfo>
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag")
        wl.acquire()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var appDBHelper = AppDBHelper(applicationContext)
        var device = android.os.Build.SERIAL
        GetCurrentLocations().requestLocationUpdates(applicationContext, device)

        //GetParentDevices().fetchparent(device,applicationContext)
        var db = FirebaseFirestore.getInstance()
        var app: ArrayList<String> = ArrayList()
        var pairRequest: ArrayList<String> = ArrayList()
        var blockApp: ArrayList<String> = arrayListOf()
        var instalAppLabel: ArrayList<String> = arrayListOf()
        var instalApp: ArrayList<String> = ArrayList()
        var mmap: HashMap<String, Any?> = HashMap()

        var table: SQLiteDatabase = appDBHelper.writableDatabase
        val quers = "SELECT count(*) FROM $APPS_TABLE_NAME"
        val mcursor = table.rawQuery(quers, null)
        mcursor.moveToFirst()
        val icount = mcursor.getInt(0)
        if (icount == 0){
        }
        else {
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
                        blockApp.add(jsonArray.getString(i))
                    }
                }
            }
        }


        val pm = applicationContext.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        var runApp = arrayListOf<String>()
        var runPackage = arrayListOf<String>()
        val list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED)
        for (rInfo in list) {
            instalApp.add(rInfo.activityInfo.applicationInfo.packageName.toString())
            instalAppLabel.add(rInfo.activityInfo.applicationInfo.loadLabel(pm).toString())
        }

        activityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        RAP = activityManager.runningAppProcesses
        for(processInfo in RAP){
            app.add(processInfo.processName)
        }

        var mmaps: HashMap<String, Any?> = HashMap()
        var pams: HashMap<String, Any?> = HashMap()
        for (rInfo in list) {
            mmaps.put(rInfo.activityInfo.applicationInfo.packageName.toString(), rInfo.activityInfo.applicationInfo.loadLabel(pm).toString())
        }

        for (i in 0 until app.size){
            var item = mmaps.get(app[i])
            runApp.add(item.toString())
        }
        runApp.removeAll(Collections.singleton("null"))
        runApp.removeAll(Collections.singleton(""))



        db.collection("Devices").document(device).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot.exists()) {
                null
            } else {
                mmap.put("Serial", device)
                mmap.put("BootDevice", false)
                mmap.put("LockScreen", "")
                mmap.put("BlockApplications", blockApp)
                mmap.put("Screenshot", false)
                mmap.put("CaptureCam", false)
                mmap.put("InstalledAppLabel", instalAppLabel)
                mmap.put("InstalledApplications", instalApp)
                mmap.put("TriggerAlarm", false)
                mmap.put("Location",false)
                mmap.put("Messages", "")
                mmap.put("Applications", runApp)
                mmap.put("Request", pairRequest)
                mmap.put("AppPermit", false)
                //    mmap.put("KillApp", "")
                db.collection("Devices")
                        .document(device)
                        .set(mmap)

            }


        }

        db.collection("Devices")
                .whereEqualTo("Serial", device)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        for (doc in p0!!.documents) {
                            var devicet = doc.toObject(ConsoleCommand::class.java)

                            if (devicet.CaptureCam) {
                                db.collection("Devices").document(doc.id).update("CaptureCam", false)
                                //CaptureCam().openFrontCamera(doc.id,device,applicationContext)
                                var intent = Intent(applicationContext, RequestPicture::class.java)
                                intent.putExtra("id", "val")
                                intent.putExtra("serial", device)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                                startActivity(intent)
                            }

                            if (devicet.LockScreen == "one"){
                                appDBHelper.insertLockData("one")
                            }
                            if (devicet.LockScreen == "five"){
                                appDBHelper.insertLockData("five")
                            }
                            if (devicet.LockScreen == "ten"){
                                appDBHelper.insertLockData("ten")
                            }
                            if (devicet.LockScreen == "twenty"){
                                appDBHelper.insertLockData("twenty")
                            }
                            if (devicet.LockScreen == "thirty"){
                                appDBHelper.insertLockData("thirty")
                            }

                            if (devicet.Screenshot) {
                                db.collection("Devices").document(doc.id).update("Screenshot", false)
                                ScreenShot().doshot(doc.id, applicationContext)
                            }

                            if (devicet.TriggerAlarm) {
                                TriggerAlarm().playAlarm(applicationContext)
                                db.collection("Devices").document(doc.id).update("TriggerAlarm", false)
                            }
                            if (!devicet.Messages.equals("")) {
                                var intent = Intent(applicationContext, MessageReciever::class.java)
                                intent.putExtra("id", doc.id)
                                intent.putExtra("msg", devicet.Messages)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }

                            if (devicet.Location) {
                                db.collection("Devices").document(doc.id).update("Location", false)
                                var intent = Intent(applicationContext, RefreshLocation::class.java)
                                intent.putExtra("id", doc.id)

                                intent.putExtra("msg", devicet.Messages)
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                                startActivity(intent)

                            }


                        }
                    }
                })

        var ser = Build.SERIAL
        var database = FirebaseFirestore.getInstance()
        database.collection("Devices")
                .whereEqualTo("Serial", ser)
                .addSnapshotListener { p0, p1 ->
                    if (p0!!.isEmpty) {
                        Toast.makeText(applicationContext, "Device is Restarting the Connector", Toast.LENGTH_SHORT).show()
                    } else {
                        for (doc in p0!!.documents) {
                            var result: Boolean = false
                            var app: BlockApplications = doc.toObject(BlockApplications::class.java)
                            var list = app.BlockApplications
                            if (list.isNotEmpty()) {
                                var appDBHelper = AppDBHelper(applicationContext)
                                var table: SQLiteDatabase = appDBHelper.writableDatabase
                                val quers = "SELECT count(*) FROM $APPS_TABLE_NAME"
                                val mcursor = table.rawQuery(quers, null)
                                mcursor.moveToFirst()
                                val icount = mcursor.getInt(0)
                                if (icount == 0){
                                    val json = JSONObject()
                                    json.put("Apps", JSONArray(list))
                                    val arrayList = json.toString()
                                    appDBHelper.insertAppsData(arrayList)
                                    break
                                }
                                else{
                                    val json = JSONObject()
                                    json.put("Apps", JSONArray(list))
                                    appDBHelper.updateAppsData(1, json)
                                }
                            }
                        }
                    }
                }

        var rmap: HashMap<String, Any?> = HashMap()
        rmap.put("ID", "")
        rmap.put("Name", "")

        // Request Pairing
        db.collection("Requests")
                .whereEqualTo("ID", device)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {

                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        for (doc in p0!!.documents) {
                            var dev = doc.toObject(Requests::class.java)
                            var intent = Intent(this@TrackerService, RequestReciever::class.java)
                            intent.putExtra("serial", device)
                            intent.putExtra("name", dev.Name)
                            intent.putExtra("requestid", dev.RequestID)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(intent)

                        }
                    }
                })






        saveData()
        saveMinutes()
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        var intent = Intent("com.android.ServiceStopped")
        sendBroadcast(intent)
        var intent3 = Intent(this@TrackerService, MainActivity::class.java)

        startActivity(intent3)

        super.onTaskRemoved(rootIntent)
    }


    override fun onDestroy() {
        super.onDestroy()
        var intent2 = Intent(this@TrackerService, MainActivity::class.java)
                .setAction("enable_capture")
        try {
            try {
                startActivity(intent2)
            } catch (e: RuntimeException) {

                e.printStackTrace()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()


        }
    }

    fun saveData(){


    }
    fun saveMinutes(){
        var messaging = arrayListOf<String>("Messaging")
        var appDBHelper = AppDBHelper(applicationContext)
        var ser = Build.SERIAL
        var database = FirebaseFirestore.getInstance()
        database.collection("Devices")
                .whereEqualTo("Serial", ser)
                .addSnapshotListener { p0, p1 ->
                    if (p0!!.isEmpty) {
                        Toast.makeText(applicationContext, "Device is Restarting the Connector", Toast.LENGTH_SHORT).show()
                    } else {
                        for (doc in p0!!.documents) {
                            var one: OneMinute = doc.toObject(OneMinute::class.java)
                            var oneList = one.OneMinute
                            var five: FiveMinutes = doc.toObject(FiveMinutes::class.java)
                            var fiveList = five.FiveMinutes
                            var ten: TenMinutes = doc.toObject(TenMinutes::class.java)
                            var tenList = ten.TenMinutes
                            var twenty: TwentyMinutes = doc.toObject(TwentyMinutes::class.java)
                            var twentyList = twenty.TwentyMinutes
                            var thirtyMinutes: ThirtyMinutes = doc.toObject(ThirtyMinutes::class.java)
                            var thirtyList = thirtyMinutes.ThirtyMinutes

                            if (oneList.isNotEmpty()) {
                                val json = JSONObject()
                                json.put("OneMinute", JSONArray(oneList))
                                val arrayList = json.toString()
                                appDBHelper.insertOneMin(arrayList)
                                break
                            }
                            else if (fiveList.isNotEmpty()){
                                val json = JSONObject()
                                json.put("FiveMinutes", JSONArray(fiveList))
                                val arrayList = json.toString()
                                appDBHelper.insertFiveMin(arrayList)
                                break
                            }
                            else if (tenList.isNotEmpty()){
                                val json = JSONObject()
                                json.put("TenMinute", JSONArray(tenList))
                                val arrayList = json.toString()
                                appDBHelper.insertTenMin(arrayList)
                                break
                            }
                            else if (twentyList.isNotEmpty()){
                                val json = JSONObject()
                                json.put("TwentyMinutes", JSONArray(twentyList))
                                val arrayList = json.toString()
                                appDBHelper.insertTwentyMin(arrayList)
                                break
                            }
                            else if (thirtyList.isNotEmpty()){
                                val json = JSONObject()
                                json.put("ThirtyMinutes", JSONArray(thirtyList))
                                val arrayList = json.toString()
                                appDBHelper.insertThirtyMin(arrayList)
                                break
                            }

                        }
                    }
                }

    }


}


