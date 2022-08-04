package net.danh.bsoul.Database;

import net.danh.bsoul.bSoul;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {

    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS PlayerData (" + // make sure to put your table name in here too.
            "`player` varchar(32) NOT NULL," + // This creates the different columns you will save data too. varchar(32) Is a string, int = integer
            "`soul` BIGSERIAL NOT NULL," +
            "`max_soul` BIGSERIAL NOT NULL," +
            "PRIMARY KEY (`player`)" +  // This is creating 5 columns Player, material, amount, storage, max_storage. Primary key is what you are going to use as your indexer. Here we want to use player so
            ");"; // we can search by player, and get kills and total. If you have somehow were searching kills it would provide total and player.
    String dbname;

    public SQLite(bSoul instance) {
        super(instance);
        dbname = "PlayerData"; // Set the table name here e.g. player_kills
    }

    // SQL creation stuff, You can leave the blow stuff untouched.
    public Connection getSQLConnection() {
        File dataFolder = new File(bSoul.getInstance().getDataFolder(), dbname + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                bSoul.getInstance().getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            bSoul.getInstance().getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            bSoul.getInstance().getLogger().log(Level.SEVERE, "You need the SQLite JDBC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}

