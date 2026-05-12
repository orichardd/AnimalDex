package animalDex.dex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "captures")
public class Capture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "captured_at", nullable = false)
    private LocalDateTime capturedAt;

    @Column(name = "is_first_discoverer")
    private Boolean isFirstDiscoverer = false;

    public Capture() {

    }

    @PrePersist
    public void prePersist() {
        this.capturedAt = LocalDateTime.now();
    }

    public Capture(User user, Animal animal, Boolean isFirstDiscoverer) {
        this.user = user;
        this.animal = animal;
        this.capturedAt = LocalDateTime.now();
        this.isFirstDiscoverer = isFirstDiscoverer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(LocalDateTime capturedAt) {
        this.capturedAt = capturedAt;
    }

    public Boolean getFirstDiscoverer() {
        return isFirstDiscoverer;
    }

    public void setFirstDiscoverer(Boolean firstDiscoverer) {
        isFirstDiscoverer = firstDiscoverer;
    }

    public Long getId() {
        return id;
    }
}