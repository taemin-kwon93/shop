package net.member.action;

public class ActionForward {
	private boolean isRedirect=false;
	private String path=null;
	
	public boolean isRedirect(){
		return isRedirect;//true�� Redirect, false�� forward
	}
	
	public String getPath(){
		return path;//��θ� StringŸ������ �����Ѵ�.
	}
	
	public void setRedirect(boolean b){
		isRedirect=b;
	}
	
	public void setPath(String string){
		path=string;
	}
}