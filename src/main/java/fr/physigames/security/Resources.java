package fr.physigames.security;

import lombok.Getter;

@Getter
public enum Resources {
    PHYSICAL_RELEASE("physical-release");

    private final String resourceName;

    Resources(String resourceName) {
        this.resourceName = resourceName;
    }

}
