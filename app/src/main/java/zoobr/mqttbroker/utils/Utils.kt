package zoobr.mqttbroker.utils


import android.util.Log
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

object Utils {

    const val TAG = "Utils"
    const val NETWORK_BROADCAST_ACTION = "network_change"

    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val networkInterfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in networkInterfaces) {
                val inetAddressList: List<InetAddress> =
                    Collections.list(networkInterface.inetAddresses)
                Log.d(TAG, "getIPAddress: $inetAddressList")
                for (inetAddress in inetAddressList) {
                    if (!inetAddress.isLoopbackAddress) {
                        val sAddr = inetAddress.hostAddress
                        if (sAddr != null) {
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                Log.d(TAG, "addr: $sAddr")
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    return if (delim < 0) sAddr.uppercase() else sAddr.substring(
                                        0,
                                        delim
                                    ).uppercase()
                                }
                            }
                        }
                    }
                }
            }
        } catch (ignored: Exception) {
        }
        return "21.37.14.88"
    }

}
