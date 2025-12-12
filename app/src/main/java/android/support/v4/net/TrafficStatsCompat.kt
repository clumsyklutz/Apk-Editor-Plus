package android.support.v4.net

import android.net.TrafficStats
import android.os.Build
import android.os.ParcelFileDescriptor
import android.support.annotation.NonNull
import java.net.DatagramSocket
import java.net.Socket
import java.net.SocketException

class TrafficStatsCompat {
    private constructor() {
    }

    @Deprecated
    fun clearThreadStatsTag() {
        TrafficStats.clearThreadStatsTag()
    }

    @Deprecated
    fun getThreadStatsTag() {
        return TrafficStats.getThreadStatsTag()
    }

    @Deprecated
    fun incrementOperationCount(Int i) {
        TrafficStats.incrementOperationCount(i)
    }

    @Deprecated
    fun incrementOperationCount(Int i, Int i2) {
        TrafficStats.incrementOperationCount(i, i2)
    }

    @Deprecated
    fun setThreadStatsTag(Int i) {
        TrafficStats.setThreadStatsTag(i)
    }

    fun tagDatagramSocket(@NonNull DatagramSocket datagramSocket) throws SocketException {
        if (Build.VERSION.SDK_INT >= 24) {
            TrafficStats.tagDatagramSocket(datagramSocket)
            return
        }
        ParcelFileDescriptor parcelFileDescriptorFromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket)
        TrafficStats.tagSocket(DatagramSocketWrapper(datagramSocket, parcelFileDescriptorFromDatagramSocket.getFileDescriptor()))
        parcelFileDescriptorFromDatagramSocket.detachFd()
    }

    @Deprecated
    fun tagSocket(Socket socket) throws SocketException {
        TrafficStats.tagSocket(socket)
    }

    fun untagDatagramSocket(@NonNull DatagramSocket datagramSocket) throws SocketException {
        if (Build.VERSION.SDK_INT >= 24) {
            TrafficStats.untagDatagramSocket(datagramSocket)
            return
        }
        ParcelFileDescriptor parcelFileDescriptorFromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket)
        TrafficStats.untagSocket(DatagramSocketWrapper(datagramSocket, parcelFileDescriptorFromDatagramSocket.getFileDescriptor()))
        parcelFileDescriptorFromDatagramSocket.detachFd()
    }

    @Deprecated
    fun untagSocket(Socket socket) throws SocketException {
        TrafficStats.untagSocket(socket)
    }
}
