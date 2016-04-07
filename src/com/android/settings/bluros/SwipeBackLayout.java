/* 
 *  Create by Linh Phi
 * */
package com.android.settings.bluros;

import android.app.INotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.SwitchPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SwipeBackLayout extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String TAG = "Swipe Back Layout";
    private static final String KEY_ENABLE_GESTURE = "swipe_back_layout_enable";
    private static final String KEY_GESTURE_EDGE = "swipe_back_layout_edge";

    private ListPreference mGestureEdge;
    private SwitchPreference mEnableGesture;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        addPreferencesFromResource(R.xml.swipe_back_layout_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mEnableGesture = (SwitchPreference) prefSet.findPreference(KEY_ENABLE_GESTURE);
        mEnableGesture.setChecked(Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.SWIPE_BACK_GESTURE_ENABLED, 0) == 1);

        mGestureEdge = (ListPreference) prefSet.findPreference(KEY_GESTURE_EDGE);
        try {
            int edge = Settings.System.getInt(mContext.getContentResolver(),
                    Settings.System.SWIPE_BACK_GESTURE_EDGE, 0);
            mGestureEdge.setValue(String.valueOf(edge));  
        } catch(Exception ex) {
            // So what
        }
        mGestureEdge.setOnPreferenceChangeListener(this);
    }
      protected int getMetricsCategory()
			{
				return MetricsLogger.APPLICATION;
			}

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mEnableGesture) {	
            Settings.System.putInt(mContext.getContentResolver(),
                    Settings.System.SWIPE_BACK_GESTURE_ENABLED, mEnableGesture.isChecked()
                    ? 1 : 0);	
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mGestureEdge) {
            int edge = Integer.parseInt((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.SWIPE_BACK_GESTURE_EDGE, edge);
            return true;
        }
        return false;
    }
}
