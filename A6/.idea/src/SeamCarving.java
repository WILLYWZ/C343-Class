import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public class SeamCarving {
    private int[] pixels;
    private int type, height, width;

    // Field getters

    int[] getPixels() {
        return pixels;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    // Read and write images

    void readImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        type = image.getType();
        height = image.getHeight();
        width = image.getWidth();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    void writeImage(String filename) throws IOException {
        BufferedImage image = new BufferedImage(width, height, type);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        ImageIO.write(image, "jpg", new File(filename));
    }

    // Accessing pixels and their neighbors

    /***
     * By convention, h is the vertical index and w and the horizontal index.
     * The array of pixels is stored as follows:
     * [(0,0), (0,1), (0,2), ... (0,width-1), (1,0), (1,1), (1,2), ... (1,width-1), ...]
     */
    Color getColor(int h, int w) {
        int pixel = pixels[w + h * width];
        return new Color(pixel, true);
    }

    /**
     * This method takes the position of a pixel (h,w) and returns a list of its
     * neighbors' positions in the horizontal and vertical directions.
     * In the general case, these would be at  positions:
     * (h+1,w), (h-1,w), (h,w+1), (h,w-1).
     * Of course, care must be taken when dealing with pixels along the boundaries.
     */

    ArrayList<Position> getHVneighbors(int h, int w) {
        // For visualization
        // |__|__|__|__|
        // |__|__|__|__|
        // |__|__|__|__|
        // |__|__|__|__|

        // If height is bigger than 0, there has to be a space below the position
        // If width is bigger than 0, there has to be a space left the position
        // only exceptions are the right, and the down

        ArrayList<Position> positionList = new ArrayList<>();
        if (h > 0) {
            positionList.add(new Position(h - 1, w));
        }
        if (h < this.height - 1) {
            positionList.add(new Position(h + 1, w));
        }
        if (w > 0) {
            positionList.add(new Position(h, w - 1));
        }
        if (w < this.width - 1) {
            positionList.add(new Position(h, w + 1));
        }
        return positionList;
    }

    /**
     * This method takes the position of a pixel (h,w) and returns a list of its
     * neighbors' positions that are below and touching it.
     * In the general case, these would be at  positions:
     * (h+1,w-1), (h+1,w), (h+1,w+1)
     * Of course, care must be taken when dealing with pixels along the boundaries.
     */

    ArrayList<Position> getBelowNeighbors(int h, int w) {

        // For visualization
        // |__|__|__|__|
        // |__|__|__|__|
        // |__|__|__|__|
        // |__|__|__|__|

        ArrayList<Position> positionList = new ArrayList<>();
        if (h < this.height - 1 && w == 0) {
            positionList.add(new Position(h + 1, w));
            positionList.add(new Position(h + 1, w + 1));

        } else if (h < this.height - 1 && w == this.width - 1) {
            positionList.add(new Position(h + 1, w));
            positionList.add(new Position(h + 1, w - 1));

        } else if (h < this.height - 1 && w < this.width - 1 && w > 0) {
            positionList.add(new Position(h + 1, w));
            positionList.add(new Position(h + 1, w + 1));
            positionList.add(new Position(h + 1, w - 1));
        }
        return positionList;
    }

    /**
     * This method takes the position of a pixel (h,w) and computes its 'energy'
     * which is an estimate of how it differs from its neighbors. The computation
     * is as follows. First, using the method getColor, get the colors of the pixel
     * and all its neighbors in the horizontal and vertical dimensions. The energy
     * is the sum of the squares of the differences along each of the RGB components
     * of the color. For example, given two colors c1 and c2 (for the current pixel
     * and one of its neighbors), we would compute this component of the energy as:
     * square (c1.getRed() - c2.getRed()) +
     * square (c1.getGreen() - c2.getGreen()) +
     * square (c1.getBlue() - c2.getBlue())
     * The total energy is this quantity summed over all the neighbors in the
     * horizontal and vertical dimensions.
     */

    int computeEnergy(int h, int w) {

        int sum = 0;
        Color color = getColor(h, w);
        ArrayList<Position> positionList = getHVneighbors(h, w);

        for (Position i : positionList) {

            Color x = getColor(i.getFirst(), i.getSecond());
            int red = (int) Math.pow(x.getRed() - color.getRed(), 2);
            int green = (int) Math.pow(x.getGreen() - color.getGreen(), 2);
            int blue = (int) Math.pow(x.getBlue() - color.getBlue(), 2);
            sum = sum + red + green + blue;
        }
        return sum;
    }

    /**
     * This next method is the core of our dynamic programming algorithm. We will
     * use the top-down approach with the given hash table (which you should initialize).
     * The key to the hash table is a pixel position. The value stored at each key
     * is the "seam" that starts with this pixel all the way to the bottom
     * of the image and its cost.
     * <p>
     * The method takes the position of a pixel and returns the seam from this pixel
     * and its cost using the following steps:
     * - compute the energy of the given pixel
     * - get the list of neighbors below the current pixel
     * - Base case: if the list of neighbors is empty, return the following pair:
     * < [<h,w>], energy >
     * the first component of the pair is a list containing just one position
     * (the current one); the second component of the pair is the current energy.
     * - Recursive case: we will consider each of the neighbors below the current
     * pixel and choose the one with the cheapest seam.
     */

    Map<Position, Pair<List<Position>, Integer>> hash = new HashMap<>();

    Pair<List<Position>, Integer> findSeam(int h, int w) {
        ArrayList<Position> n = this.getBelowNeighbors(h, w);
        int energy = computeEnergy(h, w);
        int bestCost = Integer.MAX_VALUE;
        List<Position> bestPath = null;

        if (hash.containsKey(new Position(h, w))) {
            return hash.get(new Position(h, w));
        }

        if (n.isEmpty()) {
            return new Pair<List<Position>, Integer>(List.singleton(new Position(h, w)), energy);
        } else {
            for (Position i : n) {
                Pair<List<Position>, Integer> list = findSeam(i.getFirst(), i.getSecond());

                if (list.getSecond() < bestCost) {
                    bestCost = list.getSecond();
                    bestPath = list.getFirst();
                }
            }
            bestCost = bestCost + energy;
            bestPath = new Node(new Position(h, w), bestPath);
            Pair<List<Position>, Integer> BestPair = new Pair(bestPath, bestCost);
            hash.put(new Position(h, w), BestPair);
            return BestPair;
        }
    }

    /**
     * This next method is relatively short. It performs the following actions:
     * - clears the hash table
     * - iterate over the first row of the image, computing the seam
     * from its position and returning the best one.
     */

    Pair<List<Position>, Integer> bestSeam() {
        hash.clear();
        Pair<List<Position>, Integer> best = null;
        int bestCost = Integer.MAX_VALUE;
        for (int width = 0; width < this.getWidth(); width++) {
            Pair<List<Position>, Integer> small = findSeam(0, width);

            if (small.getSecond() < bestCost) {
                bestCost = small.getSecond();
                best = small;
            }
        }
        return best;
    }

    /**
     * The last method puts its all together:
     * - it finds the best seam
     * - then it creates a new array of pixels representing an image of dimensions
     * (height,width-1)
     * - it then copies the old array pixels to the new arrays skipping the pixels
     * in the seam
     * - the method does not return anything: instead it updates the width and
     * pixels instance variables to the new values.
     */
    void cutSeam() {
        List<Position> bSeam = bestSeam().getFirst();
        int start = height * (width - 1);
        int[] pixel = new int[start];
        int newWidth;

        for (int h = 0; h < height; h++) {
            newWidth = 0;
            try {
                for (int w = 0; w < width; w++) {
                    if (bSeam.isEmpty()) {
                        pixel[h * (width - 1) + newWidth] = pixels[h * width + w];
                        newWidth++;
                    } else {
                        if (bSeam.getFirst().equals(new Position(h, w))) {
                            bSeam = bSeam.getRest();
                        } else {
                            pixel[h * (width - 1) + newWidth] = pixels[h * width + w];
                            newWidth++;
                        }
                    }
                }
            } catch (EmptyListE e) {
                throw new Error("woops");
            }
        }

        pixels = pixel;
        width -= 1;
    }
}


