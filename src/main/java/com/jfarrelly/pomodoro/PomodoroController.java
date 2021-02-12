package com.jfarrelly.pomodoro;

import java.time.Duration;
import java.util.Optional;

import com.jfarrelly.pomodoro.model.CountdownTimerModel;
import com.jfarrelly.pomodoro.tray.Tray;
import com.jfarrelly.pomodoro.view.PomodoroView;

import javafx.application.Platform;

public class PomodoroController {

  private final CountdownTimer countdownTimer;
  private final CountdownTimerModel countdownTimerModel;
  private final PomodoroView view;
  private final NotificationSevice notificationSevice;
  private final Optional<Tray> tray;

  public PomodoroController(CountdownTimerModel countdownTimerModel, PomodoroView view, Optional<Tray> tray) {
    this.view = view;
    this.countdownTimerModel = countdownTimerModel;
    this.tray = tray;
    countdownTimer = new CountdownTimer(countdownTimerModel);
    notificationSevice = new NotificationSevice(tray);

    countdownTimerModel.addListener((observable, oldValue, newValue) -> updateTimeRemaining(newValue));
    view.setBreakButtonHandler(event -> countdownTimer.startWithDuration(PomodoroApp.BREAK_DURATION));
    view.setStopButtonHandler(event -> stop());
    view.setPomodoroButtonHandler(event -> startPomodoro());
    tray.ifPresent(t -> {
      t.addStopListener(event -> stop());
      t.addPomodoroListener(event -> startPomodoro());
    });
  }

  public void stop() {
    countdownTimer.stop();
    notificationSevice.stop();
  }

  private void updateTimeRemaining(Duration timeRemaining) {
    Platform.runLater(() -> {
      view.setTimeRemaining(timeRemaining);
      tray.ifPresent(t -> t.setTimeRemaining(timeRemaining));
      if (timeRemaining.isZero() || timeRemaining.isNegative()) {
        notificationSevice.start();
      }
    });
  }

  private void startPomodoro() {
    countdownTimer.startWithDuration(PomodoroApp.POMODORO_DURATION);
  }
}
