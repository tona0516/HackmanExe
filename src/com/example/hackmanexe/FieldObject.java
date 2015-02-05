package com.example.hackmanexe;

public class FieldObject {
	protected FrameInfo currentFrameInfo;

	public FieldObject(FrameInfo currentFrameInfo) {
		this.currentFrameInfo = currentFrameInfo;
	}

	public FrameInfo getCurrentFrameInfo() {
		return currentFrameInfo;
	}
}
