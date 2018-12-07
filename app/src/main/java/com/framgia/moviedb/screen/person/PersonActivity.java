package com.framgia.moviedb.screen.person;

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
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.Person;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.repository.PersonRepository;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.data.source.remote.PersonRemoteDataSource;
import com.framgia.moviedb.screen.more.MoreMovieActivity;
import com.framgia.moviedb.screen.movie_detail.MovieDetailActivity;
import com.framgia.moviedb.screen.movie_detail.RelatedMovieAdapter;
import com.framgia.moviedb.utils.APIUtils;

import java.util.List;

public class PersonActivity extends AppCompatActivity implements PersonContract.View,
        View.OnClickListener, RelatedMovieAdapter.OnItemClickListener {
    private static final String EXTRA_PERSON_ID = "EXTRA_PERSON_ID";
    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int SIZE_PROFILE_IMAGE = 500;
    private TextView mTextName;
    private TextView mTextBirthday;
    private TextView mTextPlaceOfBirth;
    private TextView mTextBiography;
    private ImageView mImageProfile;
    private RecyclerView mRecyclerView;
    private int mId;

    public static Intent getIntent(Context context, int personId) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initToolbar();
        getPersonData();
        initView();
    }

    @Override
    public void showInfo(Person person) {
        mTextName.setText(person.getName());
        mTextPlaceOfBirth.setText(person.getPlaceOfBirth());
        mTextBirthday.setText(person.getBirthday());
        mTextBiography.setText(person.getBiography());
        String url = APIUtils.PRE_POSTER_URL + person.getProfilePath();
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading_icon)
                        .error(R.drawable.ic_error_icon)
                        .override(SIZE_PROFILE_IMAGE))
                .into(mImageProfile);
    }

    @Override
    public void showInfoError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, R.string.error_show_movie_detail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRelatedMovie(List<Movie> movies) {
        RelatedMovieAdapter adapter = new RelatedMovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyItemRangeChanged(0, movies.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_label_related:
                MoreMovieActivity.getIntent(this, mId);
                break;
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mTextBiography = findViewById(R.id.text_biography);
        mTextBirthday = findViewById(R.id.text_dob);
        mTextName = findViewById(R.id.text_person_name);
        mTextPlaceOfBirth = findViewById(R.id.text_place);
        mImageProfile = findViewById(R.id.image_profile);
        mRecyclerView = findViewById(R.id.recycle_related_by_person);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
    }

    private void getPersonData() {
        Intent intent = getIntent();
        mId = intent.getExtras().getInt(EXTRA_PERSON_ID);
        PersonContract.Presenter presenter = new PersonPresenter(this,
                PersonRepository.getInstance(PersonRemoteDataSource.getInstance()),
                MoviesRepository.getInstance(MovieRemoteDataSource.getInstance()));
        presenter.getPersonInfo(mId);
        presenter.getMovieByPersonId(mId, FIRST_PAGE_NUMBER);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
    }
}
