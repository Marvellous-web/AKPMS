package com.idsargus.akpmsadminservice.wspojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WsResponseDto {
	private String message;
	private String wsfilehandle;
	private String exporter;
	private float progress;
	private String status;
	private String filepath;
	private String timeTaken;
	private String QueryParams;
}
