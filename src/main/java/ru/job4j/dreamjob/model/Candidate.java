package ru.job4j.dreamjob.model;

import java.util.Objects;

/**
 * Модель данных.
 * Класс описывает кандидата.
 */
public class Candidate {
    private int id;
    private String name;
    private String descr;
    private String created;
    private boolean visible;
    private City city;
    private byte[] photo;

    public Candidate(int id, String name, String descr, String created, Boolean visible, byte[] photo) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.created = created;
        this.visible = (visible != null) ? visible : false;
        this.photo = (photo != null) ? photo : new byte[]{0};
        setCity(new City(0, "none"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
