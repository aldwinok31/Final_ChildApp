package aldwin.tablante.com.appblock.Model

class ConsoleCommand(BootDevice:Boolean,Screenshot:Boolean,CaptureCam:Boolean,TriggerAlarm:Boolean ,
                     Messages:String,Applications:ArrayList<String>,
                     AppPermit:Boolean,KillApp:String,Location:Boolean,
                     InstalledAppLabel: ArrayList<String>,
                     InstalledApplications: ArrayList<String>,
                     LockScreen: String) {

    var BootDevice = BootDevice
    var Screenshot = Screenshot
    var CaptureCam =CaptureCam
    var TriggerAlarm = TriggerAlarm
    var Messages = Messages
    var Applications = Applications
    var AppPermit = AppPermit
    var KillApp= KillApp
    var InstalledAppLabel = InstalledAppLabel
    var InstalledApplications = InstalledApplications
    var Location = Location
    var LockScreen = LockScreen

    constructor() : this(false,false,false,false,
            "",ArrayList(),false,"",false, ArrayList(), ArrayList(),"")
}