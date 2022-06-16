package Controller;

import Model.TileMap;
import Util.Json;

public class Test {
    public static void main(String[] args) throws Exception {
        var tm = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        System.out.println(tm.tileSet.tiles.length);
    }
}
