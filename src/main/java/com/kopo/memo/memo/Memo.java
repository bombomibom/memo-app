package com.kopo.memo.memo;

public class Memo {
	int idx;
	String title;
	String contents;
	
	Memo(){
		
	}
	
	Memo(String title, String contents){
		this.title = title;
		this.contents = contents;
	}
	
	Memo(int idx, String title, String contents){
		this.idx = idx;
		this.title = title;
		this.contents = contents;
	}
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
