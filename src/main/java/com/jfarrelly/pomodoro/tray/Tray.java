package com.jfarrelly.pomodoro.tray;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.Duration;

import javax.imageio.ImageIO;

import com.jfarrelly.pomodoro.image.TextImage;

import javafx.application.Platform;

public class Tray {
  private final SystemTray tray;
  private final TrayIcon trayIcon;
  private final TextImage textImage;
  private final MenuItem stopMenuItem;

  public Tray(SystemTray tray, String tooltip, Duration timeRemaining) {
    this.tray = tray;
    Dimension iconSize = tray.getTrayIconSize();
    textImage = new TextImage(getIcon(iconSize), iconSize, asString(timeRemaining));
    stopMenuItem = new MenuItem("Stop");
    trayIcon = new TrayIcon(textImage.getImage(), tooltip, createPopupMenu(stopMenuItem));
    try {
      tray.add(trayIcon);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Image getIcon(Dimension dimension) {
    URL iconUrl = Tray.class.getResource("/images/tomato.png");
    try {
      return ImageIO.read(iconUrl).getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), Image.SCALE_SMOOTH);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String asString(Duration timeRemaining) {
    return String.format("%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());
  }

  public void showMessage(String message) {
    System.out.println(message);
    trayIcon.displayMessage(trayIcon.getToolTip(), message, TrayIcon.MessageType.INFO);
  }

  public void addStopListener(ActionListener listener) {
    stopMenuItem.addActionListener(listener);
  }

  public void setTimeRemaining(Duration timeRemaining) {
    textImage.drawText(asString(timeRemaining));
    trayIcon.setImage(textImage.getImage());
  }

  private PopupMenu createPopupMenu(MenuItem stop) {
    PopupMenu popupMenu = new PopupMenu();

    MenuItem exit = new MenuItem("Exit");
    exit.addActionListener(e -> {
      this.tray.remove(this.trayIcon);
      Platform.exit();
    });

    popupMenu.add(stop);
    popupMenu.addSeparator();
    popupMenu.add(exit);

    return popupMenu;
  }
}
