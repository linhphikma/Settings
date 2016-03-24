/*
* Copyright (C) 2016 RR
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.android.settings.rr;


import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.TwoStatePreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.util.Helpers;
import org.cyanogenmod.internal.util.CmLockPatternUtils;
import com.android.settings.Utils;
import android.text.format.DateFormat;
import android.telephony.TelephonyManager;
import android.provider.SearchIndexableResource;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import cyanogenmod.providers.CMSettings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class NotificationDrawerSettings extends SettingsPreferenceFragment  implements Preference.OnPreferenceChangeListener, Indexable{
	
    	
    private static final int MY_USER_ID = UserHandle.myUserId();

// BlurOS

    private TwoStatePreference mExpand;
    private TwoStatePreference mNotiTrans;
    private TwoStatePreference mHeadSett;
    private TwoStatePreference mQuickSett;
    private ListPreference mScale;
    private ListPreference mRadius;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ContentResolver resolver = getActivity().getContentResolver();
	addPreferencesFromResource(R.xml.notification_drawer_settings);
	PreferenceScreen prefSet = getPreferenceScreen();

        mExpand = (TwoStatePreference) findPreference("hook_system_ui_blurred_status_bar_expanded_enabled_pref");
        boolean mExpandint = (Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, 1) == 1);
        mExpand.setChecked(mExpandint);
        mExpand.setOnPreferenceChangeListener(this);

        mScale = (ListPreference) findPreference("hook_system_ui_blurred_expanded_panel_scale_pref");
        long currentScale = Settings.System.getLong(resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, 10);
        mScale.setValue(String.valueOf(currentScale));
        mScale.setOnPreferenceChangeListener(this);

        mRadius = (ListPreference) findPreference("hook_system_ui_blurred_expanded_panel_radius_pref");
        long currentRadius = Settings.System.getLong(resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, 5);
        mRadius.setValue(String.valueOf(currentRadius));
        mRadius.setOnPreferenceChangeListener(this);

        mNotiTrans = (TwoStatePreference) findPreference("hook_system_ui_translucent_notifications_pref");
        boolean mNotiTransint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, 1) == 1);
        mNotiTrans.setChecked(mNotiTransint);
        mNotiTrans.setOnPreferenceChangeListener(this);

        mHeadSett = (TwoStatePreference) findPreference("hook_system_ui_translucent_header_pref");
        boolean mHeadSettint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, 1) == 1);
        mHeadSett.setChecked(mHeadSettint);
        mHeadSett.setOnPreferenceChangeListener(this);

        mQuickSett = (TwoStatePreference) findPreference("hook_system_ui_translucent_quick_settings_pref");
        boolean mQuickSettint = (Settings.System.getInt(resolver,
                Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, 1) == 1);
        mQuickSett.setChecked(mQuickSettint);
        mQuickSett.setOnPreferenceChangeListener(this);

    }
    
    @Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
	ContentResolver resolver = getActivity().getContentResolver();
	Resources res = getResources();
        Intent i = new Intent("serajr.blurred.system.ui.lp.UPDATE_PREFERENCES");
        if (preference == mExpand) {
            Settings.System.putInt(
                    resolver, Settings.System.STATUS_BAR_EXPANDED_ENABLED_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            getContext().sendBroadcast(i);
            return true;
        } else if (preference == mScale) {
            int value = Integer.parseInt((String) newValue);
            Settings.System.putInt(
                resolver, Settings.System.BLUR_SCALE_PREFERENCE_KEY, value);
            getContext().sendBroadcast(i);
            return true;
        } else if (preference == mRadius) {
            int value = Integer.parseInt((String) newValue);
            Settings.System.putInt(
                resolver, Settings.System.BLUR_RADIUS_PREFERENCE_KEY, value);
            getContext().sendBroadcast(i);
            return true;
        } else if (preference == mNotiTrans) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_NOTIFICATIONS_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            getContext().sendBroadcast(i);
            return true;
        } else if (preference == mHeadSett) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_HEADER_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            getContext().sendBroadcast(i);
            return true;
        } else if (preference == mQuickSett) {
            Settings.System.putInt(
                    resolver, Settings.System.TRANSLUCENT_QUICK_SETTINGS_PREFERENCE_KEY, (((Boolean) newValue) ? 1 : 0));
            getContext().sendBroadcast(i);
            return true;
        }
        return false;
    }

// BlurOS enable

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.NOTIFICATION_DRAWER_SETTINGS;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

   public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                                                                            boolean enabled) {
                    ArrayList<SearchIndexableResource> result =
                            new ArrayList<SearchIndexableResource>();

                    SearchIndexableResource sir = new SearchIndexableResource(context);
                   sir.xmlResId = R.xml.notification_drawer_settings;
                    result.add(sir);

                    return result;
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = new ArrayList<String>();
                    return keys;
                }
        };
}
