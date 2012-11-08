/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.oose.taskboard.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

import de.oose.taskboard.shared.Task;
import de.oose.taskboard.shared.Task.State;

/**
 * Example file.
 */
public class TaskboardView extends Composite {

	/**
	 * The UiBinder interface used by this example.
	 */

	interface Binder extends UiBinder<Widget, TaskboardView> {
	}

	/**
	 * The constants used in this Content Widget.
	 */

	public static interface CwConstants extends Constants {
		@DefaultStringValue("Id")
		String idHeader();

		@DefaultStringValue("State")
		String stateHeader();

		@DefaultStringValue("Description")
		String descriptionHeader();

		@DefaultStringValue("Owner")
		String ownerHeader();

		@DefaultStringValue("Private")
		String privateHeader();
	}

	/**
	 * The main CellTable.
	 */

	@UiField(provided = true)
	CellTable<Task> cellTable;

	/**
	 * The pager used to change the range of data.
	 */

	@UiField(provided = true)
	SimplePager pager;

	/**
	 * An instance of the constants.
	 */

	private final CwConstants constants = GWT.create(CwConstants.class);

	private ListDataProvider<Task> dataProvider = new ListDataProvider<Task>();

	private TaskboardPresenter taskboardPresenter;

	/**
	 * Constructor.
	 * 
	 * @param taskboardPresenter
	 * 
	 * @param constants
	 *            the constants
	 */
	public TaskboardView(TaskboardPresenter taskboardPresenter) {
		this.taskboardPresenter = taskboardPresenter;
		initWidget(createWidget());
	}

	Widget createWidget() {
		// Create a CellTable.

		// Set a key provider that provides a unique key for each contact. If
		// key is
		// used to identify contacts when fields (such as the name and address)
		// change.

		final ProvidesKey<Task> keyProvider = new ProvidesKey<Task>() {
			public Object getKey(Task task) {
				return task == null ? null : task.getId();
			}
		};
		cellTable = new CellTable<Task>(keyProvider);
		ListHandler<Task> columnSortHandler = new ListHandler<Task>(
				dataProvider.getList());
		cellTable.addColumnSortHandler(columnSortHandler);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(cellTable);

		// Add a selection model so we can select cells.
		final MultiSelectionModel<Task> selectionModel = new MultiSelectionModel<Task>(
				keyProvider);
		cellTable.setSelectionModel(selectionModel);

		// Initialize the columns.
		initTableColumns(selectionModel, columnSortHandler);

		dataProvider.addDataDisplay(cellTable);

		// Create the UiBinder.
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;
	}

	public void addTask(Task task) {
		dataProvider.getList().add(task);
	}

	public void setTasks(List<Task> tasks) {
		dataProvider.setList(tasks);
	}

	/**
	 * Add the columns to the table.
	 */

	private void initTableColumns(final SelectionModel<Task> selectionModel, ListHandler<Task> columnSortHandler) {

		Column<Task, String> idColumn = new Column<Task, String>(new TextCell()) {
			@Override
			public String getValue(Task object) {
				return "" + (object.getId() == 0 ? "" : object.getId());
			}
		};
		cellTable.addColumn(idColumn, constants.idHeader());
		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<Task>() {
			
			@Override
			public int compare(Task o1, Task o2) {
				return Integer.valueOf(o1.getId()).compareTo(o2.getId());
			}
		});

		Column<Task, String> descriptionColumn = new Column<Task, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Task object) {
				return object.getDescription();
			}
		};
		cellTable.addColumn(descriptionColumn, constants.descriptionHeader());
		descriptionColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
			public void update(int index, Task object, String value) {
				// Called when the user changes the value.
				object.setDescription(value);
				taskboardPresenter.saveTask(object);
				dataProvider.refresh();
			}
		});
		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<Task>() {
			
			@Override
			public int compare(Task o1, Task o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
		});

		List<String> names = new ArrayList<String>();
		State[] values = Task.State.values();
		for (State state : values) {
			names.add(state.name());
		}

		SelectionCell categoryCell = new SelectionCell(names);
		Column<Task, String> stateColumn = new Column<Task, String>(
				categoryCell) {
			@Override
			public String getValue(Task object) {
				return object.getState().name();
			}
		};
		cellTable.addColumn(stateColumn, constants.stateHeader());
		stateColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
			public void update(int index, Task object, String value) {
				for (Task.State state : Task.State.values()) {
					if (state.name().equals(value)) {
						object.setState(state);
					}
				}
				taskboardPresenter.saveTask(object);
				dataProvider.refresh();
			}
		});

		cellTable.addColumn(new Column<Task, String>(new TextCell()) {
			@Override
			public String getValue(Task object) {
				return object.getOwner();
			}
		}, constants.ownerHeader());

		Column<Task, Boolean> checkColumn = new Column<Task, Boolean>(
				new CheckboxCell(true)) {
			@Override
			public Boolean getValue(Task object) {
				return object.isHide();
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<Task, Boolean>() {
			public void update(int index, Task object, Boolean value) {
				object.setHide(value);
				taskboardPresenter.saveTask(object);
			}
		});
		cellTable.addColumn(checkColumn, constants.privateHeader());

	}
}