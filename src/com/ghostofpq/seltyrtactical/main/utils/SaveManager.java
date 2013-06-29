package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.entities.Player;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 16/06/13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class SaveManager {
    private static SaveManager instance = new SaveManager();

    private SaveManager() {
        checkDirectoriesExist();
    }

    public static SaveManager getInstance() {
        return instance;
    }

    public static void main(String[] argv) {
        SaveManager saveManager = SaveManager.getInstance();
        Player player = new Player("Bobbyxxxxx");
        saveManager.savePlayer(player);

        Player player1 = saveManager.loadPlayer("Bobbyxxxxx");
    }

    public List<String> checkFolderForPlayers() throws IOException {
        File players = new File("./saves/players/");
        return Arrays.asList(players.list());
    }

    public void savePlayer(Player player) {
        try {
            StringBuilder path = new StringBuilder();
            path.append("./saves/players/");
            path.append(player.getPseudo());
            path.append(".pla");
            FileOutputStream fileOut =
                    new FileOutputStream(path.toString());
            ObjectOutputStream out =
                    new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Player loadPlayer(String playerPseudo) {
        StringBuilder path = new StringBuilder();
        path.append("./saves/players/");
        path.append(playerPseudo);
        //path.append(".pla");
        Player player = null;
        try {
            FileInputStream fileIn =
                    new FileInputStream(path.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Player class not found");
            c.printStackTrace();
        }
        System.out.println("Deserialized Player...");
        System.out.println(player.getPseudo());
        return player;
    }

    public void checkDirectoriesExist() {
        File main = new File("./saves/");
        if (!main.exists()) {
            main.mkdir();
        }
        File players = new File("./saves/players/");
        if (!players.exists()) {
            players.mkdir();
        }
        File maps = new File("./saves/maps/");
        if (!maps.exists()) {
            maps.mkdir();
        }
    }
}
