package com.example.semesterexam.core;

import com.example.semesterexam.manage.GameScreen;

import java.util.Objects;

public abstract class Weapon {
    protected String name = "";
    protected Character character;
    protected GameScreen gameScreen;
    protected long cycle = 700;
    protected String direction = "RIGHT";

    public Weapon(Character character, GameScreen gameScreen) {
        this.character = character;
        this.gameScreen = gameScreen;
    }

    public abstract void conduct();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return Objects.equals(name, weapon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public abstract String getAttackName();

    public abstract String getName();

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
