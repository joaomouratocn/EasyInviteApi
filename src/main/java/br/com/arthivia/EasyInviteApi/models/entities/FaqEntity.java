package br.com.arthivia.EasyInviteApi.models.entities;

import br.com.arthivia.EasyInviteApi.models.dtos.FaqsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faqs")
public class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String question;
    private String answer;
    @Column(name = "display_order")
    private Integer order;

    public FaqEntity(FaqsDto faqsDto) {
        this.id = faqsDto.id();
        this.question = faqsDto.question();
        this.answer = faqsDto.answer();
        this.order = faqsDto.order();
    }
}
