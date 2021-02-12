package com.jfarrelly.pomodoro;

import java.awt.SystemTray;
import java.awt.Taskbar;
import java.net.URL;
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

  public static final Duration POMODORO_DURATION = Duration.ofMinutes(25);
  public static final Duration BREAK_DURATION = Duration.ofMinutes(5);

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
    primaryStage.setTitle("Pomodoro Timer");
    URL tomatoIconUrl = PomodoroApp.class.getResource("/images/tomato.png");

    if (SystemTray.isSupported()) {
      tray = Optional.of(new Tray(SystemTray.getSystemTray(), primaryStage.getTitle(), POMODORO_DURATION, tomatoIconUrl));
    }
    PomodoroView view = new PomodoroView(POMODORO_DURATION);
    CountdownTimerModel model = new CountdownTimerModel(POMODORO_DURATION);
    pomodoroController = new PomodoroController(model, view, tray);

    Taskbar taskbar = Taskbar.getTaskbar();
    taskbar.setIconImage(ImageIO.read(tomatoIconUrl));
    primaryStage.setScene(new Scene(view.getLayout()));
    primaryStage.show();
  }
}
