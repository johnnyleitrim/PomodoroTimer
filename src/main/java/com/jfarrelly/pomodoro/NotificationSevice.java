package com.jfarrelly.pomodoro;

import java.util.Optional;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.jfarrelly.pomodoro.tray.Tray;

public class NotificationSevice {

  private final Clip audioClip;

  private final Optional<Tray> tray;

  public NotificationSevice(Optional<Tray> tray) {
    this.tray = tray;
    try {
      audioClip = AudioSystem.getClip();
      audioClip.open(AudioSystem.getAudioInputStream(PomodoroApp.class.getResource("/sound/ring.wav")));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void start() {
    tray.ifPresent(t -> t.showMessage("Pomodoro completed!"));
    audioClip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    audioClip.stop();
  }
}
