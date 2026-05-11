package br.com.arthivia.EasyInviteApi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "faqs")
public class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String question;
    private String answer;
    @Column(name = "display_order")
    private Integer order;
}
