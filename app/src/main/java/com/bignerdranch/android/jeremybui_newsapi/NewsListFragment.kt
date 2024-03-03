package com.bignerdranch.android.jeremybui_newsapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.jeremybui_newsapi.databinding.FragmentNewsListBinding
import android.util.Log

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NewsListFragment", "onViewCreated called")

        // Set up RecyclerView
        binding.newsListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample list of articles
        val sampleArticles = listOf(
            Article(
                Source("source_id", "Sample Source"),
                "Author",
                "Sample Article 1",
                "This is a sample description for article 1.",
                "https://example.com/article1",
                "https://example.com/article1/image.jpg",
                "2024-03-03",
                "Sample content for article 1."
            ),
            Article(
                Source("source_id", "Sample Source"),
                "Author",
                "Sample Article 2",
                "This is a sample description for article 2.",
                "https://example.com/article2",
                "https://example.com/article2/image.jpg",
                "2024-03-03",
                "Sample content for article 2."
            )
        )

        val adapter = NewsListAdapter(sampleArticles) { article ->
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(article)
            findNavController().navigate(action)
        }

        binding.newsListRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
