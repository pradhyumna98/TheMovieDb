package com.example.shubham.themoviedb.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
Bundle bundle1=new Bundle(),bundle2=new Bundle(),bundle3=new Bundle(),bundle4=new Bundle();
    ListFragment fragment1=new ListFragment(),fragment2=new ListFragment(),fragment3=new ListFragment(),fragment4=new ListFragment();

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output=inflater.inflate(R.layout.fragment_movies, container, false);
        FragmentManager manager=getChildFragmentManager();
        bundle1.putString(Constants.TYPE,Constants.NOW_SHOWING_MOVIES);
        fragment1.setArguments(bundle1);
        FragmentTransaction transaction1=manager.beginTransaction();
        transaction1.replace(R.id.NowShowingContainer,fragment1);
        transaction1.commit();
        bundle2.putString(Constants.TYPE,Constants.POPULAR_MOVIES);
        fragment2.setArguments(bundle2);
        FragmentTransaction transaction2=manager.beginTransaction();
        transaction2.replace(R.id.PopularContainer,fragment2);
        transaction2.commit();
        bundle3.putString(Constants.TYPE,Constants.UPCOMING_MOVIES);
        fragment3.setArguments(bundle3);
        FragmentTransaction transaction3=manager.beginTransaction();
        transaction3.replace(R.id.UpcomingContainer,fragment3);
        transaction3.commit();
        bundle4.putString(Constants.TYPE,Constants.TOP_RATED_MOVIES);
        fragment4.setArguments(bundle4);
        FragmentTransaction transaction4=manager.beginTransaction();
        transaction4.replace(R.id.TopRatedContainer,fragment4);
        transaction4.commit();
        return output;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
