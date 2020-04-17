package gamint.com.unityschoolsattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

import gamint.com.unityschoolsattendance.utils.Tools;

public class Login extends AppCompatActivity {
    private AppCompatEditText bip;
    private String token = "";
    private String bippiis, getBippiis, extra;
    private ProgressBar progressBar;
    private AppCompatImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        extra = getIntent().getStringExtra("extra");
        bip = findViewById(R.id.bippiis_no);

//        //to be removed
//        bip.setText("O/S 2243");

        progressBar = findViewById(R.id.progress);
        img = findViewById(R.id.img);
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        new Transition() {
            @Override
            public void captureEndValues(@NonNull TransitionValues transitionValues) {

            }

            @Override
            public void captureStartValues(@NonNull TransitionValues transitionValues) {

            }
        };
        animation.setDuration(2000); // duration - 2 seconds
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(3); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        img.startAnimation(animation);

        bip.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideSoftKeyboard();
                Toast.makeText(getApplicationContext(), "Logging in..", Toast.LENGTH_SHORT).show();
                //   login();
                handled = true;
            }
            return handled;

        });

    }

    //DONE
    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void proceed(View view) {
        //login();
    }

//    private void login() {
//        bippiis = bip.getText().toString().trim();
//
//        if (bippiis.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "BIPPIIS NO CANNOT BE EMPTY!", Toast.LENGTH_SHORT).show();
//        } else {
//            getBippiis = bip.getText().toString().trim();
//
//            //retrofit_auth and get token
//            progressBar.setVisibility(View.VISIBLE);
//
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request newRequest = chain.request().newBuilder()
//                            .addHeader("Authorization", "Bearer " + token)
//                            .build();
//                    return chain.proceed(newRequest);
//                }
//            }).build();
//
//            Retrofit retrofit = new Retrofit.Builder().client(client)
//                    .baseUrl(getString(R.string.base_url))
//                    .addConverterFactory(GsonConverterFactory.create()).build();
//            BIPPIIS service = retrofit.create(BIPPIIS.class);
//
//            LoginRequest loginRequest = new LoginRequest();
//            loginRequest.setBippiis_number(Objects.requireNonNull(bip.getText()).toString().trim());
//
//            Call<ResponseBody> ResponseBodyCall = service.getLoginResponse(loginRequest);
//            ResponseBodyCall.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    // TODO: still need to catch errors properly from accurate response filters
//                    progressBar.setVisibility(View.GONE);
//                    Log.d("fingerprint", "Response Code: " + response.code());
//
//                    //validate response
//                    try {
//                        if (response.code() == 201) {
//                            InputStream inputStr = response.body().byteStream();
//
//                            String jsonResponse = "";
//                            boolean enrolled = false;
//
//                            try {
//                                jsonResponse = IOUtils.toString(inputStr, "UTF-8");
//                                Log.d("fingerprint", "" + jsonResponse);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (!jsonResponse.isEmpty()) {
//
//                                JSONParser parse = new JSONParser();
//                                JSONObject jobj = null;
//                                try {
//                                    jobj = (JSONObject) parse.parse(jsonResponse);
//
//                                    JSONObject jsonobj_1 = (JSONObject) jobj.get("data");
//
//                                    String fullname = (String) jsonobj_1.get("fullname");
//
//                                    enrolled = (Boolean) jsonobj_1.get("has_enrolled");
//                                    Log.d("fingerprint", "has enroll: " + enrolled);
//                                    bippiis = bippiis.replace("/", "_");
//                                    bippiis = bippiis.replace("-", "_");
//                                    bippiis = bippiis.replace(" ", "_").toUpperCase();
//
//                                    if (extra.equalsIgnoreCase("enroll")) {
//                                        if (!enrolled) {
//                                            Log.d("fingerprint", "Coming from Original BIPPIIS : " + getBippiis + " edited BIPPIIS: " + bippiis + " token : " + token);
//                                            startActivity(new Intent(getApplicationContext(), BioMiniMain.class).putExtra("bippiis_number", getBippiis).putExtra("bippiis_number_edited", bippiis).putExtra("token", token).putExtra("fullname", fullname));
//                                        } else {
//                                            // go to login then camera
//                                            Toast.makeText(getApplicationContext(), "Has been enrolled", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                    if (extra.equalsIgnoreCase("verify"))
//                                        startActivity(new Intent(getApplicationContext(), Verify.class).putExtra("bippiis_number", getBippiis).putExtra("bippiis_number_edited", bippiis).putExtra("token", token).putExtra("fullname", fullname));
//                                    if (extra.equalsIgnoreCase("profile"))
//                                        startActivity(new Intent(getApplicationContext(), LoginFingerprint.class).putExtra("bippiis_number", getBippiis).putExtra("bippiis_number_edited", bippiis).putExtra("token", token).putExtra("fullname", fullname));
//
//                                } catch (ParseException | NullPointerException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                Log.d("fingerprint", "Response RAW: " + response.raw());
//
//                                Toast.makeText(getApplicationContext(), "INVALID BIPPIIS NUMBER", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            //something went wrong. Try again.
//                            Toast.makeText(getApplicationContext(), "INVALID BIPPIIS NUMBER. Try again", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (NullPointerException n) {
//                        n.printStackTrace();
//
//                        Toast.makeText(getApplicationContext(), "INVALID BIPPIIS NUMBER", Toast.LENGTH_SHORT).show();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
//
//                    Log.d("fingerprint", "Failed to Verify: " + t.getLocalizedMessage());
//
//                }
//            });
//        }
//    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Tools.toast("Press again to go back", Login.this);
            exitTime = System.currentTimeMillis();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    @Override
    public void onBackPressed() {
        doExitApp();
    }
}
