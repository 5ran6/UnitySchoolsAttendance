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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Objects;

import gamint.com.unityschoolsattendance.utils.CheckNetworkConnection;
import gamint.com.unityschoolsattendance.utils.Tools;

import static gamint.com.unityschoolsattendance.Splash.myPref;

public class Attendance_menu extends AppCompatActivity {
    private ProgressBar progressBar1;
    private TextView date;
    private TextView load;
    private BroadcastReceiver mReceiver;
    private int w = 0, status;
    private AsyncTask lastThread;

    private void initComponent() {
        (findViewById(R.id.pick_date)).setOnClickListener(this::dialogDatePickerLight);
    }

    private void dialogDatePickerLight(final View bt) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    long date_ship_millis = calendar.getTimeInMillis();
                    date.setText(Tools.getFormattedDate(date_ship_millis));
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_menu);

        SharedPreferences mPrefs = Objects.requireNonNull(getSharedPreferences(myPref, 0));
        //school_id/staff id from sharedPrefs


        //   Toast.makeText(this, staff_id, Toast.LENGTH_SHORT).show();
        initComponent();
        load = findViewById(R.id.load);
        progressBar1 = findViewById(R.id.progress1);
        progressBar1.setVisibility(View.VISIBLE);
        date = findViewById(R.id.date);


        load.setOnClickListener(v -> {
//            if (!class_name.equals("") && !date.getText().equals("") && !arm.equals("")) {
//                if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
//                    Intent intent1 = new Intent(getBaseContext(), AttendanceActivity.class);
//                    intent1.putExtra("school_id", school_id);
//                    intent1.putExtra("class_name", class_name);
//                    intent1.putExtra("arm", arm);
//                    intent1.putExtra("date", date.getText().toString().trim());
//                    startActivity(intent1);
//                } else {
//                    Tools.toast(getResources().getString(R.string.no_internet_connection), this, R.color.red_700);
//                }
//            } else {
//                Tools.toast("Fill all necessary fields", Attendance_menu.this, R.color.yellow_900);
//            }
        });
    }


    @Override
    protected void onResume() {
        this.mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                w++;
                new CheckNetworkConnection(context, new CheckNetworkConnection.OnConnectionCallback() {
                    @Override
                    public void onConnectionSuccess() {
                        status = 1;
                        if (w > 1)
                            Tools.toast("Back Online! Try again", Attendance_menu.this, R.color.green_800);
                        else //load classes and assessments
                        {
                            //  lastThread = new loadClass().execute();
                        }
                    }

                    @Override
                    public void onConnectionFail(String errorMsg) {
                        status = 0;
                        if (w > 1) {
                            try {
                                Tools.toast(getResources().getString(R.string.no_internet_connection), Attendance_menu.this, R.color.red_900);
                                lastThread.cancel(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Tools.toast(getResources().getString(R.string.no_internet_connection), Attendance_menu.this, R.color.red_900);
                        }
                    }
                }).execute();
            }

        };

        registerReceiver(
                this.mReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(this.mReceiver);
        w = 0;
        super.onPause();
    }
}