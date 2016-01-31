package com.blstream.patronage.movieDataBundle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

import static java.lang.Math.pow;

class MovieRepresentation {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Rated")
    private String rated;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Awards")
    private String awards;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Metascore")
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Response")
    private String response;

    public Movie convertToMovie() {
        Movie movie = new Movie();

        movie.setTitle(getTitle());
        movie.setYear(getYear());
        movie.setRated(getRated());
        movie.setReleased(getReleased());
        movie.setRuntime(getRuntime());
        movie.setGenres(getGenre());
        movie.setDirector(getDirector());
        movie.setWriter(getWriter());
        movie.setActors(getActors());
        movie.setPlot(getPlot());
        movie.setLanguage(getLanguage());
        movie.setCountry(getCountry());
        movie.setAwards(getAwards());
        movie.setPoster(getPoster());
        movie.setMetascore(getMetascore());
        movie.setImdbRating(getImdbRating());
        movie.setImdbVotes(getImdbVotes());
        movie.setImdbID(getImdbID());
        movie.setType(getType());
        movie.setResponse(getResponse());

        return movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return Integer.parseInt(year);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public int getRuntime() {
        String minutes = runtime.split("\\s")[0];
        return Integer.parseInt(minutes);
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public URI getPoster() {
        return URI.create(poster);
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getMetascore() {
        return Integer.parseInt(metascore);
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public float getImdbRating() {
        return Float.parseFloat(imdbRating);
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public long getImdbVotes() {
        String[] split = imdbVotes.split(",");
        long votes = 0;
        for (int i = 0; i < split.length; i++) {
            votes += pow(1000, i) * Long.parseLong(split[i]);
        }
        return votes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getResponse() {
        return Boolean.parseBoolean(response);
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
