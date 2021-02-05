package com.jfarrelly.pomodoro;

import java.awt.SystemTray;
import java.awt.Taskbar;
import java.time.Duration;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.jfarrelly.pomodoro.model.CountdownTimerModel;
import com.jfarrelly.pomodoro.tray.Tray;
import com.jfarrelly.pomodoro.view.PomodoroView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PomodoroApp extends Application {

  private static final Duration POMODORO_DURATION = Duration.ofMinutes(25);

  private PomodoroController pomodoroController;

  private Optional<Tray> tray = Optional.empty();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void stop() throws Exception {
    pomodoroController.stop();
    super.stop();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Pomodoro");

    if (SystemTray.isSupported()) {
      tray = Optional.of(new Tray(SystemTray.getSystemTray(), primaryStage.getTitle(), POMODORO_DURATION));
    }
    PomodoroView view = new PomodoroView(POMODORO_DURATION);
    CountdownTimerModel model = new CountdownTimerModel(POMODORO_DURATION);
    pomodoroController = new PomodoroController(model, view, tray);

    Scene scene = new Scene(view.getLayout());

    Taskbar taskbar = Taskbar.getTaskbar();
    taskbar.setIconImage(ImageIO.read(PomodoroApp.class.getResourceAsStream("/images/tomato.png")));

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
