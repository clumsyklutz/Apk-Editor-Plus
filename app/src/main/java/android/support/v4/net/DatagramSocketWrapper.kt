package android.support.v4.net

import java.io.FileDescriptor
import java.io.InputStream
import java.io.OutputStream
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketImpl

class DatagramSocketWrapper extends Socket {

    class DatagramSocketImplWrapper extends SocketImpl {
        DatagramSocketImplWrapper(DatagramSocket datagramSocket, FileDescriptor fileDescriptor) {
            this.localport = datagramSocket.getLocalPort()
            this.fd = fileDescriptor
        }

        @Override // java.net.SocketImpl
        protected fun accept(SocketImpl socketImpl) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun available() {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun bind(InetAddress inetAddress, Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun close() {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun connect(String str, Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun connect(InetAddress inetAddress, Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun connect(SocketAddress socketAddress, Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun create(Boolean z) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun getInputStream() {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketOptions
        fun getOption(Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun getOutputStream() {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun listen(Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketImpl
        protected fun sendUrgentData(Int i) {
            throw UnsupportedOperationException()
        }

        @Override // java.net.SocketOptions
        fun setOption(Int i, Object obj) {
            throw UnsupportedOperationException()
        }
    }

    DatagramSocketWrapper(DatagramSocket datagramSocket, FileDescriptor fileDescriptor) {
        super(DatagramSocketImplWrapper(datagramSocket, fileDescriptor))
    }
}
