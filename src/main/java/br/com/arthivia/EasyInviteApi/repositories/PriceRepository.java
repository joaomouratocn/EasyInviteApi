package br.com.arthivia.EasyInviteApi.repositories;

import br.com.arthivia.EasyInviteApi.models.entities.PriceEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, UUID> {
    Optional<PriceEntity> findByEnableTrue();

    @Modifying
    @Transactional
    @Query("UPDATE PriceEntity p SET p.enable = false WHERE p.id = :id")
    void disablePriceById(@Param("id") UUID id);
}
