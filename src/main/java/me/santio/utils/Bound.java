package me.santio.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldMayBeFinal", "unused", "UnusedReturnValue"})
public class Bound {
    
    @Getter private Vector corner1;
    @Getter private Vector corner2;
    
    @Getter private double minX;
    @Getter private double minY;
    @Getter private double minZ;
    @Getter private double maxX;
    @Getter private double maxY;
    @Getter private double maxZ;
    
    public Bound(Vector corner1, Vector corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        
        update();
    }
    
    private void update() {
        this.minX = Math.min(corner1.getX(), corner2.getX());
        this.minY = Math.min(corner1.getY(), corner2.getY());
        this.minZ = Math.min(corner1.getZ(), corner2.getZ());
        this.maxX = Math.max(corner1.getX(), corner2.getX());
        this.maxY = Math.max(corner1.getY(), corner2.getY());
        this.maxZ = Math.max(corner1.getZ(), corner2.getZ());
    }
    
    public List<Block> getBlocksWithin(World world) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (double x = minX; x<=maxX; x++) {
            for (double y = minY; y<=maxY; y++) {
                for (double z = minZ; z<=maxZ; z++) {
                    blocks.add(new Location(world, x, y, z).getBlock());
                }
            }
        }
        return blocks;
    }
    
    public Bound setCorner1(Vector corner1) {
        this.corner1 = corner1;
        update();
        return this;
    }
    
    public Bound setCorner2(Vector corner2) {
        this.corner2 = corner2;
        update();
        return this;
    }
    
    public Bound shift(Vector shift) {
        this.corner1.add(shift);
        this.corner2.add(shift);
        update();
        return this;
    }
    
    public Bound expand(double size) {
        minX -= size;
        minY -= size;
        minZ -= size;
        maxX += size;
        maxY += size;
        maxZ += size;
        
        this.corner1 = new Vector(minX, minY, minZ);
        this.corner2 = new Vector(maxX, maxY, maxZ);
        return this;
    }
    
    public Bound expand(Extension extension) {
        this.corner1 = extension.affectMin(corner1);
        this.corner2 = extension.affectMax(corner2);
        update();
        return this;
    }
    
    public ArrayList<Vector> getSide(Side side) {
        ArrayList<Vector> blocks = new ArrayList<>();
        
        Vector corner1 = side.getCorner1(this);
        Vector corner2 = side.getCorner2(this);
        Vector direction = corner2.clone().subtract(corner1).normalize();
        
        blocks.add(corner1);
        while (corner1.distance(corner2) > 1) {
            corner1.add(direction);
            blocks.add(corner1.clone());
        }
        blocks.add(corner1);
        
        return blocks;
    }
    
    public boolean collides(Vector vector) {
        return vector.getX() >= minX
                && vector.getX() <= maxX
                && vector.getY() >= minY
                && vector.getY() <= maxY
                && vector.getZ() >= minZ
                && vector.getZ() <= maxZ;
    }
    
    public Bound copy() {
        return new Bound(getCorner1(), getCorner2());
    }
    
    public enum Side {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        
        public Vector getCorner1(Bound bound) {
            switch(this) {
                case NORTH:
                    return new Vector(bound.getMinX(), 0, bound.getMinZ());
                case EAST:
                    return new Vector(bound.getMaxX(), 0, bound.getMinZ());
                case SOUTH:
                    return new Vector(bound.getMaxX(), 0, bound.getMaxZ());
                case WEST:
                    return new Vector(bound.getMinX(), 0, bound.getMaxZ());
                default:
                    return new Vector(0, 0, 0);
            }
        }
        
        public Vector getCorner2(Bound bound) {
            switch(this) {
                case NORTH:
                    return EAST.getCorner1(bound);
                case EAST:
                    return SOUTH.getCorner1(bound);
                case SOUTH:
                    return WEST.getCorner1(bound);
                case WEST:
                    return NORTH.getCorner1(bound);
                default:
                    return new Vector(0, 0, 0);
            }
        }
    }
    
    public enum Extension {
        VERTICAL(0, 255);
        
        @Getter private final double min;
        @Getter private final double max;
        Extension(double min, double max) {
            this.min = min;
            this.max = max;
        }
        
        public Vector affectMin(Vector original) {
            if (this == VERTICAL) return original.clone().setY(min);
            return null;
        }
        
        public Vector affectMax(Vector original) {
            if (this == VERTICAL) return original.clone().setY(max);
            return null;
        }
    }
    
}
