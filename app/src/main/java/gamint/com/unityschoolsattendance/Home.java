package gamint.com.unityschoolsattendance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import gamint.com.unityschoolsattendance.utils.Tools;


public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Tools.toast("Press again to exit app", Home.this);
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }

}