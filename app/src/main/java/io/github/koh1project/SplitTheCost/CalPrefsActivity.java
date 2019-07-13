package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.os.Bundle;

public class CalPrefsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new CalPrefsFragment())
                .commit();

    }
}
