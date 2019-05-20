package com.mycompany.myapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @Column(name = "from", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date from;

    @Column(name = "to", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date to;


    public Reservation(
        @NotNull User user,
        @NotNull Pool pool,
        @NotNull int count,
        @NotNull Date from,
        @NotNull Date to
    ) {
        this.user = user;
        this.pool = pool;
        this.count = count;
        this.from = from;
        this.to = to;
    }

    public Reservation() {
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
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
