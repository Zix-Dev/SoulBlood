package Util;

import Model.Physics.Body;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Json {

    public static Gson gson;
    static {
        var builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Body.class, (JsonDeserializer<Body>) (jsonElement, type, jsonDeserializationContext)
                -> {
            var b = jsonElement.getAsJsonObject();
            return new Body(b.get("x").getAsFloat(),
                    b.get("y").getAsFloat(),
                    b.get("width").getAsFloat(),
                    b.get("height").getAsFloat());});
        gson = builder.create();
    }


    public static <T> T read(String path, Class<T> type) {
        T object = null;
        try (FileReader fileReader = new FileReader(path)) {
            object = gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (object instanceof PostLoadable) {
            ((PostLoadable) object).load();
        }
        return object;
    }

    public static void write(Object object, String path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(object, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
