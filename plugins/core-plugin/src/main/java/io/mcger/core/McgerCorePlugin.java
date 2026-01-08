package io.mcger.core;

import org.bukkit.plugin.java.JavaPlugin;
import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.lifecycle.CoreLifecycle;
import io.mcger.core.runtime.RuntimeContext;

public class McgerCorePlugin extends JavaPlugin {

  private RuntimeContext runtimeContext;
  private CoreLifecycle lifecycle;

  @Override
  public void onEnable() {
    this.runtimeContext = new RuntimeContext(this);
    this.lifecycle = new CoreLifecycle(runtimeContext);

    PluginBridge.init(runtimeContext);

    getLogger().info("[CORE] enabled");
  }
}
