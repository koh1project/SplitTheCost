package io.github.koh1project.SplitTheCost;


import android.os.Bundle;
import android.preference.PreferenceFragment;


public class CalPrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.calprefs);

    }


}
