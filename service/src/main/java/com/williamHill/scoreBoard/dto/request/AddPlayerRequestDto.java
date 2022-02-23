package com.williamHill.scoreBoard.dto.request;

import com.williamHill.scoreBoard.validator.ValidUUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPlayerRequestDto {

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 35)
  private String name;

  @ValidUUID
  private String teamKey;
}
