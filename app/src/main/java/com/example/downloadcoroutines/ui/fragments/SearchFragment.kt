package com.example.downloadcoroutines.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.adapters.GenericAdapter
import com.example.downloadcoroutines.modelClasses.SpecialistsModel
import com.example.downloadcoroutines.showToast
import com.example.downloadcoroutines.viewModel.PicsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), GenericAdapter.OnItemClickListener<Any> {
    lateinit var searchAdapter: GenericAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var viewmodel: PicsViewModel
    lateinit var searchList: ArrayList<SpecialistsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeSearch.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_gd2nnpqy.json")

        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        searchList = ArrayList()
        if (!::viewmodel.isInitialized) {
            viewmodel = ViewModelProvider(this).get(PicsViewModel::class.java)

        }
        setSearchAdapter()

        edSearch.doOnTextChanged { _, _, _, _ ->
            if (searchList.isNotEmpty()) {
                var list = searchList.filter { it.author == edSearch.text.toString() }
                searchAdapter =
                    GenericAdapter(
                        list as ArrayList<Any>,
                        this,
                        R.layout.row_home_pics
                    )
                rvSearch.adapter = searchAdapter
                rvSearch.layoutManager = layoutManager
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onStart() {
        super.onStart()
        val window: Window = requireActivity().getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.gray_deep)
    }

    fun setSearchAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel.getPics(1, 500)
            if (!viewmodel.picsResponse.hasActiveObservers()) {
                viewmodel.picsResponse.observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        return@Observer
                    } else {
                        searchList = it
                    }

                })
            }
        }


    }

    override fun onItemClick(view: View?, position: Int, `object`: Any) {

    }

}