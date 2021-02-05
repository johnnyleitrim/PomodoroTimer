package com.jfarrelly.pomodoro.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TextImage {
  private static final int PADDING = 5;
  private final Image icon;
  private final BufferedImage image;
  private final Graphics2D graphics;
  private final int imageWidth;
  private final int imageHeight;
  private final int iconWidth;

  public TextImage(Image icon, Dimension iconDimension, String text) {
    this.icon = icon;

    iconWidth = (int) iconDimension.getWidth();
    int iconHeight = (int) iconDimension.getHeight();
    imageHeight = iconHeight;
    int fontHeight = imageHeight - (PADDING / 2);
    imageWidth = iconWidth + PADDING + calculateWidthNeeded(iconWidth, imageHeight, fontHeight, text);

    image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    graphics = image.createGraphics();

    Font font = new Font(Font.MONOSPACED, Font.BOLD, fontHeight);
    graphics.setFont(font);

    drawText(text);
  }

  public Image getImage() {
    return image;
  }

  private static int calculateWidthNeeded(int imageWidth, int imageHeight, int fontHeight, String text) {
    BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = image.createGraphics();
    Font font = new Font(Font.MONOSPACED, Font.BOLD, fontHeight);
    graphics.setFont(font);
    return graphics.getFontMetrics().stringWidth(text);

  }

  public void drawText(String text) {
    graphics.setBackground(new Color(255, 255, 255, 0));
    graphics.clearRect(0, 0, imageWidth, imageHeight);

    FontMetrics metrics = graphics.getFontMetrics();

    int textY = ((imageHeight - metrics.getHeight()) / 2) + metrics.getAscent();

    graphics.drawImage(icon, 0, 0, null);
    graphics.setColor(Color.RED);
    graphics.drawString(text, iconWidth + PADDING, textY);
  }
}
