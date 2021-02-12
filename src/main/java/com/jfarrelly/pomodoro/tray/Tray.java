package com.jfarrelly.pomodoro.tray;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
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
  private final MenuItem pomodoroMenuItem;

  public Tray(SystemTray tray, String tooltip, Duration timeRemaining, URL iconUrl) {
    this.tray = tray;
    Dimension iconSize = tray.getTrayIconSize();
    textImage = new TextImage(getIcon(iconSize, iconUrl), iconSize, asString(timeRemaining));
    stopMenuItem = new MenuItem("Stop");
    pomodoroMenuItem = new MenuItem("Pomodoro");
    trayIcon = new TrayIcon(textImage.getImage(), tooltip, createPopupMenu(stopMenuItem, pomodoroMenuItem));
    try {
      tray.add(trayIcon);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static Image getIcon(Dimension dimension, URL iconUrl) {
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
    trayIcon.displayMessage(trayIcon.getToolTip(), message, MessageType.NONE);
  }

  public void addStopListener(ActionListener listener) {
    stopMenuItem.addActionListener(listener);
  }

  public void addPomodoroListener(ActionListener listener) {
    pomodoroMenuItem.addActionListener(listener);
  }

  public void setTimeRemaining(Duration timeRemaining) {
    textImage.drawText(asString(timeRemaining));
    trayIcon.setImage(textImage.getImage());
  }

  private PopupMenu createPopupMenu(MenuItem... menuItems) {
    PopupMenu popupMenu = new PopupMenu();

    MenuItem exit = new MenuItem("Exit");
    exit.addActionListener(e -> {
      this.tray.remove(this.trayIcon);
      Platform.exit();
    });

    for (MenuItem menuItem : menuItems) {
      popupMenu.add(menuItem);
    }
    popupMenu.addSeparator();
    popupMenu.add(exit);

    return popupMenu;
  }
}
