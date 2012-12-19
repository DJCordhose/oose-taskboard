package de.oose.taskboard.client;

import com.google.gwt.core.client.JavaScriptObject;

public final class TaskJsni extends JavaScriptObject {
	protected TaskJsni() {

	}

	public final native static TaskJsni createInstance(String json) /*-{
		return JSON.parse(json);
	}-*/;

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native String getState() /*-{
		return this.state;
	}-*/;

	public final native String getDescription() /*-{
		return this.description;
	}-*/;

	public final native String getOwner() /*-{
		return this.owner;
	}-*/;

	public final native boolean isHide() /*-{
		return this.hide;
	}-*/;
}
