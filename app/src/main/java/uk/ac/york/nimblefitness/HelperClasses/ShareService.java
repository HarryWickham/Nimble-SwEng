package uk.ac.york.nimblefitness.HelperClasses;

import android.content.Intent;

/**
 * A handler to ease the creation of the ShareIntent that allows the user to share content to
 * other apps such as social media or e-mails
 */

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
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Text);
        return (Intent.createChooser(sharingIntent, ShareTitle));
    }
}
