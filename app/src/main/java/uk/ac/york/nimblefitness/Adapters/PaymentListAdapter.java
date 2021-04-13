package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import uk.ac.york.nimblefitness.HelperClasses.PaymentCard;
import uk.ac.york.nimblefitness.R;
import uk.ac.york.nimblefitness.Screens.PaymentActivity;
import uk.ac.york.nimblefitness.HelperClasses.PaymentCard;

public class PaymentListAdapter extends BaseAdapter {
    
    Context context;
    private String[] planSubtitle;
    private String[] planTier;
    private int[] planImage;
    private String[] membershipDetails;

    

    public PaymentListAdapter(Context context, String[] planSubtitle, String[] planTier, int[] planImage) {
        this.context = context;
        this.planSubtitle = planSubtitle;
        this.planTier = planTier;
        this.planImage = planImage;
        //this.moreDetailsButton = moreDetailsButton;

        //this.selectionButton = selectionButton;
    }

    @Override
    public int getCount() {
        return planTier.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.payment_list_layout, parent, false);

            viewHolder.planSubtitle = convertView.findViewById(R.id.plan_subtitle);
            viewHolder.planTier = convertView.findViewById(R.id.plan_tier);
            viewHolder.planImage = convertView.findViewById(R.id.plan_image);
            viewHolder.moreLessButton = convertView.findViewById(R.id.more_less_button);

            //viewHolder.moreDetailsButton = convertView.findViewById(R.id.more_details_button);
            //viewHolder.selectionButton = convertView.findViewById(R.id.selection_button);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.planTier.setText(planTier[position]);

        viewHolder.planSubtitle.setText(planSubtitle[position]);

        viewHolder.planImage.setImageResource(planImage[position]);



        //viewHolder.selectionButton.setButton(selectionButton[position]);
        //viewHolder.buttonIcon.setImageResource(buttonIcon[position]);

        //View finalConvertView = convertView;

        viewHolder.moreLessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreLessSelector(viewHolder);
            }
        });

        return convertView;
    }

    public String getPlanTierAtPosition(int position){
        return planTier[position];
    }

    private static class ViewHolder {
        ImageView planImage;
        TextView planSubtitle;
        TextView planTier;
        Button moreLessButton;
        LinearLayout moreDetailsButton;
        LinearLayout selectionButton;
        //ImageView buttonIcon;
    }

    private void moreLessSelector(ViewHolder viewHolder){
        if(viewHolder.moreLessButton.getVisibility() == View.VISIBLE){
            viewHolder.moreLessButton.setVisibility(View.GONE);
            viewHolder.moreLessButton.setText("More Details");
            //viewHolder.txtMoves.setCompoundDrawables(null,null, get , null);

        }else if(viewHolder.moreLessButton.getVisibility() == View.GONE){
            viewHolder.moreLessButton.setVisibility(View.VISIBLE);
            viewHolder.moreLessButton.setText("Less Details");
            //viewHolder.txtMoves.setCompoundDrawables(null,null, convertView.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24) , null);
        }
    }
}
