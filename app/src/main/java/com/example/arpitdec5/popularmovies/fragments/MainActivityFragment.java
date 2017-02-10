package com.example.arpitdec5.popularmovies.fragments;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arpitdec5.popularmovies.activities.MovieDescription;
import com.example.arpitdec5.popularmovies.R;
import com.example.arpitdec5.popularmovies.data.MovieDescriptionHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements SearchView.OnQueryTextListener{

    final private String SEARCH = "com.example.arpitdec5.searchText";
    private String searchText = "";
    private SearchView searchView;
    private NavigationView navigationView;

    Activity mActivity;
    MovieDescriptionHandler movieDescriptionHandler;
    RecyclerView grid;
    String[] images;
    ArrayList<String> arrayList;
    ArrayList<String> titeList;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest1;
    JsonObjectRequest jsonObjectRequest2;

    String url1 = "https://api.themoviedb.org/3/movie/";
    String url2 = "?api_key=e9bedc7d4abd75b8283a9734f5bcb6d2";
    String url3 = "http://image.tmdb.org/t/p/w185/";
    String toast_error = getString(R.string.error);

    public interface OnMovieClickListener{

        public void selectMovie(String movie_title);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        arrayList = new ArrayList<String>();
        titeList = new ArrayList<String>();
        setHasOptionsMenu(true);

        //checking the bundle to update search text
        if(savedInstanceState!=null && savedInstanceState.getString(SEARCH)!=null){
            searchText = savedInstanceState.getString(SEARCH);
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        requestQueue = Volley.newRequestQueue(mActivity);
        grid = (RecyclerView) rootView.findViewById(R.id.gri);
        movieDescriptionHandler = new MovieDescriptionHandler(mActivity);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(searchText!=null){
            outState.putString(SEARCH, searchText);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        //setting search items
        searchView = (SearchView) menu.findItem(R.id.search_movie).getActionView();
        searchView.setOnQueryTextListener(this);
        if(searchText!=null){
            searchView.setQuery(searchText, false);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase(Locale.getDefault());
        ArrayList<String> modifiedList = new ArrayList<String>();
        for(int i=0;i<arrayList.size();i++){

            String text = titeList.get(i);
            text = text.toLowerCase(Locale.getDefault());
            if(text.contains(newText))
                modifiedList.add(arrayList.get(i));
        }

        //by default arranging the movies by popularity
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (width/175.00);

        grid.setHasFixedSize(true);
        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(mActivity,spanCount);
        grid.setLayoutManager(linearLayoutManager);
        grid.setAdapter(new ListAdapterr(modifiedList, mActivity));
        grid.scrollToPosition(0);

        searchText = newText;
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        arrayList = new ArrayList<String>();
        String str="0";
        str = sharedPreferences.getString("value", "");
        str = "0";
        Toast.makeText(getContext(), "Value is " + str, Toast.LENGTH_SHORT).show();
        if (str.equals("0")) {
            // this is called if user wants to see the movies by popularity
            Toast.makeText(mActivity , "Loading..Please wait" , Toast.LENGTH_LONG).show();

            jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1 + "popular" + url2, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray array = response.getJSONArray("results");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject c = array.getJSONObject(i);

                                    String str1 = c.getString("poster_path");
                                    String str2 = c.getString("overview");
                                    String str3 = c.getString("release_date");
                                    String str4 = c.getString("original_title");
                                    String str5 = c.getString("vote_average");
                                    String str6 = c.getString("id");

                                    //adding into the database
                                    if (movieDescriptionHandler.present_title(str4 , str1))
                                        movieDescriptionHandler.insert(str6, str4, str1, str3, str5, str2, "0");
                                    arrayList.add(str1);
                                    titeList.add(str4);
                                }

                                //by default arranging the movies by popularity
                                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                                float width = displayMetrics.widthPixels / displayMetrics.density;
                                int spanCount = (int) (width/175.00);

                                grid.setHasFixedSize(true);
                                RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(mActivity,spanCount);
                                grid.setLayoutManager(linearLayoutManager);
                                grid.setAdapter(new ListAdapterr(arrayList, mActivity));
                                //grid.addItemDecoration(new com.example.arpitdec5.popularmovies.utils.DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, toast_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mActivity, toast_error, Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });

            requestQueue.add(jsonObjectRequest1);
        } else if (str.equals("2")) {
            //displays the favorite movies of the user
            arrayList = movieDescriptionHandler.get_favorites();

            //getting the title from the string images
            for(int i=0;i<arrayList.size();i++)
            {
                String title = movieDescriptionHandler.get_title(arrayList.get(i));
                titeList.add(title);
            }

            grid.setHasFixedSize(true);
            RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(mActivity,2);
            grid.setLayoutManager(linearLayoutManager);
            grid.setAdapter(new ListAdapterr(arrayList, mActivity));

        } else {
            //this is called when the user wants to see the movies by top rating
            Toast.makeText(mActivity , "Loading..Please wait" , Toast.LENGTH_LONG).show();

            jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url1 + "top-rated" + url2, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray array = response.getJSONArray("results");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject c = array.getJSONObject(i);

                                    String str1 = c.getString("poster_path");
                                    String str2 = c.getString("overview");
                                    String str3 = c.getString("release_date");
                                    String str4 = c.getString("original_title");
                                    String str5 = c.getString("vote_average");
                                    String str6 = c.getString("id");

                                    //adding into the database
                                    if (movieDescriptionHandler.present_title(str4 , str1))
                                        movieDescriptionHandler.insert(str6, str4, str1, str3, str5, str2, "0");
                                    arrayList.add(str1);
                                    titeList.add(str4);
                                }
                                //by default arranging the movies by popularity
                                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                                float width = displayMetrics.widthPixels / displayMetrics.density;
                                int spanCount = (int) (width/175.00);

                                grid.setHasFixedSize(true);
                                RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(mActivity,spanCount);
                                grid.setLayoutManager(linearLayoutManager);
                                grid.setAdapter(new ListAdapterr(arrayList, mActivity));
                                //grid.addItemDecoration(new com.example.arpitdec5.popularmovies.utils.DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, toast_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mActivity, toast_error, Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });

            requestQueue.add(jsonObjectRequest2);
        }
    }

    //leveraging the cancellation api of volley
    @Override
    public void onStop() {
        super.onStop();
        requestQueue.cancelAll("GET");
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

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imag);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridlayout_item , parent ,false);
            return (new ViewHolder(view));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            String text = arrayList.get(position);

            Picasso.with(mActivity)
                    .load(url3 + text)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .resize(350,350)
                    .into(holder.imageView);

            final String movie_title = movieDescriptionHandler.get_title(arrayList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), MovieDescription.class);
                    intent.putExtra("title", movie_title);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}