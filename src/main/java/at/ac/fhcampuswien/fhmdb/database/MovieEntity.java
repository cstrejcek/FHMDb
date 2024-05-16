package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false,unique = true)
    private String apiId;

    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField()
    private String description;
    @DatabaseField()
    private String genres;
    @DatabaseField()
    private int releaseYear;
    @DatabaseField()
    private String imgUrl;
    @DatabaseField()
    private int lengthInMinutes;
    @DatabaseField()
    private double rating;

    /*Da für das Speichern von Listen (zB directors, writers, mainCast)
     weitere Tabellen nötig wären, werden diese ausgenommen.  */

    public static String genresToString(List<Genre> genres){
        String genresString = "";
        if(genres != null) {
            for (Genre genre : genres) {
                genresString += genre.toString() + ",";
            }
            if (!genresString.isEmpty()) {
                genresString = genresString.substring(0, genresString.length() - 1);
            }
        }
        return genresString;
    }
    public static List<MovieEntity> fromMovies(List<Movie> movies){
        List<MovieEntity> movieEntities = new ArrayList<>();
        for(Movie movie : movies) {
            MovieEntity me = new MovieEntity();
            //id gets generated automatically
            me.apiId = movie.getId();
            me.title = movie.getTitle();
            me.description = movie.getDescription();
            me.genres  = genresToString(movie.getGenres());
            me.releaseYear = movie.getReleaseYear();
            me.imgUrl = movie.getImgUrl();
            me.lengthInMinutes = movie.getLengthInMinutes();
            me.rating = movie.getRating();
            movieEntities.add(me);
        }
        return movieEntities;
    }
    public static List<Movie> toMovies(List<MovieEntity> movieEntities){
        List<Movie> movies = new ArrayList<>();
        for(MovieEntity me : movieEntities) {
            Movie movie = new Movie(me.title,me.description);
            movie.setId(me.apiId);
            movie.setGenres(Arrays.stream(me.genres.split(",")).map(Genre::valueOf).collect(Collectors.toList()));
            movie.setReleaseYear(me.releaseYear);
            movie.setImgUrl(me.imgUrl);
            movie.setLengthInMinutes(me.lengthInMinutes);
            movie.setRating(me.rating);
            //cannot load all data
            List<String> na = new ArrayList<>();
            na.add("Not available in offline mode.");
            movie.setDirectors(na);
            movie.setMainCast(na);
            movie.setWriters(na);
            movies.add(movie);
        }
        return movies;
    }
    public MovieEntity(){

    }
    public MovieEntity(String apiId, String title, String description, String genres, int releaseYear,
                       String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }
}