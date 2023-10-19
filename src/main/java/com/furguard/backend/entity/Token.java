package com.furguard.backend.entity;

import com.furguard.backend.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TokenId;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Builder.Default
    private boolean expired = false;

    @Builder.Default
    private boolean revoked = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "t_user_id")
    private User user;
}
