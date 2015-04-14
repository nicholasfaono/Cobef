package br.org.bir.senai.cobef;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class PageFragment extends Fragment {

    public static final String ARG_PAGE_INDEX = "page_index";

    private String[] mPageUrls;
    private View mRootView;
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int pageIndex = getArguments().getInt(ARG_PAGE_INDEX);

        mPageUrls = getResources().getStringArray(R.array.page_urls);

        mRootView =  inflater.inflate(R.layout.fragment_page, container, false);
        mWebView = (WebView)mRootView.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mPageUrls[pageIndex]);

        return mRootView;
    }
}
