package com.monumenta.rl2;

import com.google.gson.*;
import com.monumenta.rl2.enums.Biome;
import com.monumenta.rl2.enums.Config;
import com.monumenta.rl2.enums.RoomType;
import com.monumenta.rl2.objects.Door;
import com.monumenta.rl2.objects.LootChest;
import com.monumenta.rl2.objects.Objective;
import com.monumenta.rl2.objects.Room;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {



    static ArrayList<Room> loadFiles(Plugin plugin, CommandSender sender) {
        ArrayList<Room> out = new ArrayList<>();
        String roomsPath = plugin.getDataFolder().getPath() + "/rooms";
        JsonParser jsonParser = new JsonParser();

        File folder = new File(roomsPath);
        folder.mkdirs();
        for (File subfolder : folder.listFiles()) {
            if (subfolder.isDirectory()) {
                if (sender != null) {
                    sender.sendMessage(subfolder.getName());
                }
                StringBuilder outLog = new StringBuilder();
                for (File file : subfolder.listFiles()) {
                    if (file.isFile()) {
                        String fileName = file.getPath();
                        try (FileReader reader = new FileReader(fileName))
                        {
                            outLog.append(file.getName());
                            out.add(parseFile(jsonParser.parse(reader)));
                            outLog.append(" | ");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JsonParseException e) {
                            System.out.println("JSON Parser crashed for file " + fileName);
                            throw e;
                        }
                    }
                }
                if (sender != null) {
                    sender.sendMessage(outLog.toString());
                }
            }
        }
        return out;
    }

    static Room parseFile(JsonElement root) {
        Room out = new Room();

        JsonObject rootObj = root.getAsJsonObject();
        out.setLocation(new Location(null, 0, 0, 0));
        out.setPath(rootObj.get("path").getAsString());
        out.setSize(parseVector(rootObj.get("size").getAsJsonObject()));
        out.setWeight(rootObj.get("weight").getAsInt());
        out.setType(RoomType.valueOf(rootObj.get("type").getAsString()));
        out.setDoorList(parseDoorList(out, rootObj.get("doors").getAsJsonArray()));
        out.setObjectiveList(parseObjectiveList(rootObj.get("objectives").getAsJsonArray()));
        out.setLootChestList(parseChestList(rootObj.get("chests").getAsJsonArray()));
        return out;
    }

    static ArrayList<Door> parseDoorList(Room parentRoom, JsonArray array) {
        ArrayList<Door> out = new ArrayList<>();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();
            Door current = new Door();
            current.setRelPos(parseVector(obj));
            current.setBiome(Biome.valueOf(obj.get("biome").getAsString()));
            current.setDirection(BlockFace.valueOf(obj.get("dir").getAsString()));
            current.setParentRoom(parentRoom);
            out.add(current);
        }
        return out;
    }

    static ArrayList<Objective> parseObjectiveList(JsonArray array) {
        ArrayList<Objective> out = new ArrayList<>();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();
            Objective current = new Objective();
            current.setRelPos(parseVector(obj));
            current.setBiome(Biome.valueOf(obj.get("biome").getAsString()));
            current.setDirection(BlockFace.valueOf(obj.get("dir").getAsString()));
            out.add(current);
        }
        return out;
    }

    static ArrayList<LootChest> parseChestList(JsonArray array) {
        ArrayList<LootChest> out = new ArrayList<>();

        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();
            LootChest current = new LootChest();
            current.setRelPos(parseVector(obj));
            current.setBiome(Biome.valueOf(obj.get("biome").getAsString()));
            current.setDirection(BlockFace.valueOf(obj.get("dir").getAsString()));
            out.add(current);
        }
        return out;
    }

    static Vector parseVector(JsonObject obj) {
        Vector out = new Vector();

        out.setX(obj.get("x").getAsInt());
        out.setY(obj.get("y").getAsInt());
        out.setZ(obj.get("z").getAsInt());
        return out;
    }
}
