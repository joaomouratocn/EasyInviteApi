package br.com.arthivia.EasyInviteApi.models.entities;

import br.com.arthivia.EasyInviteApi.models.dtos.FaqsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faqs")
public class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
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
