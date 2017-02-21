package com.example.light.implicitintent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:5551234");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Map point based on address
                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                // Or map point based on latitude/longitude
                // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                // Verify it resolves
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                boolean isIntentSafe = activities.size() > 0;

                String title = getResources().getString(R.string.chooser_map);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(mapIntent, title);

                // Start an activity if it's safe
                if (isIntentSafe) {
                    startActivity(chooser);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("http://www.android.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                String title = getResources().getString(R.string.chooser_web);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(webIntent, title);

                // Verify the intent will resolve to at least one activity
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // The intent does not have a URI, so declare the "text/plain" MIME type
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                // You can also attach multiple items by passing an ArrayList of Uris

                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                String title = getResources().getString(R.string.chooser_postman);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(emailIntent, title);

                // Verify the intent will resolve to at least one activity
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                beginTime.set(2012, 0, 19, 7, 30);
                endTime.set(2012, 0, 19, 10, 30);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
                startActivity(calendarIntent);
            }
        });

    }
}
