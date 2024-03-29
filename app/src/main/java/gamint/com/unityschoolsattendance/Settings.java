/*
 * Copyright 2019 Mountedwings Cybersystems LTD. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gamint.com.unityschoolsattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import gamint.com.unityschoolsattendance.utils.Tools;

import static gamint.com.unityschoolsattendance.Splash.myPref;

public class Settings extends AppCompatActivity {
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        TextView email = findViewById(R.id.email);

        if (getSharedPreferences(myPref, PREFERENCE_MODE_PRIVATE).toString() != null) {
            SharedPreferences mPrefs = getSharedPreferences(myPref, PREFERENCE_MODE_PRIVATE);
            if (mPrefs.getString("email_address", getIntent().getStringExtra("email_address")).equalsIgnoreCase("admin")) {
                email.setTextColor(getResources().getColor(R.color.green_700));
                email.setText(mPrefs.getString("email_address", getIntent().getStringExtra("email_address")).toUpperCase());
            } else {
                email.setText(mPrefs.getString("email_address", getIntent().getStringExtra("email_address")));
            }

            String raw_pass = mPrefs.getString("pass", Arrays.toString(getIntent().getByteArrayExtra("pass")));
            try {
                byte[] pass = Base64.decode(raw_pass, Base64.NO_WRAP);

                CircleImageView passport = findViewById(R.id.passport);

                Bitmap bitmap = BitmapFactory.decodeByteArray(pass, 0, pass.length);
                passport.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Tools.toast("Previous Login invalidated. Login again!", Settings.this, R.color.red_600);
            //clear mPrefs
            clearSharedPreferences(this);
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
    }

    //DONE
    public void app_info(View view) {
        startActivity(new Intent(getApplicationContext(), About.class));
    }

    //DONE
    public void privacy_policy(View view) {
        //go to website from browser
        String uri = "http://mountedwingscs.com/mskola_privacy_policy.php";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(uri));
        startActivity(i);
    }

    public void get_help(View view) {
        //go to website from browser
        String uri = "http://mountedwingscs.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(uri));
        startActivity(i);
    }

    public void feedback(View view) {
        startActivity(new Intent(getApplicationContext(), DialogAddReview.class));
    }

    //DONE
    public void invite(View view) {
        shareText(getResources().getString(R.string.invite_a_friend));
    }

    //DONE
    public void logout(View view) {
        clearSharedPreferences(Settings.this);
        Tools.toast("Logged out", Settings.this, R.color.green_600);
        finish();
        startActivity(new Intent(getApplicationContext(), Home.class));

    }

    public static void clearSharedPreferences(Context ctx) {
        ctx.getSharedPreferences("usa", 0).edit().clear().apply();
    }

    public void shareText(String text) {
        String mimeType = "text/plain";
        String title = "Unity Schools Attendance";

        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(text)
                .getIntent();
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }
}