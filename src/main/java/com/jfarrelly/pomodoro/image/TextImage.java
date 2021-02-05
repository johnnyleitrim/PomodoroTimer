package com.jfarrelly.pomodoro.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextImage {
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
    imageWidth = iconWidth + (iconWidth * 4);

    image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    graphics = image.createGraphics();

    Font font = new Font(Font.MONOSPACED, Font.BOLD, iconHeight);
    graphics.setFont(font);

    drawText(text);
  }

  public Image getImage() {
    return image;
  }

  public void drawText(String text) {
    graphics.setBackground(new Color(255, 255, 255, 0));
    graphics.clearRect(0, 0, imageWidth, imageHeight);

    FontMetrics metrics = graphics.getFontMetrics();

    int textY = ((imageHeight - metrics.getHeight()) / 2) + metrics.getAscent();

    graphics.drawImage(icon, 0, 0, null);
    graphics.setColor(Color.RED);
    graphics.drawString(text, iconWidth + 5, textY);
  }
}
