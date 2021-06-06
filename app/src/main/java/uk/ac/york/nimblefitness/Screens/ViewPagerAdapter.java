package uk.ac.york.nimblefitness.Screens;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import uk.ac.york.nimblefitness.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<String> myTitle;
    private List<String> myDescription;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private List<Integer> myImage;



    private int[] colorArray = new int[]{android.R.color.holo_blue_bright, android.R.color.holo_red_light, android.R.color.holo_purple, android.R.color.holo_orange_light};


    ViewPagerAdapter(Context context, List<String> data, ViewPager2 viewPager2, List<Integer> image, List<String> description) {
        this.mInflater = LayoutInflater.from(context);
        this.myTitle = data;
        this.viewPager2 = viewPager2;
        this.myImage =image;
        this.myDescription = description;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String positionData = myTitle.get(position);
        holder.mytitleView.setText(positionData);
        holder.linearLayout.setBackgroundResource(colorArray[position]);
        holder.myimageview.setImageResource(myImage.get(position));
        holder.mydescriptionView.setText(myDescription.get(position));
        holder.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(OnBoadring.this, MainActivity.class);
            }

        });

        if(getItemCount()-1 == position){
            holder.getStarted.setVisibility(View.VISIBLE);
            holder.getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent myIntent = new Intent(OnBoadring.this, MainActivity.class);
                }

            });

        }
    }

    @Override
    public int getItemCount() {
        return myTitle.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mytitleView;
        TextView mydescriptionView;
        LinearLayout linearLayout;
        ImageView myimageview;
        Button getStarted;
        Button skip;


        ViewHolder(View itemView) {
            super(itemView);
            mytitleView = itemView.findViewById(R.id.title);
            mydescriptionView = itemView.findViewById(R.id.description);
            myimageview = itemView.findViewById(R.id.image);
            linearLayout = itemView.findViewById(R.id.container);
            getStarted = itemView.findViewById(R.id.getStarted_button);
            skip = itemView.findViewById(R.id.skip_button);

        }
    }
}