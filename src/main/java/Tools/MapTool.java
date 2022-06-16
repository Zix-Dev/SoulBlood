package Tools;

import Graphics.TileSet;
import Model.TileMap;
import Util.Csv;
import Util.Json;
import com.google.gson.Gson;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MapTool {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Write map dir path:");
        var path = input.nextLine();
        var dirs = path.replace('\\', '/').split("/");
        var dirName = dirs[dirs.length-1];
        String[] files = new File(path).list();
        var layers = new ArrayList<int[][]>();
        assert files != null;
        for (var file : files) if (file.endsWith(".csv")) {
            layers.add(Csv.readInts(path + "/" + file));
        }
        System.out.println("Write the tileset path: ");
        var tsPath = input.nextLine();
        System.out.println("Write the tile size: ");
        var tileSize = Integer.parseInt(input.nextLine());
        var newPath = path + '/' + dirName + ".json";
        Json.write(new TileMap(layers.toArray(new int[0][][]), new TileSet(tsPath, tileSize)), newPath);
    }

}
