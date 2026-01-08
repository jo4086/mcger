package dev.mcger.lobby.editor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LobbyEditorManager {

  private static final Set<UUID> editors = new HashSet<>();

  public static boolean isEditor(UUID uuid) {
    return editors.contains(uuid);
  }

  public static void addEditor(UUID uuid) {
    editors.add(uuid);
  }

  public static void removeEditor(UUID uuid) {
    editors.remove(uuid);
  }

  public static void toggleEditor(UUID uuid) {
    if (editors.contains(uuid)) {
      editors.remove(uuid);
    } else {
      editors.add(uuid);
    }
  }
}
