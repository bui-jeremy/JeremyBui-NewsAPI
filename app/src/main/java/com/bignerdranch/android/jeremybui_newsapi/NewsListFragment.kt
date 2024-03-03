package com.bignerdranch.android.jeremybui_newsapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.jeremybui_newsapi.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsListViewModel by viewModels {
        NewsListViewModelFactory(NewsRepository(RetrofitInstance.newsApiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsListAdapter(mutableListOf()) { article ->
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(article)
            findNavController().navigate(action)
        }
        binding.newsListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsListRecyclerView.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            adapter.updateArticles(articles)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
