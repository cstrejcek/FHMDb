package at.ac.fhcampuswien.fhmdb.models;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here
    private List<Genre> genres;

    public String getGenres() {
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

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Movie(String title, String description,List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

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
}
