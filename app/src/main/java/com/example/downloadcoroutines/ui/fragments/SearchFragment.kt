package com.example.downloadcoroutines.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.adapters.GenericAdapter
import com.example.downloadcoroutines.modelClasses.PicsModel
import com.example.downloadcoroutines.utils.NetworkResource
import com.example.downloadcoroutines.utils.showToast
import com.example.downloadcoroutines.viewModel.PicsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*


@AndroidEntryPoint
class SearchFragment : Fragment(), GenericAdapter.OnItemClickListener<Any> {
    private lateinit var searchAdapter: GenericAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    val viewmodel: PicsViewModel by viewModels()
    private lateinit var searchList: ArrayList<PicsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()


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

    private fun initViews() {
        animeSearch.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_gd2nnpqy.json")

        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        searchList = ArrayList()

        setSearchAdapter()

        edSearch.doOnTextChanged { _, _, _, _ ->
            if (searchList.isNotEmpty()) {
                val list = searchList.filter { it.author == edSearch.text.toString() }
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

    private fun setSearchAdapter() {
        viewmodel.getPics(page = 1,100)
        viewmodel.picsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResource.Success -> {
                    searchList = it.data as ArrayList<PicsModel>

                }
                is NetworkResource.Error -> {
                    requireContext().showToast(
                        it.error!!.localizedMessage
                    )
                }
                is NetworkResource.Loading -> {
                }
            }


        }


    }

    override fun onItemClick(view: View?, position: Int, `object`: Any) {

    }

}