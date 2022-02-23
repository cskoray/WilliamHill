package com.williamHill.scoreBoard.repository.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "game")
public class Game implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "game_key", nullable = false)
  private String gameKey;

  @OneToOne
  @JoinColumn(name = "home_team_key", referencedColumnName = "team_key")
  private Team homeTeam;

  @OneToOne
  @JoinColumn(name = "away_team_key", referencedColumnName = "team_key")
  private Team awayTeam;

  @Column(name = "home_score", nullable = false)
  private int homeScore;

  @Column(name = "away_score", nullable = false)
  private int awayScore;

  @Column(name = "game_date", updatable = false, nullable = false)
  private Timestamp gameDate;
}
