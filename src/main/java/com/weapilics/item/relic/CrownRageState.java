package com.weapilics.item.relic;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrownRageState extends PersistentState {
    private final Map<String, Integer> rage = new HashMap<>();

    public CrownRageState() {
    }

    public static CrownRageState fromNbt(NbtCompound nbt) {
        CrownRageState s = new CrownRageState();
        NbtCompound map = nbt.getCompound("rage").orElse(new NbtCompound());
        for (String key : map.getKeys()) {
            s.rage.put(key, map.getInt(key).orElse(0));
        }
        return s;
    }
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound map = new NbtCompound();
        for (Map.Entry<String, Integer> e : rage.entrySet()) {
            map.putInt(e.getKey(), e.getValue());
        }
        nbt.put("rage", map);
        return nbt;
    }

    public int get(UUID uuid) {
        return rage.getOrDefault(uuid.toString(), 0);
    }

    public void set(UUID uuid, int value) {
        rage.put(uuid.toString(), Math.max(0, value));
        markDirty();
    }

    public void add(UUID uuid, int delta) {
        set(uuid, get(uuid) + delta);
    }

    public void remove(UUID uuid) {
        if (rage.remove(uuid.toString()) != null) markDirty();
    }
}
