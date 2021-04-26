package uk.ac.york.nimblefitness.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.york.nimblefitness.Adapters.PaymentListAdapter;
import uk.ac.york.nimblefitness.R;

//Importing

public class PaymentActivity extends AppCompatActivity {

    //Runs when page is created (opened by user)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Membership Plan");
        String[] planSubtitle = {"Basic Membership", "Intermediate Membership",
                "Advanced Membership"};
        String[] planTier = {"Bronze", "Silver", "Gold"};
        String[] membershipDetails = {"more details", "more details", "more details"};
        int[] planImage = {R.drawable.bronzerounded,
                R.drawable.silverrounded,
                R.drawable.goldrounded};
    /*int[] detailsIcon = {R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24,
            R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24,
            R.drawable.ic_baseline_keyboard_arrow_down_24,
            R.drawable.ic_baseline_keyboard_arrow_up_24};*/
        String[] moreDetailsButton = {"more details", "more details", "more details"};
        String[] selectionButton = {"select1", "select2", "select3"};


        PaymentListAdapter listAdapter = new PaymentListAdapter(this, planSubtitle, planTier, planImage);
        /**Context context, String [] planSubtitle, String [] planTier, int [] planImage,
         int [] moreDetailsButton, String[] membershipDetails,
         int [] selectionButton**/

        ListView list = findViewById(R.id.expanding_item);

        list.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(list);

    }

    public void planBought(View view) {
        startActivity(new Intent(getApplicationContext(), UserDetailsActivity.class));//takes user to the main page
        finish();
    }


    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}