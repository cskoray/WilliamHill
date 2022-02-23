package com.williamHill.scoreBoard.dto.request;

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
public class AddTeamRequestDto {

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 35)
  private String name;
}
