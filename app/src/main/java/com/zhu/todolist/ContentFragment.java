package com.zhu.todolist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private FragmentTabHost tabHost;
    private final static String TAG_TAB_1 = "Tab1";
    private final static String TAG_TAB_2 = "Tab2";
    private final static String TAG_TAB_3 = "Tab3";


    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        tabHost = (FragmentTabHost)view.findViewById(R.id.frg_tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.real_tabcontent);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_1).setIndicator(TAG_TAB_1),
                TabFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_2).setIndicator(TAG_TAB_2),
                TabFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_3).setIndicator(TAG_TAB_3),
                TabFragment.class, null);
        return view;
    }


}
