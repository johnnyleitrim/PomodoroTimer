package com.jfarrelly.pomodoro.view;

import java.time.Duration;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PomodoroView {
  private Button startButton = new Button("Start");
  private Button stopButton = new Button("Stop");
  private Button restartButton = new Button("Restart");
  private Label time = new Label();
  private VBox layout;

  public PomodoroView(Duration initialTimeRemaining) {
    HBox hbox = new HBox(startButton, stopButton, restartButton);
    hbox.setPadding(new Insets(10, 10, 10, 10));
    hbox.setSpacing(10);

    Font font = new Font("Menlo Bold", 60);
    time.setFont(font);

    layout = new VBox(time, hbox);

    time.setMaxWidth(Double.MAX_VALUE);
    time.setMaxHeight(Double.MAX_VALUE);
    time.setContentDisplay(ContentDisplay.CENTER);
    time.setAlignment(Pos.CENTER);

    startButton.setMaxWidth(Double.MAX_VALUE);
    stopButton.setMaxWidth(Double.MAX_VALUE);
    restartButton.setMaxWidth(Double.MAX_VALUE);

    HBox.setHgrow(startButton, Priority.ALWAYS);
    HBox.setHgrow(stopButton, Priority.ALWAYS);
    HBox.setHgrow(restartButton, Priority.ALWAYS);

    VBox.setVgrow(time, Priority.ALWAYS);

    setTimeRemaining(initialTimeRemaining);
  }

  public VBox getLayout() {
    return layout;
  }

  public void setTimeRemaining(Duration timeRemaining) {
    String clockText = String.format("%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());
    time.setText(clockText);
  }

  public void setStartButtonHandler(EventHandler<ActionEvent> eventHandler) {
    startButton.setOnAction(eventHandler);
  }

  public void setStopButtonHandler(EventHandler<ActionEvent> eventHandler) {
    stopButton.setOnAction(eventHandler);
  }

  public void setRestartButtonHandler(EventHandler<ActionEvent> eventHandler) {
    restartButton.setOnAction(eventHandler);
  }


}
