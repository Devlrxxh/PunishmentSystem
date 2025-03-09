package dev.lrxh.punishmentSystem.database.impl;

import com.google.gson.JsonParseException;
import dev.lrxh.punishmentSystem.Main;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLiteDatabase implements IDatabase {
    private final String dbPath;
    private Connection connection;

    public SQLiteDatabase() {
        this.dbPath = "jdbc:sqlite:" + Main.instance.getDataFolder() + "/neptune.db";
    }

    @Override
    public IDatabase load() {
        try {
            connection = DriverManager.getConnection(dbPath);
            createTableIfNotExists();
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Failed to connect to SQLite database: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(Main.instance);
        }
        return this;
    }

    @Override
    public DataDocument getUserData(UUID playerUUID) {
        String query = "SELECT * FROM playerData WHERE uuid=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dataString = resultSet.getString("data");
                if (isValidJSON(dataString)) {
                    return new DataDocument(dataString);
                } else {
                    Main.instance.getLogger().severe("Invalid JSON data for UUID: " + playerUUID);
                }
            }
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Error fetching user data from SQLite: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void replace(UUID playerUUID, DataDocument newDocument) {
        String updateQuery = "REPLACE INTO playerData (uuid, data) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, newDocument.toDocument().toJson());
            statement.executeUpdate();
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Error replacing user data in SQLite: " + e.getMessage());
        }
    }

    @Override
    public void replace(String playerUUID, DataDocument newDocument) {
        String updateQuery = "REPLACE INTO playerData (uuid, data) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, playerUUID);
            statement.setString(2, newDocument.toDocument().toJson());
            statement.executeUpdate();
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Error replacing user data in SQLite: " + e.getMessage());
        }
    }

    @Override
    public List<DataDocument> getAll() {
        List<DataDocument> allDocuments = new ArrayList<>();
        String query = "SELECT * FROM playerData";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String jsonString = resultSet.getString("data");
                if (isValidJSON(jsonString)) {
                    allDocuments.add(new DataDocument(jsonString));
                } else {
                    Main.instance.getLogger().severe("Invalid JSON found in database: " + jsonString);
                }
            }
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Error retrieving all documents from SQLite: " + e.getMessage());
        }
        return allDocuments;
    }

    public boolean isValidJSON(String jsonString) {
        try {
            Document.parse(jsonString);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS playerData (" +
                "uuid VARCHAR(36) NOT NULL, " +
                "data TEXT NOT NULL, " +
                "PRIMARY KEY (uuid)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Error creating playerData table: " + e.getMessage());
        }
    }
}
