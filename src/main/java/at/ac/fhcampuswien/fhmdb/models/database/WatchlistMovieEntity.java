package at.ac.fhcampuswien.fhmdb.models.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false,unique = true)
    private String apiId; // Reference to the MovieEntity

    public WatchlistMovieEntity(String apiId) {
        this.apiId = apiId;
    }
    public WatchlistMovieEntity(){

    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public long getId() {
        return id;
    }
}
