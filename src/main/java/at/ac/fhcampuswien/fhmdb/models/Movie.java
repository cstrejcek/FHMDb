package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String description;
    // TODO add more properties here
    private List<Genre> genres;
    private List<String> mainCast;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> writers;
    private double rating;

    public String getGenreString() {
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
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Movie(String title, String description,List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }
    public Movie(String title, String description,List<Genre> genres,List<String> mainCast) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.mainCast = mainCast;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getMainCast(){
        return mainCast;
    }
    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }
    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public static List<Movie> initializeMovies() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        return movieAPI.getAllMovies();
    }
    /*
    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        List<Genre> genres;
        String csvFile = "src/main/resources/at/ac/fhcampuswien/fhmdb/movies_metadata.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] nextLine = reader.readNext();
            int maxLines = 50;
            int line = 1;
            while ((nextLine = reader.readNext()) != null && line <= maxLines ) {
                // nextLine[] is an array of values from the line
                if(nextLine[7].equals("en") && !nextLine[8].equals("") && nextLine[8] != null && !nextLine[9].equals("") && nextLine[9] != null) {
                    String genresCsv = nextLine[3].toUpperCase();
                    genres = new ArrayList<>();
                    for (Genre genre : Genre.values()) {
                        if(genresCsv.contains(genre.toString().toUpperCase().replace('_',' '))){
                            genres.add(genre);
                        }
                    }
                    Movie movie = new Movie(nextLine[8],nextLine[9],genres);
                    movies.add(movie);
                    line++;
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

     */
    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                ", directors=" + directors +
                ", writers=" + writers +
                ", mainCast=" + mainCast +
                ", rating=" + rating +
                '}';
    }
}
