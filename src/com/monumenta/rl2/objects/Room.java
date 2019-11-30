package com.monumenta.rl2.objects;

import com.monumenta.rl2.enums.RoomType;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Room {
    private String path;
    private RoomType type;
    private Vector size;
    private Location location;
    private Hitbox hitbox;
    private int weight;

    ArrayList<Objective> objectiveList;
    ArrayList<LootChest> lootChestList;
    ArrayList<Door> doorList;

    // copy constructor
    public Room(Room old) {
        this();
        this.path = old.path;
        this.type = old.type;
        this.size = old.size;
        this.location = old.location.clone();
        this.weight = old.weight;

        for (LootChest c : old.lootChestList) {
            this.lootChestList.add(new LootChest(c));
        }
        for (Objective o : old.objectiveList) {
            this.objectiveList.add(new Objective(o));
        }
        for (Door d : old.doorList) {
            Door n = new Door(d);
            n.setParentRoom(this);
            this.doorList.add(n);
        }
    }

    //basic constructor
    public Room() {
        this.location = new Location(null, 0, 0, 0);
        this.objectiveList = new ArrayList<>();
        this.lootChestList = new ArrayList<>();
        this.doorList = new ArrayList<>();
        this.type = RoomType.NONE;
    }

    //getters

    public String getPath() {
        return path;
    }

    public RoomType getType() {
        return this.type;
    }

    public Vector getSize() {
        return this.size;
    }

    public Location getLocation() {
        return this.location;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public int getWeight() {
        return this.weight;
    }

    public ArrayList<Door> getDoorList() {
        return this.doorList;
    }

    public ArrayList<Objective> getObjectiveList() {
        return this.objectiveList;
    }

    public ArrayList<LootChest> getLootChestList() {
        return this.lootChestList;
    }

    public String getLoadStructureCommand() {
        StringBuilder out = new StringBuilder();

        out.append("loadstructure \"");
        out.append(this.path);
        out.append("\" ");
        out.append(this.location.getBlockX());
        out.append(" ");
        out.append(this.location.getBlockY());
        out.append(" ");
        out.append(this.location.getBlockZ());
        out.append(" true");

        return out.toString();
    }

    // setters

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDoorList(ArrayList<Door> doorList) {
        this.doorList = doorList;
    }

    public void setObjectiveList(ArrayList<Objective> objectiveList) {
        this.objectiveList = objectiveList;
    }

    public void setLootChestList(ArrayList<LootChest> lootChestList) {
        this.lootChestList = lootChestList;
    }

    // methods

    public JSONObject toJSONObject() {
        JSONObject room = new JSONObject();
        JSONObject size = new JSONObject();
        JSONArray doors = new JSONArray();
        JSONArray objectives = new JSONArray();
        JSONArray chests = new JSONArray();

        Vector roomSize = this.getSize();
        size.put("x", roomSize.getBlockX());
        size.put("y", roomSize.getBlockY());
        size.put("z", roomSize.getBlockZ());

        for (Door d : doorList) {
            doors.add(d.toJSONObject());
        }
        for (Objective o : objectiveList) {
            objectives.add(o.toJSONObject());
        }
        for (LootChest c : lootChestList) {
            chests.add(c.toJSONObject());
        }

        room.put("path", this.path);
        room.put("size", size);
        room.put("type", this.type.name());
        room.put("weight", this.weight);
        room.put("doors", doors);
        room.put("objectives", objectives);
        room.put("chests", chests);
        return room;
    }
}
