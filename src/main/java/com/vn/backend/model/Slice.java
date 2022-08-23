package com.vn.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slice")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Slice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private String image;
	// Slice: set = 0 display left, set = 1 display right
	private int type = 0;
	@Column(name = "delete_flag")
	private int deleteFlag;
}
