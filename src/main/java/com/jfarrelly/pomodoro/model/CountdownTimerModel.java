package com.jfarrelly.pomodoro.model;

import java.time.Duration;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;

public class CountdownTimerModel {

  private final SimpleLongProperty seconds;

  public CountdownTimerModel() {
    this(Duration.ZERO);
  }

  public CountdownTimerModel(Duration duration) {
    seconds = new SimpleLongProperty();
    setDuration(duration);
  }

  public void addListener(ChangeListener<Duration> changeListener) {
    seconds.addListener((observable, oldValue, newValue) -> changeListener.changed(
        null,
        Duration.ofSeconds(oldValue.longValue()),
        Duration.ofSeconds(newValue.longValue()
        )));
  }

  public void setDuration(Duration duration) {
    seconds.set(duration.toSeconds());
  }

  public void reduce(Duration duration) {
    seconds.set(Math.max(0, seconds.get() - duration.toSeconds()));
  }

  public Duration getTimeRemaining() {
    return Duration.ofSeconds(seconds.get());
  }

  public boolean hasTimeRemaining() {
    return seconds.get() > 0;
  }
}
