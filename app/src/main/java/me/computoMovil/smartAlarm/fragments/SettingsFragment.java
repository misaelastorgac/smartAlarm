package me.computoMovil.smartAlarm.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.aesthetic.Aesthetic;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.computoMovil.smartAlarm.R;
import me.computoMovil.smartAlarm.adapters.PreferenceAdapter;
import me.computoMovil.smartAlarm.data.PreferenceData;
import me.computoMovil.smartAlarm.data.preference.AboutPreferenceData;
import me.computoMovil.smartAlarm.data.preference.BasePreferenceData;
import me.computoMovil.smartAlarm.data.preference.BatteryOptimizationPreferenceData;
import me.computoMovil.smartAlarm.data.preference.BooleanPreferenceData;
import me.computoMovil.smartAlarm.data.preference.ImageFilePreferenceData;
import me.computoMovil.smartAlarm.data.preference.RingtonePreferenceData;
import me.computoMovil.smartAlarm.data.preference.ThemePreferenceData;
import me.computoMovil.smartAlarm.data.preference.TimePreferenceData;
import me.computoMovil.smartAlarm.data.preference.TimeZonesPreferenceData;

public class SettingsFragment extends BasePagerFragment implements Consumer {

    private RecyclerView recyclerView;

    private PreferenceAdapter preferenceAdapter;

    private Disposable colorPrimarySubscription;
    private Disposable textColorPrimarySubscription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        ArrayList<BasePreferenceData> list = new ArrayList<BasePreferenceData>(Arrays.asList(
                new ThemePreferenceData(),
                new ImageFilePreferenceData(PreferenceData.BACKGROUND_IMAGE, R.string.title_background_image),
                new BooleanPreferenceData(PreferenceData.RINGING_BACKGROUND_IMAGE, R.string.title_ringing_background_image, R.string.desc_ringing_background_image),
                new TimeZonesPreferenceData(PreferenceData.TIME_ZONE_ENABLED, R.string.title_time_zones),
                new RingtonePreferenceData(PreferenceData.DEFAULT_ALARM_RINGTONE, R.string.title_default_alarm_ringtone),
                new RingtonePreferenceData(PreferenceData.DEFAULT_TIMER_RINGTONE, R.string.title_default_timer_ringtone),
                new BooleanPreferenceData(PreferenceData.SLEEP_REMINDER, R.string.title_sleep_reminder, R.string.desc_sleep_reminder),
                new TimePreferenceData(PreferenceData.SLEEP_REMINDER_TIME, R.string.title_sleep_reminder_time),
                new BooleanPreferenceData(PreferenceData.SLOW_WAKE_UP, R.string.title_slow_wake_up, R.string.desc_slow_wake_up),
                new TimePreferenceData(PreferenceData.SLOW_WAKE_UP_TIME, R.string.title_slow_wake_up_time)
        ));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            list.add(new BatteryOptimizationPreferenceData());

        list.add(new AboutPreferenceData());

        preferenceAdapter = new PreferenceAdapter(list);
        recyclerView.setAdapter(preferenceAdapter);

        colorPrimarySubscription = Aesthetic.Companion.get()
                .colorPrimary()
                .subscribe(this);

        textColorPrimarySubscription = Aesthetic.Companion.get()
                .textColorPrimary()
                .subscribe(this);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        colorPrimarySubscription.dispose();
        textColorPrimarySubscription.dispose();
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.title_settings);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (preferenceAdapter != null)
            preferenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void accept(Object o) throws Exception {
        if (preferenceAdapter != null)
            preferenceAdapter.notifyDataSetChanged();
    }

}
