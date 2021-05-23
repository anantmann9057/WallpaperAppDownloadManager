package com.example.downloadcoroutines

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment

import android.view.View

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*

import com.example.downloadcoroutines.adapters.GenericAdapter
import com.example.downloadcoroutines.viewModel.PicsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nexogic.adapters.DataBindingAdapter
import com.example.downloadcoroutines.modelClasses.SpecialistsModel
import com.nexogic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottomsheet_layout.*
import kotlinx.android.synthetic.main.bottomsheet_layout.view.*
import kotlinx.coroutines.*
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), GenericAdapter.OnItemClickListener<Any>,
    View.OnClickListener {
    var downloadProgress = 0
    lateinit var job: Job
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var viewmodel: PicsViewModel
    lateinit var genericAdapter: GenericAdapter
    lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var imageList: ArrayList<SpecialistsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        (layoutManager as StaggeredGridLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS)
        setBottomSheet()
        imageList = ArrayList()


        if (!::viewmodel.isInitialized) {
            viewmodel = ViewModelProvider(this).get(PicsViewModel::class.java)
            job = CoroutineScope(Dispatchers.IO).launch {
                viewmodel.getPics(1, 100)
            }
        }
        setClickListeners()
        initPicsAdapter()
        setPicsAdapter()
    }


    private fun setBottomSheet() {
        GlobalScope.launch(Dispatchers.Main) {
            bottomSheetView =
                layoutInflater.inflate(R.layout.bottomsheet_layout, null)

            bottomSheetDialog = BottomSheetDialog(this@MainActivity)
            bottomSheetDialog.setContentView(bottomSheetView)


        }
    }


    private fun setClickListeners() {
    }

    private fun initPicsAdapter() {
        if (!::genericAdapter.isInitialized) {
            genericAdapter =
                GenericAdapter(
                    imageList as ArrayList<Any>,
                    this,
                    R.layout.row_home_pics
                )
            rvImages.layoutManager = layoutManager
            rvImages.adapter = genericAdapter
            rvImages.setItemViewCacheSize(500)


        }
    }

    fun setPicsAdapter() {
        if (!viewmodel.picsResponse.hasActiveObservers()) {
            viewmodel.picsResponse.observe(this, Observer
            {
                if (it == null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(3000)
                        repeat(3) {
                            job.start()
                        }

                    }
                } else {
                    if (imageList.isEmpty()) {
                        imageList = it
                        genericAdapter.notifyAdapter(it as ArrayList<Any>)

                    }
                }
            })
            job.start()


        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun downloadFile(url: String, fileName: String? = null) {
        val directory = File(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(fileName)
                .setVisibleInDownloadsUi(true)
                .setDescription(fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }


        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)

        GlobalScope.launch(Dispatchers.IO) {

            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()

                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val fileStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                when (fileStatus) {
                    DownloadManager.STATUS_FAILED -> {
                    }
                    DownloadManager.STATUS_PAUSED -> {
                    }
                    DownloadManager.STATUS_PENDING -> {
                    }
                    DownloadManager.STATUS_RUNNING -> {
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {


                    }
                }
                cursor.close()
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun askPermissions(url: String, imageName: String? = null) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Permission required")
                    .setMessage("Permission required to save files.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                        finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                downloadFile(url, imageName)
            }
        } else {
            // Permission has already been granted
            downloadFile(url, imageName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClick(view: View?, position: Int, `object`: Any) {
        if (`object` is SpecialistsModel) {
            when (view?.id) {
                R.id.cardIImage -> {

                    DataBindingAdapter.let {
                        it.setSrc(bottomSheetDialog.ivImage, `object`.download_url)
                    }
                    bottomSheetDialog.show()
                    bottomSheetDialog.btDownload.setOnClickListener {

                        askPermissions(
                            `object`.download_url.toString(),
                            `object`.author
                        )

                    }
                }
            }


        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.floating -> {
            }
        }
    }

}