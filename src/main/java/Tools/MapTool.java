package Tools;

import Model.Physics.Body;
import Model.TileMap;
import Util.Json;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import javax.swing.*;
import java.io.FileReader;
import java.util.ArrayList;

public class MapTool {

    public static void main(String[] args) throws Exception {
        JFileChooser fc = new JFileChooser("src/main/resources/Maps");
        fc.showOpenDialog(new JDialog());
        var path = fc.getSelectedFile().getPath();
        var b = new GsonBuilder();
        b.registerTypeAdapter(TileMap.class, (JsonDeserializer<TileMap>) (jsonElement, type, jsonDeserializationContext) -> {
            ArrayList<int[][]> layers = new ArrayList<>();
            ArrayList<Body> colliders = new ArrayList<>();
            var jo = jsonElement.getAsJsonObject();
            var jlayers = jo.get("layers").getAsJsonArray();
            for (var l : jlayers) {
                String layerType = l.getAsJsonObject().get("type").getAsString();
                if (layerType.equals("tilelayer")) {
                    var a = l.getAsJsonObject().get("data");
                    var w = l.getAsJsonObject().get("width").getAsInt();
                    var c =(int[]) jsonDeserializationContext.deserialize(a.getAsJsonArray(), int[].class);
                    var d = new int[c.length/w][w];
                    for (int i = 0; i < c.length; i++) {
                        d[i/w][i%w] = c[i]-1;
                    }
                    layers.add(d);
                } else if (layerType.equals("objectgroup")){
                    var as = l.getAsJsonObject().get("objects").getAsJsonArray();
                    for (var a : as) {
                        var w = a.getAsJsonObject().get("width").getAsInt();
                        var h = a.getAsJsonObject().get("height").getAsInt();
                        var x = a.getAsJsonObject().get("x").getAsInt();
                        var y = a.getAsJsonObject().get("y").getAsInt();
                        colliders.add(new Body(x/32f,y/32f,w/32f,h/32f));
                    }
                }
            }
            return new TileMap(layers.toArray(new int[0][][]), colliders.toArray(new Body[0]));
        });
        var gson = b.create();
        var fr = new FileReader(path);
        var tm = gson.fromJson(fr, TileMap.class);
        fr.close();
        Json.write(tm, path);
        System.exit(0);
    }
}
