package models;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by VanDavv on 2015-12-13.
 */
@Entity
@Table(name="Actors")
@NamedQueries({
        @NamedQuery(
                name = "models.Actor.findAll",
                query = "Select a from Actors a"
        )
})
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthDate", nullable = false)
    private String birthDate;

    public Actor() {
    }

    public Actor(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (id != actor.id) return false;
        if (name != null ? !name.equals(actor.name) : actor.name != null) return false;
        return !(birthDate != null ? !birthDate.equals(actor.birthDate) : actor.birthDate != null);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
