package com.blstream.patronage.app.views;

import com.blstream.patronage.app.model.Actor;
import io.dropwizard.views.View;

import java.util.List;

@SuppressWarnings("unused")
public class ActorView extends View {
    private final List<Actor> actor;

    public ActorView(List<Actor> actor) {
        super("actor.mustache");
        this.actor = actor;
    }

    public List<Actor> getActor() {
        return actor;
    }
}
