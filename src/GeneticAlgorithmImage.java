// Anjana Kuruwita Arachchi
// Final Project COMP 3710

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GeneticAlgorithmImage {
    private static final int POPULATION_SIZE = 50;
    private static final int MAX_GENERATIONS = 10000;
    private static final double MUTATION_RATE = 0.3;

    public static void main(String[] args) throws IOException {
        // Reading the original image
        File originalImageFile = new File("color11.jpg");
        BufferedImage originalImage = ImageIO.read(originalImageFile);

        // Create an initial population of images
        BufferedImage[] population = createInitialPopulation(originalImage, POPULATION_SIZE);

        for (int generation = 1; generation <= MAX_GENERATIONS; generation++) {

            // Evaluate fitness of each image in the population
            double[] fitnessValues = evaluateFitness(originalImage, population);

            // Print fitness values
            printGenerationFitness(generation,fitnessValues);

            // Select parents based on fitness values
            BufferedImage[] parents = selectParents(population, fitnessValues);

            // Create a new population through crossover and mutation
            BufferedImage[] newPopulation = crossoverAndMutate(parents);

            // Replace the old population with the new one
            population = newPopulation;

            if (generation % 200 == 0) {
                displayBestImage(population, fitnessValues,generation);
                System.out.println("New Image");
            }

        }
    }


    private static BufferedImage[] createInitialPopulation(BufferedImage originalImage, int size) {
        BufferedImage[] population = new BufferedImage[size];
        for (int i = 0; i < size; i++) {
            population[i] = createRandomImage(originalImage.getWidth(), originalImage.getHeight(),originalImage);
        }
        return population;
    }

    private static BufferedImage createRandomImage(int width, int height, BufferedImage originalImage) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            int radius = rand.nextInt(20) + 5;

            // Sample color from the original image at the random position
            int rgb = originalImage.getRGB(x, y);
            Color color = new Color(rgb);

            drawCircle(g, x, y, radius, color);
        }
        return image;
    }

    private static double[] evaluateFitness(BufferedImage originalImage, BufferedImage[] population) {
        double[] fitnessValues = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            fitnessValues[i] = compareImages(originalImage, population[i]);
        }
        return fitnessValues;
    }

    private static void printGenerationFitness(int generation, double[] fitnessValues) {
        System.out.println("Generation " + generation + " - Average Fitness: " + calculateAverageFitness(fitnessValues));
    }

    private static double calculateAverageFitness(double[] fitnessValues) {
        double sum = 0.0;
        for (double fitness : fitnessValues) {
            sum += fitness;
        }
        return sum / fitnessValues.length;
    }


    private static BufferedImage[] selectParents(BufferedImage[] population, double[] fitnessValues) {
        BufferedImage[] parents = new BufferedImage[2];
        int bestIndex1 = findIndexOfMax(fitnessValues);
        fitnessValues[bestIndex1] = Double.NEGATIVE_INFINITY; // Mark as selected
        int bestIndex2 = findIndexOfMax(fitnessValues);

        parents[0] = population[bestIndex1];
        parents[1] = population[bestIndex2];

        return parents;
    }

    private static BufferedImage[] crossoverAndMutate(BufferedImage[] parents) {
        BufferedImage[] newPopulation = new BufferedImage[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            BufferedImage child1 = crossover(parents[0], parents[1]);
            BufferedImage child2 = crossover(parents[1], parents[0]);

            mutate(child1);
            mutate(child2);

            newPopulation[i] = child1;
            newPopulation[i + 1] = child2;
        }

        return newPopulation;
    }

    private static BufferedImage crossover(BufferedImage parent1, BufferedImage parent2) {
        BufferedImage child = new BufferedImage(parent1.getWidth(), parent1.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = child.getGraphics();

        g.drawImage(parent1, 0, 0, parent1.getWidth() / 2, parent1.getHeight(), 0, 0, parent1.getWidth() / 2, parent1.getHeight(), null);
        g.drawImage(parent2, parent1.getWidth() / 2, 0, parent1.getWidth(), parent1.getHeight(), parent1.getWidth() / 2, 0, parent1.getWidth(), parent1.getHeight(), null);

        return child;
    }

    private static void mutate(BufferedImage image) {
        Random rand = new Random();
        if (rand.nextDouble() < MUTATION_RATE) {
            Graphics g = image.getGraphics();
            int x = rand.nextInt(image.getWidth());
            int y = rand.nextInt(image.getHeight());
            int radius = rand.nextInt(20) + 5;
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            drawCircle(g, x, y, radius, color);
        }
    }

    private static double compareImages(BufferedImage img1, BufferedImage img2) {
        double difference = 0.0;
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                Color color1 = new Color(rgb1);
                Color color2 = new Color(rgb2);
                difference += Math.abs(color1.getRed() - color2.getRed());
                difference += Math.abs(color1.getGreen() - color2.getGreen());
                difference += Math.abs(color1.getBlue() - color2.getBlue());
            }
        }
        return difference;
    }

    private static void drawCircle(Graphics g, int x, int y, int radius, Color color) {
        g.setColor(color);

        int numStrokes = 10;
        int maxBrushSize = 5;

        Random rand = new Random();

        for (int i = 0; i < numStrokes; i++) {
            int brushSize = rand.nextInt(maxBrushSize) + 1;
            int brushX = x + rand.nextInt(2 * radius) - radius;
            int brushY = y + rand.nextInt(2 * radius) - radius;

            g.fillOval(brushX - brushSize / 2, brushY - brushSize / 2, brushSize, brushSize);
        }
    }

    private static int findIndexOfMax(double[] array) {
        int index = 0;
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

    private static void displayBestImage(BufferedImage[] population, double[] fitnessValues,int generation) {
        int bestIndex = findIndexOfMax(fitnessValues);
        BufferedImage bestImage = population[bestIndex];
        saveImage(bestImage, "generation_" + generation + ".jpg"); // Save the best image


        displayImage(bestImage);
    }

    private static void saveImage(BufferedImage image, String filename) {
        try {
            ImageIO.write(image, "jpg", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }
}
