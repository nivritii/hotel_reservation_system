package com.hotel.booking.entitymodel;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="room_type_id")
    private Integer roomTypeId;

    @NotNull
    @Column(name="customer_id")
    private Integer customerId;

    @NotNull
    private Integer quantity;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="start_date")
    private LocalDate startDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="end_date")
    private LocalDate endDate;

    private Boolean cancelled;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", roomTypeId=" + roomTypeId +
                ", customerId=" + customerId +
                ", quantity=" + quantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cancelled=" + cancelled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Reservation(){

    }

    public Reservation(@NotNull Integer roomTypeId, @NotNull Integer customerId, @NotNull Integer quantity,
                       LocalDate startDate, LocalDate endDate) {
        this.roomTypeId = roomTypeId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
