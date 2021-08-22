package net.member.action;

public class ActionForward {
	private boolean isRedirect=false;
	private String path=null;
	
	public boolean isRedirect(){
		return isRedirect;//true면 Redirect, false면 forward
	}
	
	public String getPath(){
		return path;//경로를 String타입으로 리턴한다.
	}
	
	public void setRedirect(boolean b){
		isRedirect=b;
	}
	
	public void setPath(String string){
		path=string;
	}
}