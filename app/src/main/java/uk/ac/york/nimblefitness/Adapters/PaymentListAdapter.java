package uk.ac.york.nimblefitness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import uk.ac.york.nimblefitness.R;

public class PaymentListAdapter extends BaseAdapter {
    Context context;
    private String[] planTitle;
    private String[] planTier;
    private String[] buttonText;
    private int[] planImage;
    private int[] buttonIcon;

    private boolean visible = false;

    public PaymentListAdapter(Context context, String [] planTitle, String [] planTier,
                            String[] buttonText, int [] planImage) {
        this.context = context;
        this.planTitle = planTitle;
        this.planTier = planTier;
        this.buttonText = buttonText;
        this.planImage = planImage;
        //this.buttonIcon = buttonIcon;
    }

    @Override
    public int getCount() {
        return planTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.moves_list_layout, parent, false);

            /*Add these after renaming the IDs of the xml parts to something similar
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.plan_title);
            viewHolder.txtDetails = (TextView) convertView.findViewById(R.id.plan_description);
            viewHolder.txtMoves = convertView.findViewById(R.id.more_details_button);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.plan_image);
            viewHolder.membership_more_details = convertView.findViewById(R.id.membership_more_details);*/

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(planTitle[position]);
        viewHolder.txtDetails.setText(planTier[position]);
        viewHolder.txtMoves.setText(buttonText[position]);
        viewHolder.icon.setImageResource(planImage[position]);
        //viewHolder.buttonIcon.setImageResource(buttonIcon[position]);

        View finalConvertView = convertView;
        viewHolder.txtMoves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreLessSelector(viewHolder);
            }
        });

        return convertView;
    }

    public String getExerciseTitleAtPosition(int position){
        return planTitle[position];
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtDetails;
        Button txtMoves;
        ImageView icon;
        LinearLayout membership_more_details;
        //ImageView buttonIcon;
    }

    private void moreLessSelector(ViewHolder viewHolder){
        if(visible){
            viewHolder.membership_more_details.setVisibility(View.GONE);

            viewHolder.txtMoves.setText("More Details");
            //viewHolder.txtMoves.setCompoundDrawables(null,null, get , null);
            visible = false;
        }else{
            viewHolder.membership_more_details.setVisibility(View.VISIBLE);
            viewHolder.txtMoves.setText("Less Details");
            //viewHolder.txtMoves.setCompoundDrawables(null,null, convertView.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24) , null);
            visible = true;
        }
    }
}
