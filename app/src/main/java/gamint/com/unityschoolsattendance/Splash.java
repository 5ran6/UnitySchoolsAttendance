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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;


public class Splash extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    public static final int PREFRENCE_MODE_PRIVATE = 0;
    private Boolean singedIn, firstTime;
    public static final String myPref = "usa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSharedPreferences(myPref, PREFRENCE_MODE_PRIVATE).toString() != null) {
            SharedPreferences mPrefs = getSharedPreferences(myPref, PREFRENCE_MODE_PRIVATE);
            singedIn = mPrefs.getBoolean("signed_in", false);
            firstTime = mPrefs.getBoolean("firstTime", true);

        } else {
            //create SharedPref
        }
        ImageView img = findViewById(R.id.img);
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        final Transition transition = new Transition() {
            @Override
            public void captureEndValues(@NonNull TransitionValues transitionValues) {

            }

            @Override
            public void captureStartValues(@NonNull TransitionValues transitionValues) {

            }
        };
        animation.setDuration(2000); // duration - 2 seconds
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        img.startAnimation(animation);

        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            // intent

            //   Toast.makeText(this, String.valueOf(singedIn), Toast.LENGTH_SHORT).show();
            if (firstTime) {
                //go to unboarding
                finish();
                startActivity(new Intent(getApplicationContext(), Unboarding.class));
            }
            if (singedIn) {
                //Go to dashboard;
                finish();
                startActivity(new Intent(getApplicationContext(), Home.class));
            } else {
                //initial Launch
                finish();
                startActivity(new Intent(getApplicationContext(), Unboarding.class));
            }
        }, 4000); // 4000 milliseconds delay
    }
}