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
import com.example.arpitdec5.popularmovies.data.ReviewHandler;
import com.example.arpitdec5.popularmovies.utils.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewsFragment extends Fragment {

    private Activity mActivity;
    private ReviewHandler reviewHandler;

    private ArrayList<String> reviews ;
    private TextView textView;
    private String no_review = getString(R.string.no_review);

    @BindView(R.id.review_list_review) RecyclerView listView;

    public ReviewsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.setDebug(true);

        reviewHandler = new ReviewHandler(mActivity);
        reviews = new ArrayList<String>();

        Intent intent = mActivity.getIntent();
        Bundle bundle = intent.getExtras();
        String movie_title = bundle.getString("yolo");
        reviews = reviewHandler.get_review_link(movie_title);

        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(new ListAdapterr(reviews, mActivity));
        listView.addItemDecoration(new DividerItemDecoration(mActivity , LinearLayoutManager.VERTICAL));

        return rootView ;
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

            TextView textView;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_review);
                textView = (TextView) itemView.findViewById(R.id.user_review);
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review , parent ,false);
            return (new ViewHolder(view));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            String text = arrayList.get(position);

            Picasso.with(mActivity)
                    .load(R.drawable.reading)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .resize(60,60)
                    .into(holder.imageView);
            holder.textView.setText("Review " + (position + 1));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(arrayList.size()>0) {

                        Toast.makeText(mActivity, "Fetching Review..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(position)));
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(mActivity, no_review, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}