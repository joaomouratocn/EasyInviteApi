package br.com.arthivia.EasyInviteApi.models.entities;

import br.com.arthivia.EasyInviteApi.models.dtos.PriceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private BigDecimal price;
    @Column(insertable = false, updatable = false)
    private boolean enable;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public PriceEntity(PriceDto priceDto) {
        this.price = priceDto.price();
    }
}
