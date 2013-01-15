package net.servertube.pidfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import java.lang.management.ManagementFactory;

/**
 *
 * @author A520
 * @version 0.2
 */
public class PidFile extends JavaPlugin {

    public String GetPID(){
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        pid = pid.substring(0, pid.indexOf("@"));
        return pid;
    }
    
    private void SavePID(){
        String pid=GetPID();
        getLogger().info("Server PID: " + pid);
        FileWriter fw = null;
        BufferedWriter fwout = null;
        try {
            fw = new FileWriter("server.pid");
            fwout = new BufferedWriter(fw);
            fwout.write(pid);
            fwout.close();
            fw.close();
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Error writing server.pid file", ex);
        } finally {
            fw = null;
            fwout = null;
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    if (cmd.getName().equalsIgnoreCase("pid")){ 
                sender.sendMessage("Server PID is: " + GetPID());
                return true;
        }
    else if (cmd.getName().equalsIgnoreCase("savepid") || cmd.getName().equalsIgnoreCase("spid")){ 
        SavePID();
    }
        return false;
    }
    
  @Override
  public void onEnable() {
    SavePID();
  }

  @Override
  public void onDisable() {
    new File("server.pid").delete();
  }
}