package uk.ac.york.nimblefitness.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.MainActivity;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<String> myTitle;
    private List<String> myDescription;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private List<Integer> myImage;
    private Context context;


    //Array of background colours
    private final int[] colorArray = new int[]{android.R.color.holo_blue_bright,
            android.R.color.holo_red_light, android.R.color.holo_purple,
            android.R.color.holo_orange_light};


    public ViewPagerAdapter(Context context, List<String> data, ViewPager2 viewPager2,
                            List<Integer> image, List<String> description) {
        this.mInflater = LayoutInflater.from(context);
        this.myTitle = data;
        this.viewPager2 = viewPager2;
        this.myImage =image;
        this.myDescription = description;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirebaseDatabase rootDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference rootReference = rootDatabase.getReference("users").
                child(currentFirebaseUser.getUid()).child("userDetails");

        //Adds all the elemets from the lists in onboarding screen class to each page
        String positionData = myTitle.get(position);
        holder.mytitleView.setText(positionData);
        holder.linearLayout.setBackgroundResource(colorArray[position]);
        holder.myimageview.setImageResource(myImage.get(position));
        holder.mydescriptionView.setText(myDescription.get(position));

        holder.skip.setOnClickListener(new View.OnClickListener() {
            //makes sure that user only sees this screen the first time they log in
            @Override
            public void onClick(View v) {
                Intent skipIntent = new Intent(context, MainActivity.class);
                context.startActivity(skipIntent);
                rootReference.child("onBoarded").setValue(true);
                ((Activity)context).finish();
            }

        });

        //This makes sure that the get started button is only displayed on the last page
        if(getItemCount()-1 == position){
            holder.getStarted.setVisibility(View.VISIBLE);
            holder.getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent getStartedIntent = new Intent(context, MainActivity.class);
                    context.startActivity(getStartedIntent);
                    rootReference.child("onBoarded").setValue(true);
                    ((Activity)context).finish();
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
