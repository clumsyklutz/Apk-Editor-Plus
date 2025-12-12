package a.a.b.b

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.view.ViewCompat
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInput
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class g implements k {
    private fun a(Bitmap bitmap, Int i, Int i2, Int i3) {
        while (i2 <= i3) {
            bitmap.setPixel(i2, i, ViewCompat.MEASURED_STATE_MASK)
            i2++
        }
    }

    private fun a(DataInput dataInput) throws a.a.ExceptionA.a, IOException {
        dataInput.skipBytes(8)
        while (true) {
            try {
                Int i = dataInput.readInt()
                if (dataInput.readInt() == 1852855395) {
                    return
                } else {
                    dataInput.skipBytes(i + 4)
                }
            } catch (IOException e) {
                throw new a.a.a.a("Cant find nine patch chunk", e)
            }
        }
    }

    private fun b(Bitmap bitmap, Int i, Int i2, Int i3) {
        while (i2 <= i3) {
            bitmap.setPixel(i, i2, ViewCompat.MEASURED_STATE_MASK)
            i2++
        }
    }

    @Override // a.a.b.b.k
    public final Unit a(InputStream inputStream, OutputStream outputStream) throws a.a.ExceptionA {
        h hVar
        try {
            ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
            com.gmail.heagoo.a.c.a.b(inputStream, byteArrayOutputStream)
            Array<Byte> byteArray = byteArrayOutputStream.toByteArray()
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length)
            Int width = bitmapDecodeByteArray.getWidth()
            Int height = bitmapDecodeByteArray.getHeight()
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width + 2, height + 2, Bitmap.Config.ARGB_8888)
            Canvas(bitmapCreateBitmap).drawBitmap(bitmapDecodeByteArray, 1.0f, 1.0f, Paint())
            try {
                a.d.f fVar = new a.d.f(ByteArrayInputStream(byteArray))
                a(fVar)
                fVar.skipBytes(1)
                Byte b2 = fVar.readByte()
                Byte b3 = fVar.readByte()
                fVar.skipBytes(1)
                fVar.skipBytes(8)
                Int i = fVar.readInt()
                Int i2 = fVar.readInt()
                Int i3 = fVar.readInt()
                Int i4 = fVar.readInt()
                fVar.skipBytes(4)
                hVar = h(i, i2, i3, i4, fVar.a(b2), fVar.a(b3))
            } catch (Exception e) {
                hVar = null
            }
            if (hVar != null) {
                a(bitmapCreateBitmap, height + 1, hVar.f54a + 1, width - hVar.f55b)
                b(bitmapCreateBitmap, width + 1, hVar.c + 1, height - hVar.d)
                Array<Int> iArr = hVar.e
                Int i5 = 0
                Int length = iArr.length
                if (length == 0) {
                    a(bitmapCreateBitmap, 0, 1, width)
                } else {
                    while (i5 < length) {
                        a(bitmapCreateBitmap, 0, iArr[i5] + 1, iArr[i5 + 1])
                        i5 += 2
                        length = iArr.length
                    }
                }
                Array<Int> iArr2 = hVar.f
                Int i6 = 0
                Int length2 = iArr2.length
                if (length2 == 0) {
                    b(bitmapCreateBitmap, 0, 1, height)
                } else {
                    while (i6 < length2) {
                        b(bitmapCreateBitmap, 0, iArr2[i6] + 1, iArr2[i6 + 1])
                        i6 += 2
                        length2 = iArr2.length
                    }
                }
            } else {
                a(bitmapCreateBitmap, height + 1, 1, width)
                b(bitmapCreateBitmap, width + 1, 1, height)
                a(bitmapCreateBitmap, 0, 1, width)
                b(bitmapCreateBitmap, 0, 1, height)
            }
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } catch (IOException e2) {
            throw new a.a.ExceptionA(e2)
        }
    }
}
