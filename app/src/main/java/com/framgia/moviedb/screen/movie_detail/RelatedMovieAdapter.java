package com.framgia.moviedb.screen.movie_detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.GlideApp;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class RelatedMovieAdapter extends RecyclerView.Adapter<RelatedMovieAdapter.ViewHolder> {
    public static final int SIZE_IMAGE = 400;
    private Context mContext;
    private List<Movie> mMovies;
    private OnItemClickListener mListener;

    public RelatedMovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        mContext = context;
        if (movies != null) {
            mMovies = movies;
        }
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private ImageView mImageView;
        private OnItemClickListener mListener;
        private Movie mMovie;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            mContext = view.getContext();
            mListener = listener;
            mImageView = view.findViewById(R.id.image_movie);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(mMovie);
        }

        private void bindData(Movie movie) {
            if (movie != null) {
                mMovie = movie;
            }
            String url = APIUtils.PRE_POSTER_URL + movie.getPosterPath();
            GlideApp.with(mContext)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_icon)
                            .error(R.drawable.ic_error_icon)
                            .override(SIZE_IMAGE))
                    .into(mImageView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
