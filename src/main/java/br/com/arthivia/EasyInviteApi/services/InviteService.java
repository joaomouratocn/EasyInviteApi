package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.InviteDto;
import br.com.arthivia.EasyInviteApi.repositories.InviteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public Long getInviteNumber(){
        return inviteRepository.getInviteAmount();
    }

    public InviteDto getInvite(@Valid String slug) {
         var invite =  inviteRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Invite not found!"));
         switch (invite.getStatus()){
            case "EXP" -> throw new RuntimeException("Invite wait expired");
            case "WAP" -> throw new RuntimeException("Invite wait approval");
            case "ACT" -> {
                return new InviteDto(invite);
            }
            default -> throw new RuntimeException("Invite without status");
        }
    }
}
