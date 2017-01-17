package com.murraystudio.cmbmeettheteam;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by sushi_000 on 1/16/2017.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {

    private List<TeamMember> teamMembersList;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public boolean bioVisible;
        public int rotationAngle = 0;

        public ImageView mExpandArrow;

        public CardView mCardView;
        public TextView mFullNameView;
        public TextView mTitleView;
        public TextView mBioView;
        public ImageView mAvatarImage;
        public MyViewHolder(View v) {
            super(v);

            mExpandArrow = (ImageView) v.findViewById(R.id.expand_arrow);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mFullNameView = (TextView) v.findViewById(R.id.full_name);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mBioView = (TextView) v.findViewById(R.id.bio);
            mAvatarImage = (ImageView) v.findViewById(R.id.avatar);

            bioVisible = false;

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(bioVisible == false) {
                        mBioView.setVisibility(View.VISIBLE);
                        bioVisible = true;
                    }
                    else{
                        mBioView.setVisibility(View.GONE);
                        bioVisible = false;
                    }

                    ObjectAnimator anim = ObjectAnimator.ofFloat(mExpandArrow, "rotation",rotationAngle, rotationAngle + 180);
                    anim.setDuration(300);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle%360;

                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamAdapter(List<TeamMember> teamMembersList, Activity activity) {
        this.teamMembersList = teamMembersList;
        this.activity = activity;
    }

    @Override
    public TeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_members_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TeamAdapter.MyViewHolder holder, int position) {
        holder.mFullNameView.setText(teamMembersList.get(position).getFirstName() + " " + teamMembersList.get(position).getLastName());
        holder.mTitleView.setText(teamMembersList.get(position).getTitle());
        holder.mBioView.setText(teamMembersList.get(position).getBio());

        String mediaURL = teamMembersList.get(position).getAvatarURL();

        Glide.with(activity)
                .load(mediaURL)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.mAvatarImage);
    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

}
