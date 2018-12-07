package com.framgia.moviedb.screen.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Cast;
import com.framgia.moviedb.data.model.Crew;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.screen.person.PersonActivity;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener,
        MovieDetailContract.View, RelatedMovieAdapter.OnItemClickListener, ActorAdapter.OnActorClickListener {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private static final int SIZE_VIEW_FLIPPER = 500;
    private Toolbar mToolbar;
    private String mTrailerKey;
    private RelatedMovieAdapter mMovieAdapter;
    private ActorAdapter mActorAdapter;
    private RecyclerView mMovieRecyclerView;
    private RecyclerView mActorRecyclerView;
    private Movie mMovie;
    private MovieDetailPresenter mPresenter;
    private ImageView mImageBackdrop;
    private ImageView mImagePlayIcon;
    private TextView mTextMovieTitle;
    private TextView mTextGenresName;
    private TextView mTextReleaseDate;
    private TextView mTextDirectorName;
    private TextView mTextOverview;
    private TextView mTextRelatedLabel;
    private int mDirectorId;


    public static Intent getIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initView();
        initToolbar();
        displayInfo();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showInfoError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, getString(R.string.error_show_movie_detail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReleaseDate(String releaseDate) {
        mTextReleaseDate.setText(releaseDate);
    }

    @Override
    public void showDirectorName(Crew director) {
        mTextDirectorName.setText(director.getName());
        mDirectorId = director.getId();
    }

    @Override
    public void showActorsName(List<Cast> casts) {
        mActorAdapter = new ActorAdapter(this, casts.subList(0, 2), this);
        mActorRecyclerView.setAdapter(mActorAdapter);
        mActorAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRelatedMovies(List<Movie> movies) {
        mMovieAdapter = new RelatedMovieAdapter(this, movies, this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGenre(String genres) {
        mTextGenresName.setText(genres);
    }

    @Override
    public void showTrailer(String trailerKey) {
        mTrailerKey = trailerKey;
        mImageBackdrop.setScaleType(ImageView.ScaleType.FIT_XY);
        String url = APIUtils.PRE_POSTER_URL + mMovie.getBackdropPath();
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading_icon)
                        .error(R.drawable.ic_error_icon)
                        .override(SIZE_VIEW_FLIPPER))
                .into(mImageBackdrop);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_play_icon:
                startActivity(TrailerActivity.getIntent(this, mTrailerKey));
                break;
            case R.id.text_label_related:

                break;
            case R.id.text_name_director:
                startActivity(PersonActivity.getIntent(this, mDirectorId));
                break;
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }

    @Override
    public void onActorClick(int personId) {
        startActivity(PersonActivity.getIntent(this, personId));
    }

    private void displayInfo() {
        mMovie = new Movie();
        Intent intent = getIntent();
        mMovie = intent.getExtras().getParcelable(EXTRA_MOVIE);
        mTextMovieTitle.setText(mMovie.getTitle());
        mTextOverview.setText(mMovie.getOverview());
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_back_icon);
    }

    private void initView() {
        mMovieRecyclerView = findViewById(R.id.recycle_related);
        mMovieRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMovieRecyclerView.setHasFixedSize(true);
        mActorRecyclerView = findViewById(R.id.recycle_actors);
        mActorRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mActorRecyclerView.setHasFixedSize(true);
        mTextGenresName = findViewById(R.id.text_genres);
        mTextReleaseDate = findViewById(R.id.text_year);
        mTextOverview = findViewById(R.id.text_overview);
        mTextMovieTitle = findViewById(R.id.text_movie_title);
        mTextDirectorName = findViewById(R.id.text_name_director);
        mTextDirectorName.setOnClickListener(this);
        mImageBackdrop = findViewById(R.id.image_trailer);
        mImagePlayIcon = findViewById(R.id.image_play_icon);
        mImagePlayIcon.setOnClickListener(this);
        mTextRelatedLabel = findViewById(R.id.text_label_related);
        mTextRelatedLabel.setOnClickListener(this);
    }

    private void getData() {
        mPresenter = new MovieDetailPresenter(this,
                MoviesRepository.getInstance(MovieRemoteDataSource.getInstance()));
        mPresenter.getMoreInfo(mMovie.getId());
    }
}
