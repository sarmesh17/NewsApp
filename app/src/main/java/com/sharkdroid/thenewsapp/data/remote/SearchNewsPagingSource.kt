package com.sharkdroid.thenewsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sharkdroid.thenewsapp.domain.model.Article

class SearchNewsPagingSource(

    private val newsAPI: NewsAPI,
    private val searchQuery:String,
    private val source:String,
): PagingSource<Int,Article> () {

    private var totalNewsCount= 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page=params.key?:1
        return try {

            val newsResponse=newsAPI.searchNews(searchQuery=searchQuery,page=page, sources = source)
            totalNewsCount += newsResponse.articles.size
            val article=newsResponse.articles.distinctBy {
                it.title } // remove duplicate
            LoadResult.Page(
                data = article,
                nextKey = if (totalNewsCount== newsResponse.totalResults) null else page+1,
                prevKey = null
            )

        }catch (e:Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // Try to get the anchor position from the PagingState
        return  state.anchorPosition?.let { anchorPosition ->
            // Find the page that is closest to the anchor position
            val anchorPage=state.closestPageToPosition(anchorPosition)
            // Determine the key to use for refreshing the data:
            // If the previous key is available, increment it by 1 (to get the next page)
            // If the previous key is not available, check the next key and decrement it by 1 (to get the previous page)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

}