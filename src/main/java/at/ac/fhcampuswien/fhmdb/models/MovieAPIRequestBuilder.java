package at.ac.fhcampuswien.fhmdb.models;

import okhttp3.HttpUrl;

public class MovieAPIRequestBuilder {
    private final HttpUrl.Builder urlBuilder;

    public MovieAPIRequestBuilder(String baseUrl) {
        this.urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
    }

    public MovieAPIRequestBuilder query(String query) {
        if (query != null && !query.isEmpty()) {
            urlBuilder.addQueryParameter("query", query);
        }
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        if (genre != null && !genre.isEmpty()) {
            urlBuilder.addQueryParameter("genre", genre);
        }
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        if (releaseYear != null && !releaseYear.isEmpty()) {
            urlBuilder.addQueryParameter("releaseYear", releaseYear);
        }
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String ratingFrom) {
        if (ratingFrom != null && !ratingFrom.isEmpty()) {
            urlBuilder.addQueryParameter("ratingFrom", ratingFrom);
        }
        return this;
    }

    public String build() {
        return urlBuilder.build().toString();
    }
}
