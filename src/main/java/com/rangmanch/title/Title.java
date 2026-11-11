package com.rangmanch.title;

import jakarta.persistence.*;

@Entity
@Table(name = "titles")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 180)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TitleType type;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(length = 80)
    private String genre;

    @Column(length = 2000)
    private String synopsis;

    protected Title() { }

    public Title(String name, TitleType type, Integer releaseYear, String genre, String synopsis) {
        this.name = name;
        this.type = type;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.synopsis = synopsis;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public TitleType getType() { return type; }
    public Integer getReleaseYear() { return releaseYear; }
    public String getGenre() { return genre; }
    public String getSynopsis() { return synopsis; }
}