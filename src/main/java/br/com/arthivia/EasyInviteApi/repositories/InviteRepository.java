package br.com.arthivia.EasyInviteApi.repositories;

import br.com.arthivia.EasyInviteApi.models.entities.InviteEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InviteRepository extends JpaRepository<InviteEntity, UUID> {
    @Query("SELECT COUNT(i) FROM InviteEntity i")
    long getInviteAmount();

    Optional<InviteEntity> findBySlug(@Valid String slug);

    Optional<List<InviteEntity>> findByUserId(@Valid @NotBlank UUID userId);
}
