package net.pitan76.speedypath;

import net.minecraft.block.Block;
import net.pitan76.easyapi.config.JsonConfig;
import net.pitan76.mcpitanlib.api.util.BlockUtil;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.api.util.PlatformUtil;

import java.io.File;
import java.util.*;

// Example:
// {
//   "speedypath": {
//     "minecraft:dirt_path": {
//       "speed": 1,
//       "haste": 1
//     }
//   }
// }
public class Config {
    private static final File file = new File(PlatformUtil.getConfigFolderAsFile(), "speedypath.json");
    private static final JsonConfig config = new JsonConfig(file);

    public static boolean isEmptyPath;

    // Block, Effect ID, Effect Level
    public static Map<Block, Map<String, Integer>> pathBlocks = new LinkedHashMap<>();

    // raw map
    public static Map<String, Map<String, Integer>> rawPathBlocks = new LinkedHashMap<>();

    public static boolean addSpeedPathBlocks = true;

    public static void before() {
        addSpeedPathBlocks = config.getBooleanOrCreate("additional_elements", true);
    }

    public static void after() {
        Map<String, Map<String, Integer>> defaultMap = new LinkedHashMap<>();

        Map<String, Integer> dirtPathInDefaultMap = new LinkedHashMap<>();
        Map<String, Integer> stonePathInDefaultMap = new LinkedHashMap<>();
        Map<String, Integer> brickPathInDefaultMap = new LinkedHashMap<>();
        dirtPathInDefaultMap.put("speed", 1);
        stonePathInDefaultMap.put("speed", 2);
        brickPathInDefaultMap.put("speed", 3);

        defaultMap.put("minecraft:dirt_path", dirtPathInDefaultMap);
        defaultMap.put("speedypath:stone_path", stonePathInDefaultMap);
        defaultMap.put("speedypath:brick_path", brickPathInDefaultMap);

        Map<String, Map<String, Integer>> speedyPaths = (Map<String, Map<String, Integer>>) config.getOrCreate("speedypath", defaultMap);
        rawPathBlocks = speedyPaths;

        for (Map.Entry<String, Map<String, Integer>> entry : speedyPaths.entrySet()) {
            CompatIdentifier id = CompatIdentifier.of(entry.getKey());
            Map<String, Integer> effects = entry.getValue();

            if (!BlockUtil.isExist(id)) {
                // dirt_pathが存在しなくてgrass_pathが存在する場合は、grass_pathを追加
                if (id.toString().equals("minecraft:dirt_path") && BlockUtil.isExist(CompatIdentifier.of("minecraft:grass_path"))) {
                    Block block = BlockUtil.fromId(CompatIdentifier.of("minecraft:grass_path"));
                    pathBlocks.put(block, effects);
                }
                continue;
            }

            Block block = BlockUtil.fromId(id);
            pathBlocks.put(block, effects);
        }

        isEmptyPath = pathBlocks.isEmpty();

        saveOnly();
    }

    public static boolean reload() {
        if (file.exists() && file.isFile()) {
            config.load(file);

            if (!config.has("speedypath"))
                return false;

            Map<String, Map<String, Integer>> speedyPaths = (Map<String, Map<String, Integer>>) config.get("speedypath");
            rawPathBlocks = speedyPaths;

            for (Map.Entry<String, Map<String, Integer>> entry : speedyPaths.entrySet()) {
                CompatIdentifier id = CompatIdentifier.of(entry.getKey());

                Map<String, Integer> effects = entry.getValue();

                if (!BlockUtil.isExist(id))
                    continue;

                Block block = BlockUtil.fromId(id);
                if (pathBlocks.containsKey(block))
                    pathBlocks.remove(block);

                pathBlocks.put(block, effects);
            }

            isEmptyPath = pathBlocks.isEmpty();
            return true;
        }
        return false;
    }

    public static void saveRaw() {
        if (!pathBlocks.isEmpty()) {
            config.set("speedypath", rawPathBlocks);
        }

        saveOnly();
    }

    public static void saveOnly() {
        // configが勝手にDoubleにされてしまうのでIntegerに変換
        Map<String, Map<String, Object>> newSpeedyPathMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : config.configMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!key.equals("speedypath")) continue;

            Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) value;
            map.forEach((key1, effects) -> {
                Map<String, Object> newEffects = new LinkedHashMap<>();

                for (String key2 : effects.keySet()) {
                    Object value2 = effects.get(key2);
                    if (value2 instanceof Double) {
                        newEffects.put(key2, ((Double) value2).intValue());

                    } else {
                        newEffects.put(key2, Integer.valueOf(value2.toString()));
                    }
                }
                newSpeedyPathMap.put(key1, newEffects);
            });
        }

        replaceMap(newSpeedyPathMap);

        config.set("speedypath", newSpeedyPathMap);

        config.save(file, true);
    }

    public static void replaceMap(Map<String, Map<String, Object>> map) {
        pathBlocks.clear();

        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            CompatIdentifier id = CompatIdentifier.of(entry.getKey());
            Map<String, Integer> effects = new LinkedHashMap<>();

            for (Map.Entry<String, Object> effect : entry.getValue().entrySet()) {
                effects.put(effect.getKey(), (Integer) effect.getValue());
            }

            Block block = BlockUtil.fromId(id);
            pathBlocks.put(block, effects);
        }

        isEmptyPath = pathBlocks.isEmpty();
    }
}
