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


public class ShowsFragment extends Fragment {
    Bundle bundle1=new Bundle(),bundle2=new Bundle(),bundle3=new Bundle(),bundle4=new Bundle();
    ListFragment fragment1=new ListFragment(),fragment2=new ListFragment(),fragment3=new ListFragment(),fragment4=new ListFragment();


    private OnFragmentInteractionListener mListener;

    public ShowsFragment() {
        // Required empty public constructor
    }
    public static ShowsFragment newInstance(String param1, String param2) {
        ShowsFragment fragment = new ShowsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output=inflater.inflate(R.layout.fragment_shows, container, false);
        FragmentManager manager=getChildFragmentManager();
        bundle1.putString(Constants.TYPE,Constants.AIR_TODAY_SHOWS);
        bundle1.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
        fragment1.setArguments(bundle1);
        FragmentTransaction transaction1=manager.beginTransaction();
        transaction1.replace(R.id.AirTodayContainer,fragment1);
        transaction1.commit();
        bundle2.putString(Constants.TYPE,Constants.POPULAR_SHOWS);
        bundle2.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
        fragment2.setArguments(bundle2);
        FragmentTransaction transaction2=manager.beginTransaction();
        transaction2.replace(R.id.PopularShowsContainer,fragment2);
        transaction2.commit();
        bundle3.putString(Constants.TYPE,Constants.ON_AIR_SHOWS);
        bundle3.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
        fragment3.setArguments(bundle3);
        FragmentTransaction transaction3=manager.beginTransaction();
        transaction3.replace(R.id.OnAirContainer,fragment3);
        transaction3.commit();
        bundle4.putString(Constants.TYPE,Constants.TOP_RATED_SHOWS);
        bundle4.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
        fragment4.setArguments(bundle4);
        FragmentTransaction transaction4=manager.beginTransaction();
        transaction4.replace(R.id.TopRatedShowsContainer,fragment4);
        transaction4.commit();
        return output;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onShowsFragmentInteraction();
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

        void onShowsFragmentInteraction();
    }
}
