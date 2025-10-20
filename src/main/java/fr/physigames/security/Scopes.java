package fr.physigames.security;

import lombok.Getter;

@Getter
public enum Scopes {
    READ("read"),
    EDIT("edit"),
    DELETE("delete"),
    ADD("add");

    private final String scopeName;

    Scopes(String scopeName) {
        this.scopeName = scopeName;
    }

}
