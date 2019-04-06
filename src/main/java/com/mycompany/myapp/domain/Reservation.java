package com.mycompany.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reservation")
public class Reservation {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "poolId")
    private Pool pool;

    @NotNull
    @Column(unique = false, nullable = false)
    private int count;

    public Reservation(@NotNull User user, @NotNull Pool pool, @NotNull int count) {
        this.user = user;
        this.pool = pool;
        this.count = count;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
