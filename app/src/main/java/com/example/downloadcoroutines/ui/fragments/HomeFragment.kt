package com.example.downloadcoroutines.ui.fragments

import android.Manifest
import android.animation.ArgbEvaluator
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.downloadcoroutines.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.adapters.GenericAdapter
import com.example.downloadcoroutines.modelClasses.SpecialistsModel
import com.example.downloadcoroutines.viewModel.PicsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nexogic.adapters.DataBindingAdapter
import kotlinx.android.synthetic.main.bottomsheet_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.io.File


class HomeFragment : Fragment(), GenericAdapter.OnItemClickListener<Any> {
    lateinit var job: Job


    var page = 1

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var viewmodel: PicsViewModel

    lateinit var genericAdapter: GenericAdapter
    lateinit var imageList: ArrayList<SpecialistsModel>

    val dialog by lazy { Dialog(requireContext()) }

    lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        setBottomSheet()

        imageList = ArrayList()
        if (!::viewmodel.isInitialized) {
            viewmodel = ViewModelProvider(this).get(PicsViewModel::class.java)

        }
        initPicsAdapter()
        setPicsAdapter(page)
        nestedHome.setOnScrollChangeListener(nestedScrollListener)

        animeHeaderHome.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_ynwbrgau.json")
    }

    var nestedScrollListener = object : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(
            v: NestedScrollView?,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {

            if (scrollY == v!!.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++
                setPicsAdapter(page)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setBottomSheet() {
        GlobalScope.launch(Dispatchers.Main) {
            bottomSheetView =
                layoutInflater.inflate(R.layout.bottomsheet_layout, null)

            bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)


        }
    }


    private fun initPicsAdapter() {
        if (!::genericAdapter.isInitialized) {
            genericAdapter =
                GenericAdapter(
                    imageList as ArrayList<Any>,
                    this,
                    R.layout.row_home_pics
                )
            rvImages.let {
                it.isNestedScrollingEnabled = false
                it.layoutManager = layoutManager
                it.adapter = genericAdapter

            }
            if (imageList.isNotEmpty()) {
                rvImages.setItemViewCacheSize(imageList.size)
            }


        }
    }

    fun setPicsAdapter(page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            async {
                viewmodel.getPics(page, 10)
            }.await()
        }
        if (!viewmodel.picsResponse.hasActiveObservers()) {
            viewmodel.picsResponse.observe(requireActivity(), Observer
            {
                if (it == null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(3000)
                        repeat(3) {
                            viewmodel.getPics(page,10)
                        }

                    }
                } else {
                    rvCat.apply {
                        isNestedScrollingEnabled = false
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = GenericAdapter(
                            it as ArrayList<Any>,
                            this@HomeFragment,
                            R.layout.row_categories
                        )
                        setItemViewCacheSize(500)
                    }
                    if (imageList.isEmpty()) {
                        imageList = it
                        genericAdapter.notifyAdapter(it as ArrayList<Any>)
                    } else {
                        for (i in it) {
                            imageList.add(
                                SpecialistsModel(
                                    i.author,
                                    i.download_url,
                                    i.id,
                                    i.url
                                )
                            )
                        }
                        genericAdapter.notifyItemInserted(imageList.size)
                    }
                }
            })


        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun downloadFile(url: String, fileName: String? = null) {
        dialog.show()
        val directory = File(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

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
                        dialog.dismiss()
                    }
                    DownloadManager.STATUS_PAUSED -> {
                        dialog.dismiss()
                    }
                    DownloadManager.STATUS_PENDING -> {
                    }
                    DownloadManager.STATUS_RUNNING -> {
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        dialog.dismiss()

                    }
                }
                cursor.close()
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun askPermissions(url: String, imageName: String? = null) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(requireContext())
                    .setTitle("Permission required")
                    .setMessage("Permission required to save files.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
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
                R.id.ivCategory -> {
                    var list = imageList.filter {
                        it.author.equals(`object`.author)
                    }
                    genericAdapter =
                        GenericAdapter(list as ArrayList<Any>, this, R.layout.row_home_pics)
                    rvImages.adapter = genericAdapter
                    genericAdapter.notifyAdapter(list as ArrayList<Any>)
                }
                R.id.cardIImage -> {

                    DataBindingAdapter.let {
                        it.setSrc(bottomSheetDialog.ivImage, `object`.download_url)
                    }

                    bottomSheetDialog.apply {

                        btDownload.setOnClickListener {
                            askPermissions(
                                `object`.download_url.toString(),
                                `object`.author
                            )
                        }
                        tvAuthorName.text = `object`.author

                        show()
                    }
                }
            }


        }
    }

}