package me.computoMovil.smartAlarm.fragments;

import me.computoMovil.smartAlarm.data.SoundData;
import me.computoMovil.smartAlarm.interfaces.SoundChooserListener;

public abstract class BaseSoundChooserFragment extends BasePagerFragment implements SoundChooserListener {

    private SoundChooserListener listener;

    public void setListener(SoundChooserListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSoundChosen(SoundData sound) {
        if (listener != null)
            listener.onSoundChosen(sound);
    }
}
