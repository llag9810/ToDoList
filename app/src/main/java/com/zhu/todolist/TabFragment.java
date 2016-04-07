package com.zhu.todolist;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab, container, false);
        ListView listView = (ListView)view.findViewById(R.id.list1);
        Cursor cursor = DbHelper.getInstance(getActivity()).rawQuery("SELECT * from eventData", null);
        listView.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.item, cursor,
                new String[] {"title", "createDate", "deadline", "priority", "isFinished"},
                new int[] {R.id.titleText, R.id.dateText, R.id.deadlineText, R.id.priorityText,
                        R.id.isFinishedText},
                0));

        return view;
    }


}
