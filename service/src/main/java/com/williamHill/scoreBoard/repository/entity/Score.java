package com.williamHill.scoreBoard.repository.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score")
public class Score implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "score_key", nullable = false)
  private String scoreKey;

  @OneToOne
  @JoinColumn(name = "game_key", referencedColumnName = "game_key")
  private Game game;

  @OneToOne
  @JoinColumn(name = "team_key", referencedColumnName = "team_key")
  private Team team;

  @Column(name = "score_minute", nullable = false)
  private int scoreMinute;

  @OneToOne
  @JoinColumn(name = "player_key", referencedColumnName = "player_key")
  private Player player;
}
