package com.framgia.moviedb.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.GroupMovie;
import java.util.List;

public class MovieGroupAdapter
		extends RecyclerView.Adapter<MovieGroupAdapter.ViewHolder> {
	private final Context mContext;
	private List<GroupMovie> mGroupMovies;
	
	public MovieGroupAdapter(Context context, List<GroupMovie> list) {
		if (list != null) {
			mGroupMovies = list;
		}
		mContext = context;
	}
	
	@NonNull @Override
	public MovieGroupAdapter.ViewHolder onCreateViewHolder(@NonNull
			ViewGroup viewGroup, int position) {
		View view = LayoutInflater.from(mContext)
				.inflate(R.layout.item_group_movie, viewGroup, false);
		return new MovieGroupAdapter.ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull
			MovieGroupAdapter.ViewHolder movieGroupViewHolder, int position) {
		movieGroupViewHolder.bindData(mGroupMovies.get(position));
	}
	
	@Override public int getItemCount() {
		return mGroupMovies != null ? mGroupMovies.size() : 0;
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder {
		private final RecyclerView mRecyclerView;
		private TextView mTextGroupLabel;
		private MovieAdapter mMovieAdapter;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			Context context = itemView.getContext();
			mTextGroupLabel = itemView.findViewById(R.id.text_label_group);
			mRecyclerView = itemView.findViewById(R.id.recycle_movie);
			mRecyclerView.setLayoutManager(
					new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
			mRecyclerView.setHasFixedSize(true);
			mMovieAdapter = new MovieAdapter(context);
			mRecyclerView.setAdapter(mMovieAdapter);
		}
		
		public void bindData(GroupMovie groupMovie) {
			mTextGroupLabel.setText(groupMovie.getTitle());
			mMovieAdapter.setMovieData(groupMovie.getMovies());
		}
	}
}
