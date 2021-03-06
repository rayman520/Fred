package com.monumenta.rl2.objects;

import com.monumenta.rl2.enums.Biome;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.json.simple.JSONObject;

public class RoomObject {
    // direction the object is facing
    private BlockFace direction;
    // the biome of the object
    private Biome biome;
    // the relative position inside the room
    private Vector relPos;
    // the ral location inside the world
    private Location location;


    //basic constructor that sets fields variables to default values
    public RoomObject() {
        this.direction = BlockFace.SELF;
        this.biome = Biome.NONE;
        this.relPos = new Vector(0,0,0);
        this.location = new Location(null, 0, 0, 0);
    }

    //constructor with given variables
    public RoomObject(BlockFace direction, Biome biome, Vector relPos) {
        this();
        this.direction = direction;
        this.biome = biome;
        this.relPos = relPos;
    }

    //copy constructor
    RoomObject(RoomObject old) {
        this.direction = old.direction;
        this.biome = old.biome;
        this.relPos = old.relPos.clone();
        this.location = old.location.clone();
    }

    // Setters

    public void setDirection(BlockFace direction) {
        this.direction = direction;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public void setRelPos(Vector relPos) {
        this.relPos = relPos;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Getters

    public BlockFace getDirection() {
        return this.direction;
    }

    public Biome getBiome() {
        return this.biome;
    }

    public Vector getRelPos() {
        return this.relPos;
    }

    public Location getLocation() {
        return location;
    }

    // methods

    public JSONObject toJSONObject() {
        JSONObject out = new JSONObject();
        Vector rp = this.getRelPos();
        out.put("x", rp.getBlockX());
        out.put("y", rp.getBlockY());
        out.put("z", rp.getBlockZ());
        out.put("biome", this.getBiome().toString());
        out.put("dir", this.getDirection().toString());
        return out;
    }

}
