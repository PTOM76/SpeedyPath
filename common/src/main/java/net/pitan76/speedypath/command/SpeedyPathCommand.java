package net.pitan76.speedypath.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.pitan76.mcpitanlib.api.command.CommandSettings;
import net.pitan76.mcpitanlib.api.command.LiteralCommand;
import net.pitan76.mcpitanlib.api.command.argument.IntegerCommand;
import net.pitan76.mcpitanlib.api.command.argument.StringCommand;
import net.pitan76.mcpitanlib.api.event.IntegerCommandEvent;
import net.pitan76.mcpitanlib.api.event.ServerCommandEvent;
import net.pitan76.mcpitanlib.api.event.StringCommandEvent;
import net.pitan76.mcpitanlib.api.util.TextUtil;
import net.pitan76.speedypath.Config;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpeedyPathCommand extends LiteralCommand {

    @Override
    public void init(CommandSettings settings) {
        super.init(settings);
        addArgumentCommand("reload", new LiteralCommand() {

            @Override
            public void init(CommandSettings settings) {
                settings.permissionLevel(2);
            }

            @Override
            public void execute(ServerCommandEvent serverCommandEvent) {
                boolean reloaded = Config.reload();
                if (!reloaded) {
                    serverCommandEvent.sendFailure(TextUtil.literal("Failed to reload the config file named \"speedypath.json\""));
                    return;
                }
                serverCommandEvent.sendSuccess(TextUtil.literal("Reloaded the config file named \"speedypath.json\""), false);
            }
        });

        addArgumentCommand("add", new LiteralCommand() {

            @Override
            public void init(CommandSettings settings) {
                settings.permissionLevel(2);

                addArgumentCommand("block_id", new StringCommand() {

                    @Override
                    public String getArgumentName() {
                        return "block_id";
                    }

                    @Override
                    public void init(CommandSettings settings) {
                        settings.permissionLevel(2);

                        addArgumentCommand("effect_id", new StringCommand() {

                            @Override
                            public String getArgumentName() {
                                return "effect_id";
                            }

                            @Override
                            public void init(CommandSettings settings) {
                                settings.permissionLevel(2);

                                addArgumentCommand("amplifier", new IntegerCommand() {

                                    @Override
                                    public String getArgumentName() {
                                        return "amplifier";
                                    }

                                    @Override
                                    public void init(CommandSettings settings) {
                                        settings.permissionLevel(2);
                                    }

                                    @Override
                                    public void execute(IntegerCommandEvent event) {
                                        String blockId = StringArgumentType.getString(event.context, "block_id");
                                        String effectId = StringArgumentType.getString(event.context, "effect_id");
                                        int amplifier = event.getValue();

                                        if (Config.rawPathBlocks.containsKey(blockId)) {
                                            Config.rawPathBlocks.get(blockId).put(effectId, amplifier);
                                        } else {
                                            Map<String, Integer> effects = new LinkedHashMap<>();
                                            effects.put(effectId, amplifier);

                                            Config.rawPathBlocks.put(blockId, effects);
                                        }
                                        Config.saveRaw();
                                        Config.reload();

                                        event.sendSuccess(TextUtil.literal("Added a Speedy Path with the block id \"" + blockId + "\""), false);
                                    }
                                });
                            }

                            @Override
                            public void execute(StringCommandEvent event) {
                                String blockId = StringArgumentType.getString(event.context, "block_id");
                                String effectId = event.getValue();
                                event.sendSuccess(TextUtil.literal("/speedypath add " + blockId + " " + effectId + " [amplifier]"), false);
                            }
                        });

                        addArgumentCommand("amplifier", new IntegerCommand() {

                            @Override
                            public String getArgumentName() {
                                return "amplifier";
                            }

                            @Override
                            public void init(CommandSettings settings) {
                                settings.permissionLevel(2);
                            }

                            @Override
                            public void execute(IntegerCommandEvent event) {
                                String blockId = StringArgumentType.getString(event.context, "block_id");
                                String effectId = "speed";
                                int amplifier = event.getValue();

                                if (Config.rawPathBlocks.containsKey(blockId)) {
                                    Config.rawPathBlocks.get(blockId).put(effectId, amplifier);
                                } else {
                                    Map<String, Integer> effects = new LinkedHashMap<>();
                                    effects.put(effectId, amplifier);

                                    Config.rawPathBlocks.put(blockId, effects);
                                }
                                Config.saveRaw();
                                Config.reload();

                                event.sendSuccess(TextUtil.literal("Added a Speedy Path with the block id \"" + blockId + "\""), false);
                            }
                        });
                    }

                    @Override
                    public void execute(StringCommandEvent event) {
                        String blockId = StringArgumentType.getString(event.context, event.getValue());
                        event.sendSuccess(TextUtil.literal("/speedypath add " + blockId + " ([effect_id]=\"speed\") [amplifier]"), false);
                    }
                });
            }

            @Override
            public void execute(ServerCommandEvent event) {
                event.sendSuccess(TextUtil.literal("/speedypath add [block_id] ([effect_id]=\"speed\") [amplifier]"), false);
            }
        });

        addArgumentCommand("remove", new LiteralCommand() {

            @Override
            public void init(CommandSettings settings) {
                settings.permissionLevel(2);

                addArgumentCommand("block_id", new StringCommand() {

                    @Override
                    public String getArgumentName() {
                        return "block_id";
                    }

                    @Override
                    public void init(CommandSettings settings) {
                        settings.permissionLevel(2);
                    }

                    @Override
                    public void execute(StringCommandEvent event) {
                        String blockId = StringArgumentType.getString(event.context, event.getValue());
                        Config.rawPathBlocks.remove(blockId);
                        Config.saveRaw();
                        Config.reload();
                        event.sendSuccess(TextUtil.literal("Removed the Speedy Path with the block id \"" + blockId + "\""), false);
                    }
                });
            }

            @Override
            public void execute(ServerCommandEvent event) {
                event.sendSuccess(TextUtil.literal("/speedypath remove [block_id]"), false);
            }
        });
    }

    @Override
    public void execute(ServerCommandEvent event) {
        event.sendSuccess(TextUtil.literal(
                "[Speedy Path]"
                + "\n- /speedypath : Show this help message"
                + "\n- /speedypath reload : Reload the config file"
                + "\n- /speedypath add [block_id] ([effect_id]=\"speed\") [amplifier] : Add a Speedy Path to the config file"
                + "\n- /speedypath remove [block_id] : Remove a Speedy Path from the config file"
        ), false);
    }
}
