package io.mcger.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.lifecycle.CoreLifecycle;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.core.runtime.WorldSummary;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;

public class McgerCorePlugin extends JavaPlugin {

  private static final String WORLDS_FILE = "worlds.json";

  private RuntimeContext runtimeContext;
  private CoreLifecycle lifecycle;

  @Override
  public void onEnable() {
    this.runtimeContext = new RuntimeContext(this);
    this.lifecycle = new CoreLifecycle(runtimeContext);
    PluginBridge.init(runtimeContext);

    loadWorldSummaries();

    getLogger().info("[CORE] enabled");
  }

  private void loadWorldSummaries() {
    if (!getDataFolder().exists() && !getDataFolder().mkdirs()) {
      getLogger().warning("[CORE] Failed to create data folder.");
      return;
    }

    File dataFile = new File(getDataFolder(), WORLDS_FILE);
    if (!dataFile.exists()) {
      saveResource(WORLDS_FILE, false);
    }

    try (Reader reader = Files.newBufferedReader(dataFile.toPath(), StandardCharsets.UTF_8)) {
      Type listType = new TypeToken<List<WorldSummaryPayload>>() {
      }.getType();
      List<WorldSummaryPayload> payloads = new Gson().fromJson(reader, listType);
      runtimeContext.clearWorldSummaries();
      if (payloads == null) {
        getLogger().warning("[CORE] No world summaries found in " + WORLDS_FILE);
        return;
      }

      int count = 0;
      for (WorldSummaryPayload payload : payloads) {
        WorldSummary summary = payload.toWorldSummary();
        if (summary == null) {
          continue;
        }
        runtimeContext.registerWorldSummary(summary);
        count++;
      }
      getLogger().info("[CORE] Loaded " + count + " world summaries.");
    } catch (IOException e) {
      getLogger().severe("[CORE] Failed to load world summaries: " + e.getMessage());
    }
  }

  private static class WorldSummaryPayload {
    private String worldId;
    private String name;
    private String ownerUuid;
    private int maxPlayers;
    private boolean passwordEnabled;
    private boolean whitelistEnabled;

    private WorldSummary toWorldSummary() {
      if (worldId == null || worldId.isBlank() || name == null || name.isBlank()) {
        return null;
      }
      UUID owner = null;
      if (ownerUuid != null && !ownerUuid.isBlank()) {
        try {
          owner = UUID.fromString(ownerUuid);
        } catch (IllegalArgumentException ignored) {
          owner = null;
        }
      }
      return new WorldSummary(worldId, name, owner, maxPlayers, passwordEnabled, whitelistEnabled);
    }
  }
}
