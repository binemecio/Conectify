package app.cloudnicaragua.bluewifi.nicaragua.GlobalVar;

/**
 * Created by binemecio on 4/5/2019.
 */

public class GlobalVar {

    public static String serverUrl = "";

    // Production
    // get information of ssid
    public static String ssidUrl = "http://bluewifi.cloudnicaragua.com/controllers/obteniendoSucursal.php";
    // send the the record object
    public static String recordUrl = "http://bluewifi.cloudnicaragua.com/controllers/registrosCliente.php";
    // get the time when each ad will run
    public static String adLoopUrl = "http://bluewifi.cloudnicaragua.com/controllers/obteniendoCicloLanzamiento.php";


    // Test
//    // get information of ssid
//    public static String ssidUrl = "http://erasmojordan.com/proyectos/newapp/controllers/obteniendoSucursal.php";
//    // send the the record object
//    public static String recordUrl = "http://erasmojordan.com/proyectos/newapp/controllers/registrosCliente.php";
//    // get the time when each ad will run
//    public static String adLoopUrl = "http://erasmojordan.com/proyectos/newapp/controllers/obteniendoCicloLanzamiento.php";

}
