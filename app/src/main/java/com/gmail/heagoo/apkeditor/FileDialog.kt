package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Environment
import android.util.Log
import com.gmail.heagoo.apkeditor.ListenerList
import java.io.File
import java.io.FilenameFilter
import java.util.ArrayList
import java.util.Arrays
import java.util.Comparator
import java.util.Locale

class FileDialog {
    private static val PARENT_DIR = ".."
    private final String TAG
    private final Activity activity
    private File currentPath
    private ListenerList<DirectorySelectedListener> dirListenerList
    private String fileEndsWith
    private Array<String> fileList
    private ListenerList<FileSelectedListener> fileListenerList
    private Boolean selectDirectoryOption

    public interface DirectorySelectedListener {
        Unit directorySelected(File file)
    }

    public interface FileSelectedListener {
        Unit fileSelected(File file)
    }

    constructor(Activity activity, File file) {
        this(activity, file, null)
    }

    constructor(Activity activity, File file, String str) {
        this.TAG = getClass().getName()
        this.fileListenerList = new ListenerList<>()
        this.dirListenerList = new ListenerList<>()
        this.activity = activity
        setFileEndsWith(str)
        loadFileList(file.exists() ? file : Environment.getExternalStorageDirectory())
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun fireDirectorySelectedEvent(final File file) {
        this.dirListenerList.fireEvent(new ListenerList.FireHandler<DirectorySelectedListener>() { // from class: com.gmail.heagoo.apkeditor.FileDialog.4
            @Override // com.gmail.heagoo.apkeditor.ListenerList.FireHandler
            fun fireEvent(DirectorySelectedListener directorySelectedListener) {
                directorySelectedListener.directorySelected(file)
            }
        })
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun fireFileSelectedEvent(final File file) {
        this.fileListenerList.fireEvent(new ListenerList.FireHandler<FileSelectedListener>() { // from class: com.gmail.heagoo.apkeditor.FileDialog.3
            @Override // com.gmail.heagoo.apkeditor.ListenerList.FireHandler
            fun fireEvent(FileSelectedListener fileSelectedListener) {
                fileSelectedListener.fileSelected(file)
            }
        })
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun getChosenFile(String str) {
        return str.equals(PARENT_DIR) ? this.currentPath.getParentFile() : File(this.currentPath, str)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun loadFileList(File file) {
        this.currentPath = file
        ArrayList arrayList = ArrayList()
        if (file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().equals(Environment.getExternalStorageDirectory().getParentFile())) {
                arrayList.add(PARENT_DIR)
            }
            Array<String> list = file.list(FilenameFilter() { // from class: com.gmail.heagoo.apkeditor.FileDialog.5
                @Override // java.io.FilenameFilter
                fun accept(File file2, String str) {
                    File file3 = File(file2, str)
                    if (!file3.canRead()) {
                        return false
                    }
                    if (FileDialog.this.selectDirectoryOption) {
                        return file3.isDirectory()
                    }
                    return (FileDialog.this.fileEndsWith != null ? str.toLowerCase().endsWith(FileDialog.this.fileEndsWith) : true) || file3.isDirectory()
                }
            })
            Arrays.sort(list, new Comparator<String>() { // from class: com.gmail.heagoo.apkeditor.FileDialog.6
                @Override // java.util.Comparator
                fun compare(String str, String str2) {
                    return str.toLowerCase(Locale.getDefault()).compareTo(str2.toLowerCase(Locale.getDefault()))
                }
            })
            for (String str : list) {
                arrayList.add(str)
            }
        }
        this.fileList = (Array<String>) arrayList.toArray(new String[0])
    }

    private fun setFileEndsWith(String str) {
        this.fileEndsWith = str != null ? str.toLowerCase() : str
    }

    fun addDirectoryListener(DirectorySelectedListener directorySelectedListener) {
        this.dirListenerList.add(directorySelectedListener)
    }

    fun addFileListener(FileSelectedListener fileSelectedListener) {
        this.fileListenerList.add(fileSelectedListener)
    }

    fun createFileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity)
        builder.setTitle(this.currentPath.getPath())
        if (this.selectDirectoryOption) {
            builder.setPositiveButton("Select directory", new DialogInterface.OnClickListener() { // from class: com.gmail.heagoo.apkeditor.FileDialog.1
                @Override // android.content.DialogInterface.OnClickListener
                fun onClick(DialogInterface dialogInterface, Int i) {
                    Log.d(FileDialog.this.TAG, FileDialog.this.currentPath.getPath())
                    FileDialog fileDialog = FileDialog.this
                    fileDialog.fireDirectorySelectedEvent(fileDialog.currentPath)
                }
            })
        }
        builder.setItems(this.fileList, new DialogInterface.OnClickListener() { // from class: com.gmail.heagoo.apkeditor.FileDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            fun onClick(DialogInterface dialogInterface, Int i) {
                File chosenFile = FileDialog.this.getChosenFile(FileDialog.this.fileList[i])
                if (!chosenFile.isDirectory()) {
                    FileDialog.this.fireFileSelectedEvent(chosenFile)
                    return
                }
                FileDialog.this.loadFileList(chosenFile)
                dialogInterface.cancel()
                dialogInterface.dismiss()
                FileDialog.this.showDialog()
            }
        })
        return builder.show()
    }

    fun removeDirectoryListener(DirectorySelectedListener directorySelectedListener) {
        this.dirListenerList.remove(directorySelectedListener)
    }

    fun removeFileListener(FileSelectedListener fileSelectedListener) {
        this.fileListenerList.remove(fileSelectedListener)
    }

    fun setSelectDirectoryOption(Boolean z) {
        this.selectDirectoryOption = z
    }

    fun showDialog() {
        createFileDialog().show()
    }
}
