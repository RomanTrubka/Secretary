package com.ru.secretary.springwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task", schema = "public")
@JsonIgnoreProperties(value = { "user" })
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    //@JoinColumn(name="user_id", nullable=false)
    @JoinColumn()
    private User user; //TODO should it be like this or we need to keep user_id only?

    private String title;
    private String description = "";
    private Date startDate = new Date(); //TODO change Date to smth else
    private Date endDate = new Date();

    private boolean allDay = true;
    private TaskPriority priority = TaskPriority.LOW;

    public Task() {
    }

    public Task(User user, String title, String description, Date startDate, Date endDate,
                boolean allDay, TaskPriority priority) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
        this.priority = priority;
    }

    //Quick constructor
    public Task(User user, String title, String description, Date startDate) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public boolean isAllDay() { return allDay; }

    public void setAllDay(boolean allDay) { this.allDay = allDay; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priority=" + priority +
                ", allDay=" + allDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id != null ? id.equals(task.id) : task.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
