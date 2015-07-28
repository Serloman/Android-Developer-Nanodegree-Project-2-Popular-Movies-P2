package com.serloman.popularmovies.cast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.models.CastMovie;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 28/07/2015.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder>{

    private Context mContext;
    private List<CastMovie> mCastMembers;

    public CastAdapter(Context context){
        this(context, new ArrayList<CastMovie>());
    }

    public CastAdapter(Context context, List<CastMovie> castMembers){
        this.mContext = context;
        this.mCastMembers = castMembers;
    }

    @Override
    public CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.single_cast_view, parent, false);

        return new CastHolder(mContext, rootView);
    }

    @Override
    public void onBindViewHolder(CastHolder holder, int position) {
        CastMovie cast = this.mCastMembers.get(position);

        holder.updateCast(cast);
    }

    @Override
    public int getItemCount() {
        return mCastMembers.size();
    }

    public void addMoreCastMembers(List<CastMovie> castMembers){
        for(CastMovie member : castMembers)
            mCastMembers.add(member);

        notifyDataSetChanged();
    }

    public static class CastHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private ImageView profileImageView;
        private TextView nameTextView;

        public CastHolder(Context context, View itemView) {
            super(itemView);

            profileImageView = (ImageView) itemView.findViewById(R.id.castProfile);
            nameTextView = (TextView) itemView.findViewById(R.id.castName);
        }

        private void updateCast(CastMovie castMember){
            Picasso.with(mContext).load(castMember.getUrlPhoto(ImageMovie.Sizes.w154)).into(profileImageView);
            nameTextView.setText(castMember.getName());
        }
    }
}
