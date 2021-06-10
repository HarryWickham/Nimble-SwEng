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

/**
 * The PaymentListAdapter class fills the payment screen with text by only changing
 * the text inside the different payment cards. This is done in conjunction with the
 * payment related layout xml files and PaymentActivity. Arrays are defined and used
 * to contain all possible text and image variations of the different membership tiers
 **/

public class PaymentListAdapter extends BaseAdapter {

    private final String[] planSubtitle;
    private final String[] planTier;
    private final int[] planImage;
    private final String[] membershipDetailsText;
    private final MyActionCallback mActionCallback;
    private final String[] selectionButton;
    Context context;


    public PaymentListAdapter(Context context, String[] planSubtitle, String[] planTier,
                              int[] planImage, String[] membershipDetailsText,
                              MyActionCallback actionCallback, String[] selectionButton) {
        this.context = context;
        this.planSubtitle = planSubtitle;
        this.planTier = planTier;
        this.planImage = planImage;
        this.membershipDetailsText = membershipDetailsText;
        mActionCallback = actionCallback;
        this.selectionButton = selectionButton;
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


        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.payment_list_layout, parent, false);

            /**ids from the xml files are associated with the corresponding arrays to link the xml
             * files to this class**/

            viewHolder.planSubtitle = convertView.findViewById(R.id.plan_subtitle);
            viewHolder.planTier = convertView.findViewById(R.id.plan_tier);
            viewHolder.planImage = convertView.findViewById(R.id.plan_image);
            viewHolder.moreLessButton = convertView.findViewById(R.id.more_less_button);
            viewHolder.membershipDetailsTextView =
                    convertView.findViewById(R.id.membership_details_text);
            viewHolder.membershipDetailsLayout = convertView.findViewById(R.id.membership_details);
            viewHolder.selectedPlan = convertView.findViewById(R.id.selection_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.planTier.setText(planTier[position]);

        viewHolder.planSubtitle.setText(planSubtitle[position]);

        viewHolder.planImage.setImageResource(planImage[position]);

        viewHolder.membershipDetailsTextView.setText(membershipDetailsText[position]);

        viewHolder.selectedPlan.setText(selectionButton[position]);

        viewHolder.moreLessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreLessSelector(viewHolder);
            }
        });

        /**Sends back the text of the purchase button that was last clicked by the user**/

        viewHolder.selectedPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionCallback.onActionPerformed(selectedPlan(viewHolder));
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    public String getPlanTierAtPosition(int position) {
        return planTier[position];
    }

    private void moreLessSelector(ViewHolder viewHolder) {
        if (viewHolder.moreLessButton.getText().equals("Less Details")) {
            viewHolder.membershipDetailsLayout.setVisibility(View.GONE);
            viewHolder.moreLessButton.setText("More Details");

        } else if (viewHolder.moreLessButton.getText().equals("More Details")) {
            viewHolder.membershipDetailsLayout.setVisibility(View.VISIBLE);
            viewHolder.moreLessButton.setText("Less Details");
        }
    }

    /**
     * Changes name of selected plan, based on content of the button currently pressed
     **/

    private String selectedPlan(ViewHolder viewHolder) {
        switch (viewHolder.selectedPlan.getText().toString()) {
            case "select this plan (£1.99)":
                return "bronze";
            case "select this plan (£3.99)":
                return "silver";
            case "select this plan (£5.99)":
                return "gold";
        }
        return "error";
    }

    public interface MyActionCallback {
        void onActionPerformed(String position);
    }

    private static class ViewHolder {
        ImageView planImage;
        TextView planSubtitle;
        TextView planTier;
        Button moreLessButton;
        TextView membershipDetailsTextView;
        LinearLayout membershipDetailsLayout;
        Button selectedPlan;
    }
}
