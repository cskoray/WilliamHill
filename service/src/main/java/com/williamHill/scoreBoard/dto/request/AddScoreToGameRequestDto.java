package com.williamHill.scoreBoard.dto.request;

import com.williamHill.scoreBoard.validator.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddScoreToGameRequestDto {

  @ValidUUID
  private String gameKey;

  @ValidUUID
  private String teamKey;

  @ValidUUID
  private String playerKey;

  @Range(min = 0, max = 120)
  private int scoreMinute;
}
