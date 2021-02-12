package com.jfarrelly.pomodoro;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import com.jfarrelly.pomodoro.model.CountdownTimerModel;

import javafx.application.Platform;

public class CountdownTimer {
  private final Timer timer = new Timer("CountdownTimer", true);
  private final CountdownTimerModel timerModel;
  private TimerTask timerTask;

  public CountdownTimer(CountdownTimerModel timerModel) {
    this.timerModel = timerModel;
  }

  public void start() {
    if (timerTask == null) {
      timerTask = new TimerTask() {
        @Override
        public void run() {
          Platform.runLater(() -> timerModel.reduce(Duration.ofSeconds(1)));
          if (!timerModel.hasTimeRemaining()) {
            cancel();
          }
        }
      };

      timer.schedule(timerTask, 0, 1000);
    }
  }

  public void startWithDuration(Duration duration) {
    timerModel.setDuration(duration);
    start();
  }

  public void stop() {
    if (timerTask != null) {
      timerTask.cancel();
      timerTask = null;
    }
  }
}
