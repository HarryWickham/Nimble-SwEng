package uk.ac.york.nimblefitness.HelperClasses;

import android.content.Intent;
import android.util.Log;

public class ShareService {
    String Subject;
    String Text;
    String ShareTitle;

    public ShareService(String subject, String text, String shareTitle) {
        Subject = subject;
        Text = text;
        ShareTitle = shareTitle;
    }

    public Intent ShareContent() {
        Log.i("TAG", "onHandleIntent: ");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Text);
        return (Intent.createChooser(sharingIntent, ShareTitle));
    }
}
