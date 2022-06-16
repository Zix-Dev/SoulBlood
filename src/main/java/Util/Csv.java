package Util;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class Csv {

    public static String[][] read(String path) {
        try {
            return new CSVReader(new FileReader(path)).readAll().toArray(new String[0][]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[][] readInts(String path) {
        var strings = read(path);
        var ints = new int[strings.length][];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = new int[strings[i].length];
            for (int j = 0; j < ints[i].length; j++) {
                ints[i][j] = Integer.parseInt(strings[i][j]);
            }
        }
        return ints;
    }
}
