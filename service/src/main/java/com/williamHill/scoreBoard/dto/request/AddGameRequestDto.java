package com.williamHill.scoreBoard.dto.request;

import com.williamHill.scoreBoard.validator.ValidUUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddGameRequestDto {

  @ValidUUID
  private String homeTeamKey;

  @ValidUUID
  private String awayTeamKey;

  @NotNull
  @NotEmpty
  private String gameDate;
}
