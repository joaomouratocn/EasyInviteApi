package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.repositories.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public Long getInviteNumber(){
        return (long) inviteRepository.findAll().size();
    }
}
