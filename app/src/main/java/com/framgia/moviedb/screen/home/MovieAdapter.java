package com.framgia.moviedb.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.GlideModuleApp;
import com.framgia.moviedb.GlideApp;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.utils.APIUtils;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MovieAdapter(Context context) {
        mContext = context;
    }

    public void setMovieData(List<Movie> movies) {
        if (movies != null) {
            mMovies = movies;
            notifyDataSetChanged();
        }
    }

    @NonNull @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        View view = mLayoutInflater.inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(mContext, view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindData(mMovies.get(position));
    }

    @Override public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private Context mContext;

        public ViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            mContext = context;
            mImageView = itemView.findViewById(R.id.image_movie);
        }

        public void bindData(Movie movie) {
            if (movie == null) {
                return;
            }
            String url = APIUtils.PRE_POSTER_URL + movie.getPosterPath();
            GlideApp.with(mContext)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_icon)
                            .error(R.drawable.ic_error_icon)
                            .override(700, 300))
                    .into(mImageView);
        }
    }
}
