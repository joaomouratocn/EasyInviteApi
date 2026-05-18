package br.com.arthivia.EasyInviteApi.repositories;

import br.com.arthivia.EasyInviteApi.models.entities.InviteEntity;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InviteRepository extends JpaRepository<InviteEntity, UUID> {
    Optional<InviteEntity> findBySlug(@Valid String slug);
}
