package animalDex.dex.model;

import jakarta.persistence.*;

@Entity(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String scientificName;

    @Column(nullable = false)
    private String commonName;

    @Column(nullable = false)
    private String weight;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String diet;

    @Column(nullable = false)
    private Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "first_catched_by")
    private User firstCachedBy;

    @Column(nullable = false)
    private Long foundBy = 1L;

    public Animal(String scientificName, String commonName, String weight, String size, String diet, Rarity rarity, User firstCachedBy) {
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.weight = weight;
        this.size = size;
        this.diet = diet;
        this.rarity = rarity;
        this.firstCachedBy = firstCachedBy;
    }

    public Long getId() {
        return Id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public User getFirstCachedBy() {
        return firstCachedBy;
    }

    public void setFirstCachedBy(User firstCachedBy) {
        this.firstCachedBy = firstCachedBy;
    }

    public Animal() {

    }

    public void IncreaseCaches(){
        this.foundBy++;
    }

    @Override
    public String toString() {
        return """
            {
              "id": %d,
              "scientificName": "%s",
              "commonName": "%s",
              "weight": "%s",
              "height": "%s",
              "diet": "%s",
              "rarity": "%s"
            }
            """.formatted(
                Id,
                scientificName,
                commonName,
                weight,
                size,
                diet,
                rarity
        );
    }
}
