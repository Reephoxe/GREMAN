package polytech.univtours.greman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircuitBuilder {

    private static final int COMPONENT_WIDTH = 240;
    private static final int COMPONENT_HEIGHT = 120;
    private static final int LINE_WIDTH = 4;
    private static int currentX = 0;
    private static int currentY = 0;
    private static int maxWidth = 0;
    private static int maxHeight = 0;

    public static void main(String[] args) {
        try {
            // Load and resize component images
            BufferedImage bobine = resizeImage(ImageIO.read(new File("src/main/resources/bobine.png")), COMPONENT_WIDTH, COMPONENT_HEIGHT);
            BufferedImage condensateur = resizeImage(ImageIO.read(new File("src/main/resources/condensateur.png")), COMPONENT_WIDTH, COMPONENT_HEIGHT);
            BufferedImage resistance = resizeImage(ImageIO.read(new File("src/main/resources/resistance.png")), COMPONENT_WIDTH, COMPONENT_HEIGHT);

            // Initialize the final image with sufficient size
            BufferedImage finalImage = new BufferedImage(COMPONENT_WIDTH * 5, COMPONENT_HEIGHT * 3, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = finalImage.createGraphics();
            g2d.setClip(0, 0, finalImage.getWidth(), finalImage.getHeight());

            // ICI: Ajoutez les composants à l'image finale

            // La résistance obligatoire
            finalImage = addResistance(g2d, finalImage, resistance);

            //A personalisé
            finalImage = addBobine(g2d, finalImage, bobine, resistance);
            finalImage = addCondensateur(g2d, finalImage, condensateur, resistance);
            finalImage = addCondensateur(g2d, finalImage, condensateur, resistance);

            // S'assurer que l'image finale est suffisamment grande
            maxWidth = Math.min(maxWidth, finalImage.getWidth());
            maxHeight = Math.min(maxHeight, finalImage.getHeight());

            // Crop the final image to the actual used size
            BufferedImage croppedImage = finalImage.getSubimage(0, 0, maxWidth, maxHeight);

            // Save the final image
            ImageIO.write(croppedImage, "PNG", new File("src/main/resources/image.png"));
            System.out.println("Final image saved as 'image.png'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage addResistance(Graphics2D g2d, BufferedImage finalImage, BufferedImage resistance) {
        g2d.drawImage(resistance, currentX, currentY, null);
        currentX += COMPONENT_WIDTH;
        maxWidth = Math.min(Math.max(maxWidth, currentX), finalImage.getWidth());
        return ensureImageSize(finalImage, maxWidth, maxHeight);
    }

    private static BufferedImage addBobine(Graphics2D g2d, BufferedImage finalImage, BufferedImage bobine, BufferedImage resistance) {
        g2d.drawImage(resistance, currentX, currentY, null);

        int bobineX = currentX + COMPONENT_WIDTH;
        g2d.drawImage(bobine, bobineX, currentY, null);
        currentX = bobineX + COMPONENT_WIDTH;

        maxWidth = Math.min(Math.max(maxWidth, currentX), finalImage.getWidth());
        return ensureImageSize(finalImage, maxWidth, maxHeight);
    }

    private static BufferedImage addCondensateur(Graphics2D g2d, BufferedImage finalImage, BufferedImage condensateur, BufferedImage resistance) {
        g2d.drawImage(resistance, currentX, currentY, null);

        // Draw vertical lines
        g2d.setColor(Color.BLACK);
        g2d.fillRect(currentX, currentY + (COMPONENT_HEIGHT/2), LINE_WIDTH, COMPONENT_HEIGHT);
        g2d.fillRect(currentX + COMPONENT_WIDTH - 4, currentY + (COMPONENT_HEIGHT/2), LINE_WIDTH, COMPONENT_HEIGHT);

        int condensateurX = currentX;
        int condensateurY = currentY + COMPONENT_HEIGHT + LINE_WIDTH;
        g2d.drawImage(condensateur, condensateurX, condensateurY, null);
        currentY = condensateurY;
        maxHeight = Math.min(Math.max(maxHeight, currentY + COMPONENT_HEIGHT), finalImage.getHeight());
        currentX = condensateurX + COMPONENT_WIDTH;
        maxWidth = Math.min(Math.max(maxWidth, currentX), finalImage.getWidth());
        return ensureImageSize(finalImage, maxWidth, maxHeight);
    }

    private static BufferedImage ensureImageSize(BufferedImage image, int width, int height) {
        if (image.getWidth() < width || image.getHeight() < height) {
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = newImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            image.flush(); // Free up memory
            return newImage;
        }
        return image;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
}
