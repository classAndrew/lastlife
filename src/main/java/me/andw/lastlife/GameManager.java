package me.andw.lastlife;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;

import org.bukkit.plugin.java.JavaPlugin;

public class GameManager {
    public enum GameState {
        NOT_STARTED, STARTED
    }

    public GameState state;
    // UUID TO LIVES
    public HashMap<String, Integer> playerLives;

    // who is the boogeyman
    public String boogeyman;

    // game constants
    public int boogeyMins = 10;
    public void setBoogeyMins(int newMins){
        this.boogeyMins=newMins;
    }
    public int getBoogeyMins(){
        return this.boogeyMins;
    }
    public int maxLives = 6;
    public int minLives = 2;

    private static GameManager gameManager;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static transient JavaPlugin p;

    public GameManager() {
        state = GameState.NOT_STARTED;
        playerLives = new HashMap<String, Integer>();
    }

    public static GameManager gm() {
        if (gameManager == null) {
            /*
            Used different api to prevent
            java: cannot find symbol
                symbol:   method of(java.lang.String,java.lang.String)
                location: interface java.nio.file.Path
             */


            // check if I can read a save
            /*File gSave = Path.of(p.getDataFolder().getAbsolutePath(), "save.json").toFile();
            if (gSave.exists()) {
                try {
                    FileInputStream fIS = new FileInputStream(gSave);
                    byte[] saveRaw = fIS.readAllBytes();
                    fIS.close();
                    String saveString = new String(saveRaw, Charset.defaultCharset());
                    gameManager = gson.fromJson(saveString, GameManager.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                gameManager = new GameManager();
                try {
                    if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                    }

                    gSave.createNewFile();
                    String res = gson.toJson(gameManager);
                    FileWriter fw = new FileWriter(gSave);
                    fw.write(res);
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/

            Path dataFolderPath = p.getDataFolder().toPath();
            Path savePath = dataFolderPath.resolve("save.json");

            if (Files.exists(savePath)) {
                try {
                    byte[] saveRaw = Files.readAllBytes(savePath);
                    String saveString = new String(saveRaw, Charset.defaultCharset());
                    gameManager = gson.fromJson(saveString, GameManager.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // if not, then make a new one

            else {
                gameManager = new GameManager();
                try {
                    Files.createDirectories(dataFolderPath);

                    String res = gson.toJson(gameManager);
                    Files.write(savePath, res.getBytes(Charset.defaultCharset()), StandardOpenOption.CREATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        return gameManager;
    }

    /*public static void save() {
        File gSave = Path.of(p.getDataFolder().getAbsolutePath(), "save.json").toFile();

        try {
            if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
            }

            gSave.createNewFile();
            String res = gson.toJson(gameManager);
            FileWriter fw = new FileWriter(gSave);
            fw.write(res);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public static void save() {
        Path dataFolderPath = p.getDataFolder().toPath();
        Path savePath = dataFolderPath.resolve("save.json");

        try {
            Files.createDirectories(dataFolderPath);

            String res = gson.toJson(gameManager);
            Files.write(savePath, res.getBytes(Charset.defaultCharset()), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // p1 gives 1 life to p2
    public void giveLife(String p1, String p2) {
        playerLives.put(p1, playerLives.get(p1)-1);
        playerLives.put(p2, playerLives.get(p2)+1);
        GameManager.save();
    }

    public int getLives(String p1) {
        return playerLives.get(p1);
    }

    public void removeLife(String p) {
        playerLives.put(p, playerLives.get(p)-1);
        GameManager.save();
    }

    public void setPlayer(String p, int lives) {
        playerLives.put(p, lives);
        GameManager.save();
    }

    public void setBoogey(String p) {
        boogeyman = p;
        GameManager.save();
    }
}
