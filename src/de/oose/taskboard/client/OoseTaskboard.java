package de.oose.taskboard.client;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;

import de.oose.taskboard.shared.Task;

public class OoseTaskboard implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootLayoutPanel root = RootLayoutPanel.get();
		TaskboardPresenter presenter = new TaskboardPresenter();
		TaskboardView view = createViewLocalDataProvider(presenter);
		presenter.setView(view);
		presenter.createTask();
		root.add(view);
	}

	private TaskboardView createViewLocalDataProvider(
			TaskboardPresenter presenter) {
		ListDataProvider<Task> dataProvider = new ListDataProvider<Task>();
		ListHandler<Task> columnSortHandler = new ListHandler<Task>(
				dataProvider.getList());
		presenter.setDataProvider(dataProvider);

		TaskboardView view = new TaskboardView(presenter, dataProvider,
				columnSortHandler);
		return view;
	}
/*
	private void createAsyncDataprovider() {
		AsyncDataProvider<Task> dataProvider = new AsyncDataProvider<Task>() {
			@Override
			protected void onRangeChanged(HasData<Task> display) {
				final Range range = display.getVisibleRange();

				// Get the ColumnSortInfo from the table.
				final ColumnSortList sortList = table.getColumnSortList();

				// This timer is here to illustrate the asynchronous nature of
				// this data
				// provider. In practice, you would use an asynchronous RPC call
				// to
				// request data in the specified range.
				new Timer() {
					@Override
					public void run() {
						int start = range.getStart();
						int end = start + range.getLength();
						// This sorting code is here so the example works. In
						// practice, you
						// would sort on the server.
						Collections.sort(CONTACTS, new Comparator<Task>() {
							public int compare(Task o1, Task o2) {
								if (o1 == o2) {
									return 0;
								}

								// Compare the name columns.
								int diff = -1;
								if (o1 != null) {
									diff = (o2 != null) ? o1.name
											.compareTo(o2.name) : 1;
								}
								return sortList.get(0).isAscending() ? diff
										: -diff;
							}
						});
						List<Contact> dataInRange = CONTACTS
								.subList(start, end);

						// Push the data back into the list.
						table.setRowData(start, dataInRange);
					}
				}.schedule(2000);
			}
		};

	}
*/
}
