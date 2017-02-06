package com.example.arpitdec5.popularmovies.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpitdec5.popularmovies.R;
import com.example.arpitdec5.popularmovies.data.TrailorHandler;
import com.example.arpitdec5.popularmovies.utils.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrailorsFragment extends Fragment {

    Activity mActivity;
    ArrayList<String> trailors;
    TrailorHandler trailorHandler;
    ImageView imageView;
    TextView textView;

    @BindView(R.id.list_trailor) RecyclerView listView;

    public TrailorsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ButterKnife.setDebug(true);
        View rootView =  inflater.inflate(R.layout.fragment_trailors, container, false);
        ButterKnife.bind(this, rootView);
        trailors = new ArrayList<String>();
        trailorHandler = new TrailorHandler(mActivity);

        Intent intent = mActivity.getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("yolo");

        trailors = trailorHandler.get_trailor_link(title);
        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(new ListAdapterr(trailors, mActivity));
        listView.addItemDecoration(new DividerItemDecoration(mActivity , LinearLayoutManager.VERTICAL));

        return rootView;
    }


    public class ListAdapterr extends RecyclerView.Adapter<ListAdapterr.ViewHolder> {

        private ArrayList<String> arrayList;
        Activity mActivity;

        public ListAdapterr(ArrayList<String> array , Activity activity)
        {
            mActivity = activity;
            arrayList = array;
        }

        public void add(String item , int position)
        {
            arrayList.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(String item)
        {
            int position = arrayList.indexOf(item);
            arrayList.remove(position);
            notifyItemRemoved(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_trailor);
                textView = (TextView) itemView.findViewById(R.id.text_trailers);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailor , parent ,false);
            return (new ViewHolder(view));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            String text = arrayList.get(position);

            Picasso.with(mActivity)
                    .load(R.drawable.play)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .resize(60,60)
                    .into(holder.imageView);
            holder.textView.setText("Trailor " + (position + 1));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(arrayList.size()>0) {
                        Toast.makeText(mActivity, "Fetching Trailor..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + arrayList.get(position)));
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(mActivity , "Sorry.. No Trailors available"  ,Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }

}
