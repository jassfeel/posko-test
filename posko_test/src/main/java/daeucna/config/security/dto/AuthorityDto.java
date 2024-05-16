package daeucna.config.security.dto;

import daeucna.config.security.entity.Authority;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String authorityName;

    public static AuthorityDto from(Authority authority) {
        if(authority == null) return null;

        return AuthorityDto.builder()
                .authorityName(authority.getAuthorityName())
                .build();
    }
}