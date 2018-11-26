package com.framgia.moviedb.screen.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.utils.APIUtils;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private static final int INTERVAL_TIME = 200;
    private static final int SIZE_VIEW_FLIPPER = 500;

    private ViewFlipper mViewFlipper;
    private MovieGroupAdapter mGroupAdapter;
    private HomePresenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        loadMovieGroup();
    }

    @Override public void showMovies(List<GroupMovie> groupMovies) {
        mGroupAdapter = new MovieGroupAdapter(this, groupMovies);
        mRecyclerView.setAdapter(mGroupAdapter);
        mGroupAdapter.notifyDataSetChanged();
    }

    @Override public void showMoviesError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, getString(R.string.error_show_image_home),
                Toast.LENGTH_SHORT).show();
    }

    @Override public void showViewFlipper(List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = APIUtils.PRE_POSTER_URL + movies.get(i).getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_loading_icon)
                            .error(R.drawable.ic_error_icon)
                            .override(SIZE_VIEW_FLIPPER))
                    .into(imageView);
            mViewFlipper.addView(imageView);
        }
        mViewFlipper.setFlipInterval(INTERVAL_TIME);
        mViewFlipper.setAutoStart(true);
    }

    @Override public void showViewFlipperError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, getString(R.string.error_show_image_home),
                Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycle_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mViewFlipper = findViewById(R.id.view_flipper);
    }

    private void loadMovieGroup() {
        mPresenter = new HomePresenter(this,
                MoviesRepository.getInstance(MovieRemoteDataSource.getInstance()));
        mPresenter.loadMovies();
    }
}
