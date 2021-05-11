package com.example.project3app3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShowsFragment extends Fragment {

    private ListSelectionListener mListener;
    private static final String TAG = "ShowsFragment";
    private ListView mShowsList;
    private int selection = -1;

    // Callback interface that allows this Fragment to notify the MainActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            // Set the ListSelectionListener for communicating with the MainActivity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListSelectionListener");
        }
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.show_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set list adapter and item click listener
        mShowsList = getView().findViewById(R.id.showList);
        mShowsList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.listitem, MainActivity.mShowsArray));
        mShowsList.setOnItemClickListener((parent, view, position, id) -> {
            selection = position;
            this.mListener.onListSelection(selection);
        });

        // Restore previous selection
        if (selection > 0) {
            mShowsList.setItemChecked(selection, true);
            this.mListener.onListSelection(selection);
        }
    }

}
