package com.veontomo.bead.bktree;

/**
 * Levenshtein distance
 * <p>@see <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">definition</a></p>
 */
public class Levenshtein implements Distance<String> {
    @Override
    public double eval(String e1, String e2) {
        int width = e1.length() + 1,
                height = e2.length() + 1;
        int[][] matrix = new int[width][height];
        for (int i = 0; i < width; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j < height; j++) {
            matrix[0][j] = j;
        }
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                int remove = matrix[i - 1][j] + 1;
                int add = matrix[i][j - 1] + 1;
                int sub = matrix[i - 1][j - 1] + (e1.charAt(i - 1) == e2.charAt(j - 1) ? 0 : 1);
                matrix[i][j] = Math.min(remove, Math.min(add, sub));
            }
        }
        return matrix[width-1][height-1];
    }
}
