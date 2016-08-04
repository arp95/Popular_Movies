package com.example.arpitdec5.popularmovies;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDescriptionFragment extends Fragment {

    TextView title ,release,vote_average, overview , first_trailor , first_review;
    ImageView imageView , imageView_play_button , imageview_reading;
    Activity mActivity;
    Button trailer_list;
    Button review_list;
    Button favorite;
    ArrayList<String> trailer ;
    ArrayList<String> review;

    String movie_id = "sdsds";
    String movie_title = "sdsdsdsd";
    String movie_image = "jjjjj" ;
    String movie_release = "kkkkk" ;
    String movie_vote = "kkkkk" ;
    String movie_overview = "bbbbb" ;
    String url4 = "https://www.youtube.com/watch?v=";
    String url3 = "/videos?api_key=e9bedc7d4abd75b8283a9734f5bcb6d2";
    String url2 = "/reviews?api_key=e9bedc7d4abd75b8283a9734f5bcb6d2";
    String url1 = "http://api.themoviedb.org/3/movie/";
    String movie_review = null;

    TrailorHandler trailorHandler;
    com.example.arpitdec5.popularmovies.ReviewHandler reviewHandler;
    com.example.arpitdec5.popularmovies.MovieDescriptionHandler movieDescriptionHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public MovieDescriptionFragment(){

    }

    public MovieDescriptionFragment(String str){
        movie_title = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView =  inflater.inflate(R.layout.moviedescription_fragment_main, container, false);

        trailer = new ArrayList<String>();
        review = new ArrayList<String>();

        movieDescriptionHandler = new com.example.arpitdec5.popularmovies.MovieDescriptionHandler(mActivity);
        trailorHandler = new com.example.arpitdec5.popularmovies.TrailorHandler(mActivity);
        reviewHandler = new com.example.arpitdec5.popularmovies.ReviewHandler(mActivity);
        title = (TextView) rootView.findViewById(R.id.e1);
        imageView = (ImageView) rootView.findViewById(R.id.e2);
        release = (TextView) rootView.findViewById(R.id.e3);
        vote_average = (TextView) rootView.findViewById(R.id.e4);
        overview = (TextView) rootView.findViewById(R.id.e5);
        first_trailor = (TextView) rootView.findViewById(R.id.e6);
        imageView_play_button = (ImageView) rootView.findViewById(R.id.e7);
        imageview_reading = (ImageView) rootView.findViewById(R.id.reading);
        first_review = (TextView) rootView.findViewById(R.id.e8);
        trailer_list = (Button) rootView.findViewById(R.id.rev);
        review_list = (Button) rootView.findViewById(R.id.tra);
        favorite = (Button) rootView.findViewById(R.id.favorite);


        setDescription(movie_title);
        MovieApiCaller(url1 + movie_id + url3, 1);

        trailer_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity, Trailors.class);
                intent.putExtra("yolo", movie_title);
                startActivity(intent);
            }
        });

        review_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity , Reviews.class);
                intent.putExtra("yolo" , movie_title);
                startActivity(intent);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                movieDescriptionHandler.mark_fav(movie_title);
                Toast.makeText(mActivity , "Added !!" , Toast.LENGTH_SHORT).show();
            }
        });


        first_review.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(review.size()>0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.get(0)));
                    startActivity(intent);
                }
                else
                    Toast.makeText(mActivity , "Sorry no reviews found !!" , Toast.LENGTH_SHORT).show();
            }
        });

        imageview_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(review.size()>0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.get(0)));
                    startActivity(intent);
                }
                else
                    Toast.makeText(mActivity , "Sorry no reviews found !!" , Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public void setDescription(String string)
    {
        movie_title = string;
        movie_image = movieDescriptionHandler.get_image(movie_title);
        movie_release = movieDescriptionHandler.get_release(movie_title);
        movie_vote = movieDescriptionHandler.get_vote(movie_title);
        movie_overview = movieDescriptionHandler.get_overview(movie_title);
        movie_id = movieDescriptionHandler.get_id(movie_title);

        title.setText(movie_title);
        release.setText(movie_release);
        vote_average.setText(movie_vote + "/10");
        overview.setText(movie_overview);
        Picasso.with(mActivity)
                .load("http://image.tmdb.org/t/p/w185/" + movie_image)
                .placeholder(R.drawable.image4)
                .error(R.drawable.image4)
                .resize(350, 350)
                .into(imageView);

        Picasso.with(mActivity)
                .load(R.drawable.play)
                .placeholder(R.drawable.play)
                .error(R.drawable.play)
                .resize(60, 60)
                .into(imageView_play_button);
    }

    public void updateTitle(String string)
    {
        setDescription(string);
        MovieApiCaller(url1 + movie_id + url3, 1);
    }
    //starts a background thread for api call
    public void MovieApiCaller(String str , int f)
    {
        ConnectivityManager conn = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
        {
            if(f==0)
                new FetchMovieReviewTask().execute(str);
            else
                new FetchMovieTrailerTask().execute(str);
        }
        else
            Toast.makeText(mActivity , "Please switch ON your WIFI !!" , Toast.LENGTH_LONG).show();
    }

    //performing the action to fetch json string from the api through a seperate thread
    public class FetchMovieReviewTask extends AsyncTask<String , Void , String> {

        private final String LOG_TAG = FetchMovieReviewTask.class.getSimpleName();

        //performs the required action to fetch json string
        @Override
        protected String doInBackground(String... params) {
            try {
                String sh = downloadUrl(params[0]);
                return sh;
            } catch (IOException e) {
                Toast.makeText(mActivity , "YO BABY" , Toast.LENGTH_LONG).show();
                return "invalid!!";
            }
        }

        //fetches json string from the inputBuffer
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject c = array.getJSONObject(i);
                        String review_link = c.getString("url");
                        review.add(review_link);

                        if(reviewHandler.is_present(review_link))
                            reviewHandler.insert(movie_title , review_link);
                    }

                    Picasso.with(mActivity)
                            .load(R.drawable.reading)
                            .placeholder(R.drawable.error)
                            .error(R.drawable.error)
                            .resize(60,60)
                            .into(imageview_reading);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity , "Error while taking data!!"  ,Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private String downloadUrl(String myurl) throws IOException
    {
        InputStream is = null;
        String result = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception squish) {
            }
        }
        return result;
    }

    //performing the action to fetch json string from the api through a seperate thread
    public class FetchMovieTrailerTask extends AsyncTask<String , Void , String> {

        private final String LOG_TAG = FetchMovieTrailerTask.class.getSimpleName();

        //performs the required action to fetch json string
        @Override
        protected String doInBackground(String... params) {
            try {
                String sh = downloadUrl(params[0]);
                return sh;
            } catch (IOException e) {
                return "invalid!!";
            }
        }

        //fetches json string from the inputBuffer
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject c = array.getJSONObject(i);
                        String trailor_link = c.getString("key");
                        trailer.add(trailor_link);

                        if(trailorHandler.is_present(trailor_link))
                            trailorHandler.insert(movie_title , trailor_link);
                    }

                    first_trailor.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {

                            if (trailer.size() > 0) {
                                Toast.makeText(mActivity, "Fetching Trailor ..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.get(0)));
                                startActivity(intent);

                            } else
                                Toast.makeText(mActivity, "Sorry no Trailor available", Toast.LENGTH_SHORT).show();

                        }
                    });

                    imageView_play_button.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {

                            if (trailer.size() > 0) {
                                Toast.makeText(mActivity, "Fetching Trailor ..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.get(0)));
                                startActivity(intent);

                            } else
                                Toast.makeText(mActivity, "Sorry no Trailor available", Toast.LENGTH_SHORT).show();

                        }
                    });


                    MovieApiCaller(url1 + movie_id + url2, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity , "Error while taking data!!"  ,Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}