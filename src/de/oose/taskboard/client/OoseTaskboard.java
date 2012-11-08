package de.oose.taskboard.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class OoseTaskboard implements EntryPoint {

	
	@Override
	public void onModuleLoad() {
		RootLayoutPanel root = RootLayoutPanel.get();
		TaskboardPresenter presenter = new TaskboardPresenter();
		TaskboardView view = new TaskboardView(presenter);
		presenter.setView(view);
		presenter.createTask();
		root.add(view);
	}

}
