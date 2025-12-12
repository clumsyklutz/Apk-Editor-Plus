package android.support.v4.print

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.util.Log
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class PrintHelper {

    @SuppressLint({"InlinedApi"})
    public static val COLOR_MODE_COLOR = 2

    @SuppressLint({"InlinedApi"})
    public static val COLOR_MODE_MONOCHROME = 1
    static final Boolean IS_MIN_MARGINS_HANDLING_CORRECT
    private static val LOG_TAG = "PrintHelper"
    private static val MAX_PRINT_SIZE = 3500
    public static val ORIENTATION_LANDSCAPE = 1
    public static val ORIENTATION_PORTRAIT = 2
    static final Boolean PRINT_ACTIVITY_RESPECTS_ORIENTATION
    public static val SCALE_MODE_FILL = 2
    public static val SCALE_MODE_FIT = 1
    final Context mContext
    BitmapFactory.Options mDecodeOptions = null
    val mLock = Object()
    Int mScaleMode = 2
    Int mColorMode = 2
    Int mOrientation = 1

    public interface OnPrintFinishCallback {
        Unit onFinish()
    }

    @RequiresApi(19)
    class PrintBitmapAdapter extends PrintDocumentAdapter {
        private PrintAttributes mAttributes
        private final Bitmap mBitmap
        private final OnPrintFinishCallback mCallback
        private final Int mFittingMode
        private final String mJobName

        PrintBitmapAdapter(String str, Int i, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            this.mJobName = str
            this.mFittingMode = i
            this.mBitmap = bitmap
            this.mCallback = onPrintFinishCallback
        }

        @Override // android.print.PrintDocumentAdapter
        fun onFinish() {
            if (this.mCallback != null) {
                this.mCallback.onFinish()
            }
        }

        @Override // android.print.PrintDocumentAdapter
        fun onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            this.mAttributes = printAttributes2
            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), printAttributes2.equals(printAttributes) ? false : true)
        }

        @Override // android.print.PrintDocumentAdapter
        fun onWrite(Array<PageRange> pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback)
        }
    }

    @RequiresApi(19)
    class PrintUriAdapter extends PrintDocumentAdapter {
        PrintAttributes mAttributes
        Bitmap mBitmap = null
        final OnPrintFinishCallback mCallback
        final Int mFittingMode
        final Uri mImageFile
        final String mJobName
        AsyncTask mLoadBitmap

        PrintUriAdapter(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback, Int i) {
            this.mJobName = str
            this.mImageFile = uri
            this.mCallback = onPrintFinishCallback
            this.mFittingMode = i
        }

        Unit cancelLoad() {
            synchronized (PrintHelper.this.mLock) {
                if (PrintHelper.this.mDecodeOptions != null) {
                    if (Build.VERSION.SDK_INT < 24) {
                        PrintHelper.this.mDecodeOptions.requestCancelDecode()
                    }
                    PrintHelper.this.mDecodeOptions = null
                }
            }
        }

        @Override // android.print.PrintDocumentAdapter
        fun onFinish() {
            super.onFinish()
            cancelLoad()
            if (this.mLoadBitmap != null) {
                this.mLoadBitmap.cancel(true)
            }
            if (this.mCallback != null) {
                this.mCallback.onFinish()
            }
            if (this.mBitmap != null) {
                this.mBitmap.recycle()
                this.mBitmap = null
            }
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [android.support.v4.print.PrintHelper$PrintUriAdapter$1] */
        @Override // android.print.PrintDocumentAdapter
        fun onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            synchronized (this) {
                this.mAttributes = printAttributes2
            }
            if (cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled()
            } else if (this.mBitmap != null) {
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), printAttributes2.equals(printAttributes) ? false : true)
            } else {
                this.mLoadBitmap = AsyncTask() { // from class: android.support.v4.print.PrintHelper.PrintUriAdapter.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    fun doInBackground(Uri... uriArr) {
                        try {
                            return PrintHelper.this.loadConstrainedBitmap(PrintUriAdapter.this.mImageFile)
                        } catch (FileNotFoundException e) {
                            return null
                        }
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    fun onCancelled(Bitmap bitmap) {
                        layoutResultCallback.onLayoutCancelled()
                        PrintUriAdapter.this.mLoadBitmap = null
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    fun onPostExecute(Bitmap bitmap) {
                        PrintAttributes.MediaSize mediaSize
                        super.onPostExecute((AnonymousClass1) bitmap)
                        if (bitmap != null && (!PrintHelper.PRINT_ACTIVITY_RESPECTS_ORIENTATION || PrintHelper.this.mOrientation == 0)) {
                            synchronized (this) {
                                mediaSize = PrintUriAdapter.this.mAttributes.getMediaSize()
                            }
                            if (mediaSize != null && mediaSize.isPortrait() != PrintHelper.isPortrait(bitmap)) {
                                Matrix matrix = Matrix()
                                matrix.postRotate(90.0f)
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
                            }
                        }
                        PrintUriAdapter.this.mBitmap = bitmap
                        if (bitmap != null) {
                            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(PrintUriAdapter.this.mJobName).setContentType(1).setPageCount(1).build(), printAttributes2.equals(printAttributes) ? false : true)
                        } else {
                            layoutResultCallback.onLayoutFailed(null)
                        }
                        PrintUriAdapter.this.mLoadBitmap = null
                    }

                    @Override // android.os.AsyncTask
                    protected fun onPreExecute() {
                        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.support.v4.print.PrintHelper.PrintUriAdapter.1.1
                            @Override // android.os.CancellationSignal.OnCancelListener
                            fun onCancel() {
                                PrintUriAdapter.this.cancelLoad()
                                cancel(false)
                            }
                        })
                    }
                }.execute(new Uri[0])
            }
        }

        @Override // android.print.PrintDocumentAdapter
        fun onWrite(Array<PageRange> pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback)
        }
    }

    static {
        PRINT_ACTIVITY_RESPECTS_ORIENTATION = Build.VERSION.SDK_INT < 20 || Build.VERSION.SDK_INT > 23
        IS_MIN_MARGINS_HANDLING_CORRECT = Build.VERSION.SDK_INT != 23
    }

    constructor(@NonNull Context context) {
        this.mContext = context
    }

    static Bitmap convertBitmapForColorMode(Bitmap bitmap, Int i) {
        if (i != 1) {
            return bitmap
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888)
        Canvas canvas = Canvas(bitmapCreateBitmap)
        Paint paint = Paint()
        ColorMatrix colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0.0f)
        paint.setColorFilter(ColorMatrixColorFilter(colorMatrix))
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint)
        canvas.setBitmap(null)
        return bitmapCreateBitmap
    }

    @RequiresApi(19)
    private static PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder minMargins = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins())
        if (printAttributes.getColorMode() != 0) {
            minMargins.setColorMode(printAttributes.getColorMode())
        }
        if (Build.VERSION.SDK_INT >= 23 && printAttributes.getDuplexMode() != 0) {
            minMargins.setDuplexMode(printAttributes.getDuplexMode())
        }
        return minMargins
    }

    static Matrix getMatrix(Int i, Int i2, RectF rectF, Int i3) {
        Matrix matrix = Matrix()
        Float fWidth = rectF.width() / i
        Float fMax = i3 == 2 ? Math.max(fWidth, rectF.height() / i2) : Math.min(fWidth, rectF.height() / i2)
        matrix.postScale(fMax, fMax)
        matrix.postTranslate((rectF.width() - (i * fMax)) / 2.0f, (rectF.height() - (fMax * i2)) / 2.0f)
        return matrix
    }

    static Boolean isPortrait(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight()
    }

    private fun loadBitmap(Uri uri, BitmapFactory.Options options) throws IOException {
        InputStream inputStreamOpenInputStream = null
        if (uri == null || this.mContext == null) {
            throw IllegalArgumentException("bad argument to loadBitmap")
        }
        try {
            inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(uri)
            return BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options)
        } finally {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close()
                } catch (IOException e) {
                    Log.w(LOG_TAG, "close fail ", e)
                }
            }
        }
    }

    fun systemSupportsPrint() {
        return Build.VERSION.SDK_INT >= 19
    }

    public final Int getColorMode() {
        return this.mColorMode
    }

    public final Int getOrientation() {
        if (Build.VERSION.SDK_INT < 19 || this.mOrientation != 0) {
            return this.mOrientation
        }
        return 1
    }

    public final Int getScaleMode() {
        return this.mScaleMode
    }

    final Bitmap loadConstrainedBitmap(Uri uri) throws IOException {
        BitmapFactory.Options options
        Int i = 1
        Bitmap bitmapLoadBitmap = null
        if (uri == null || this.mContext == null) {
            throw IllegalArgumentException("bad argument to getScaledBitmap")
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options()
        options2.inJustDecodeBounds = true
        loadBitmap(uri, options2)
        Int i2 = options2.outWidth
        Int i3 = options2.outHeight
        if (i2 > 0 && i3 > 0) {
            Int iMax = Math.max(i2, i3)
            while (iMax > MAX_PRINT_SIZE) {
                iMax >>>= 1
                i <<= 1
            }
            if (i > 0 && Math.min(i2, i3) / i > 0) {
                synchronized (this.mLock) {
                    this.mDecodeOptions = new BitmapFactory.Options()
                    this.mDecodeOptions.inMutable = true
                    this.mDecodeOptions.inSampleSize = i
                    options = this.mDecodeOptions
                }
                try {
                    bitmapLoadBitmap = loadBitmap(uri, options)
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null
                    }
                } catch (Throwable th) {
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null
                        throw th
                    }
                }
            }
        }
        return bitmapLoadBitmap
    }

    public final Unit printBitmap(@NonNull String str, @NonNull Bitmap bitmap) {
        printBitmap(str, bitmap, (OnPrintFinishCallback) null)
    }

    public final Unit printBitmap(@NonNull String str, @NonNull Bitmap bitmap, @Nullable OnPrintFinishCallback onPrintFinishCallback) {
        if (Build.VERSION.SDK_INT < 19 || bitmap == null) {
            return
        }
        ((PrintManager) this.mContext.getSystemService("print")).print(str, PrintBitmapAdapter(str, this.mScaleMode, bitmap, onPrintFinishCallback), new PrintAttributes.Builder().setMediaSize(isPortrait(bitmap) ? PrintAttributes.MediaSize.UNKNOWN_PORTRAIT : PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE).setColorMode(this.mColorMode).build())
    }

    public final Unit printBitmap(@NonNull String str, @NonNull Uri uri) {
        printBitmap(str, uri, (OnPrintFinishCallback) null)
    }

    public final Unit printBitmap(@NonNull String str, @NonNull Uri uri, @Nullable OnPrintFinishCallback onPrintFinishCallback) {
        if (Build.VERSION.SDK_INT < 19) {
            return
        }
        PrintUriAdapter printUriAdapter = PrintUriAdapter(str, uri, onPrintFinishCallback, this.mScaleMode)
        PrintManager printManager = (PrintManager) this.mContext.getSystemService("print")
        PrintAttributes.Builder builder = new PrintAttributes.Builder()
        builder.setColorMode(this.mColorMode)
        if (this.mOrientation == 1 || this.mOrientation == 0) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE)
        } else if (this.mOrientation == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT)
        }
        printManager.print(str, printUriAdapter, builder.build())
    }

    public final Unit setColorMode(Int i) {
        this.mColorMode = i
    }

    public final Unit setOrientation(Int i) {
        this.mOrientation = i
    }

    public final Unit setScaleMode(Int i) {
        this.mScaleMode = i
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [android.support.v4.print.PrintHelper$1] */
    @RequiresApi(19)
    final Unit writeBitmap(final PrintAttributes printAttributes, final Int i, final Bitmap bitmap, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        val printAttributesBuild = IS_MIN_MARGINS_HANDLING_CORRECT ? printAttributes : copyAttributes(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build()
        AsyncTask() { // from class: android.support.v4.print.PrintHelper.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            fun doInBackground(Void... voidArr) {
                RectF rectF
                try {
                    if (cancellationSignal.isCanceled()) {
                        return null
                    }
                    PrintedPdfDocument printedPdfDocument = PrintedPdfDocument(PrintHelper.this.mContext, printAttributesBuild)
                    Bitmap bitmapConvertBitmapForColorMode = PrintHelper.convertBitmapForColorMode(bitmap, printAttributesBuild.getColorMode())
                    if (cancellationSignal.isCanceled()) {
                        return null
                    }
                    try {
                        PdfDocument.Page pageStartPage = printedPdfDocument.startPage(1)
                        if (PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT) {
                            rectF = RectF(pageStartPage.getInfo().getContentRect())
                        } else {
                            PrintedPdfDocument printedPdfDocument2 = PrintedPdfDocument(PrintHelper.this.mContext, printAttributes)
                            PdfDocument.Page pageStartPage2 = printedPdfDocument2.startPage(1)
                            rectF = RectF(pageStartPage2.getInfo().getContentRect())
                            printedPdfDocument2.finishPage(pageStartPage2)
                            printedPdfDocument2.close()
                        }
                        Matrix matrix = PrintHelper.getMatrix(bitmapConvertBitmapForColorMode.getWidth(), bitmapConvertBitmapForColorMode.getHeight(), rectF, i)
                        if (!PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT) {
                            matrix.postTranslate(rectF.left, rectF.top)
                            pageStartPage.getCanvas().clipRect(rectF)
                        }
                        pageStartPage.getCanvas().drawBitmap(bitmapConvertBitmapForColorMode, matrix, null)
                        printedPdfDocument.finishPage(pageStartPage)
                        if (cancellationSignal.isCanceled()) {
                            printedPdfDocument.close()
                            if (parcelFileDescriptor != null) {
                                try {
                                    parcelFileDescriptor.close()
                                } catch (IOException e) {
                                }
                            }
                            if (bitmapConvertBitmapForColorMode == bitmap) {
                                return null
                            }
                            bitmapConvertBitmapForColorMode.recycle()
                            return null
                        }
                        printedPdfDocument.writeTo(FileOutputStream(parcelFileDescriptor.getFileDescriptor()))
                        printedPdfDocument.close()
                        if (parcelFileDescriptor != null) {
                            try {
                                parcelFileDescriptor.close()
                            } catch (IOException e2) {
                            }
                        }
                        if (bitmapConvertBitmapForColorMode == bitmap) {
                            return null
                        }
                        bitmapConvertBitmapForColorMode.recycle()
                        return null
                    } finally {
                    }
                } catch (Throwable th) {
                    return th
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            fun onPostExecute(Throwable th) {
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled()
                } else if (th == null) {
                    writeResultCallback.onWriteFinished(new Array<PageRange>{PageRange.ALL_PAGES})
                } else {
                    Log.e(PrintHelper.LOG_TAG, "Error writing printed content", th)
                    writeResultCallback.onWriteFailed(null)
                }
            }
        }.execute(new Void[0])
    }
}
