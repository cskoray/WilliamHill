package com.williamHill.scoreBoard.repository.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
public class Team implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @Column(name = "team_key", nullable = false)
  private String teamKey;

  @Column(name = "name")
  private String name;

  @Exclude
  @OneToMany(mappedBy = "team", fetch = LAZY)
  private List<Player> players;
}
