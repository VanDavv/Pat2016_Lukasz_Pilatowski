package com.blstream.patronage.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Entity
@Table(name = "Movies")
@SuppressWarnings("unused")
public class Movie {
    private long id;

    @JsonProperty
    @NotNull
    private String title;
    @JsonProperty
    private List<Actor> actors;
    private int year;
    private String rated;
    private String released;
    private int runtime;
    private String genres;
    private String director;
    private String writer;
    private String detailedActors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private URI poster;
    private int metascore;
    private float imdbRating;
    private long imdbVotes;
    private String imdbID;
    private String type;
    private boolean response;

    public Movie() {

    }

    public Movie(String title, List<Actor> actors) {
        this.title = title;
        this.actors = actors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Movie setDetailedMovieData(com.blstream.patronage.movieDataBundle.Movie detailedMovieData) {
        if (detailedMovieData == null) return this;
        this.setYear(detailedMovieData.getYear());
        this.setRated(detailedMovieData.getRated());
        this.setReleased(detailedMovieData.getReleased());
        this.setRuntime(detailedMovieData.getRuntime());
        this.setGenres(detailedMovieData.getGenres());
        this.setDirector(detailedMovieData.getDirector());
        this.setWriter(detailedMovieData.getWriter());
        this.setDetailedActors(detailedMovieData.getActors());
        this.setPlot(detailedMovieData.getPlot());
        this.setLanguage(detailedMovieData.getLanguage());
        this.setCountry(detailedMovieData.getCountry());
        this.setAwards(detailedMovieData.getAwards());
        this.setPoster(detailedMovieData.getPoster());
        this.setMetascore(detailedMovieData.getMetascore());
        this.setImdbRating(detailedMovieData.getImdbRating());
        this.setImdbVotes(detailedMovieData.getImdbVotes());
        this.setImdbID(detailedMovieData.getImdbID());
        this.setType(detailedMovieData.getType());
        this.setResponse(detailedMovieData.isResponse());

        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    public long getId() {
        return id;
    }

    @Column(name = "movieName", nullable = false)
    public String getTitle() {
        return title;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movie")
    public List<Actor> getActors() {
        return actors;
    }

    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "rated")
    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    @Column(name = "released")
    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    @Column(name = "runtime")
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Column(name = "genres")
    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Column(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Column(name = "writer")
    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Column(name = "detailedActors")
    public String getDetailedActors() {
        return detailedActors;
    }

    public void setDetailedActors(String detailedActors) {
        this.detailedActors = detailedActors;
    }

    @Column(name = "plot")
    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "awards")
    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    @Column(name = "poster")
    public URI getPoster() {
        return poster;
    }

    public void setPoster(URI poster) {
        this.poster = poster;
    }

    @Column(name = "metascore")
    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    @Column(name = "imdbRating")
    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Column(name = "imdbVotes")
    public long getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(long imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @Column(name = "imdbID")
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "response")
    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (year != movie.year) return false;
        if (runtime != movie.runtime) return false;
        if (metascore != movie.metascore) return false;
        if (Float.compare(movie.imdbRating, imdbRating) != 0) return false;
        if (imdbVotes != movie.imdbVotes) return false;
        if (response != movie.response) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (actors != null ? !actors.equals(movie.actors) : movie.actors != null) return false;
        if (rated != null ? !rated.equals(movie.rated) : movie.rated != null) return false;
        if (released != null ? !released.equals(movie.released) : movie.released != null) return false;
        if (genres != null ? !genres.equals(movie.genres) : movie.genres != null) return false;
        if (director != null ? !director.equals(movie.director) : movie.director != null) return false;
        if (writer != null ? !writer.equals(movie.writer) : movie.writer != null) return false;
        if (detailedActors != null ? !detailedActors.equals(movie.detailedActors) : movie.detailedActors != null)
            return false;
        if (plot != null ? !plot.equals(movie.plot) : movie.plot != null) return false;
        if (language != null ? !language.equals(movie.language) : movie.language != null) return false;
        if (country != null ? !country.equals(movie.country) : movie.country != null) return false;
        if (awards != null ? !awards.equals(movie.awards) : movie.awards != null) return false;
        if (poster != null ? !poster.equals(movie.poster) : movie.poster != null) return false;
        if (imdbID != null ? !imdbID.equals(movie.imdbID) : movie.imdbID != null) return false;
        return !(type != null ? !type.equals(movie.type) : movie.type != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (actors != null ? actors.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (rated != null ? rated.hashCode() : 0);
        result = 31 * result + (released != null ? released.hashCode() : 0);
        result = 31 * result + runtime;
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (detailedActors != null ? detailedActors.hashCode() : 0);
        result = 31 * result + (plot != null ? plot.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (awards != null ? awards.hashCode() : 0);
        result = 31 * result + (poster != null ? poster.hashCode() : 0);
        result = 31 * result + metascore;
        result = 31 * result + (imdbRating != +0.0f ? Float.floatToIntBits(imdbRating) : 0);
        result = 31 * result + (int) (imdbVotes ^ (imdbVotes >>> 32));
        result = 31 * result + (imdbID != null ? imdbID.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (response ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", actors=" + actors +
                ", year=" + year +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime=" + runtime +
                ", genres='" + genres + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", detailedActors='" + detailedActors + '\'' +
                ", plot='" + plot + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", awards='" + awards + '\'' +
                ", poster=" + poster +
                ", metascore=" + metascore +
                ", imdbRating=" + imdbRating +
                ", imdbVotes=" + imdbVotes +
                ", imdbID='" + imdbID + '\'' +
                ", type='" + type + '\'' +
                ", response=" + response +
                '}';
    }
}
