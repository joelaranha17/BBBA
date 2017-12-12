package com.firstapp.joel.bbba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ListViewFragment extends Fragment {
    StringRequest stringRequest;
    Gson gson;
    RecyclerView recyclerView;
    ArrayList<ResultsItem> list;
    RequestQueue mQueue;
    MyAdapter adapter;

    public ListViewFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlistview, container, false);
        gson = new Gson();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetchData();
        return view;
    }

    void fetchData() {
        String url = "http://newtojava.000webhostapp.com/bbvajson.txt";
        mQueue = Volley.newRequestQueue(getContext());
        stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                com.firstapp.joel.bbba.Response res = gson.fromJson(response, com.firstapp.joel.bbba.Response.class);
                adapter = new MyAdapter(getActivity(), new ArrayList<ResultsItem>(res.getResults()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }
}
