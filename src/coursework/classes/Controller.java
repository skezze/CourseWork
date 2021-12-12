package coursework.classes;

import coursework.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;


public class Controller {
    @FXML
    private void initialize() {
        queueTable.getColumns().setAll(genQTable());
        doneTable.getColumns().setAll(genQTable());
    }

    @FXML
    TableView<Process> queueTable;
    @FXML
    TableView<Process> doneTable;

    ObservableList<Process> qTableList = FXCollections.observableArrayList();
    ObservableList<Process> dTableList = FXCollections.observableArrayList();

    private ArrayList<TableColumn<Process, String>> genQTable() {
        TableColumn<Process, String> idColumn = new TableColumn<>("ID");
        TableColumn<Process, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Process, String> priorColumn = new TableColumn<>("Priority");
        TableColumn<Process, String> statusColumn = new TableColumn<>("Status");
        TableColumn<Process, String> tickColumn = new TableColumn<>("TickWorks");
        TableColumn<Process, String> memColumn = new TableColumn<>("Memory");
        TableColumn<Process, String> timeInColumn = new TableColumn<>("TimeIn");
        TableColumn<Process, String> burstColumn = new TableColumn<>("BursTime");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tickColumn.setCellValueFactory(new PropertyValueFactory<>("tickWorks"));
        memColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("bursTime"));

        priorColumn.setVisible(false);
        tickColumn.setVisible(false);
        memColumn.setVisible(false);
        burstColumn.setVisible(false);

        ArrayList<TableColumn<Process, String>> _tmp = new ArrayList<>();
        _tmp.add(idColumn);
        _tmp.add(nameColumn);
        _tmp.add(priorColumn);
        _tmp.add(statusColumn);
        _tmp.add(tickColumn);
        _tmp.add(memColumn);
        _tmp.add(timeInColumn);
        _tmp.add(burstColumn);
        return _tmp;
    }

    public void updateTable(Queue q, ArrayList<Process> a) {
        qTableList.setAll(q.getQueue());
        queueTable.setItems(qTableList);
        queueTable.refresh();
        dTableList.setAll(a);
        doneTable.setItems(dTableList);
        doneTable.refresh();
    }

    @FXML
    Button runBTN;
    @FXML
    Button stopBTN;

    @FXML
    protected void runBTN_Click() {
        Main.emuThread.start();

        runBTN.setDisable(true);
        stopBTN.setDisable(false);
    }

    @FXML
    protected void stopBTN_Click() {
        Main.emuThread.interrupt();

        MemScheduler.clearMem();
        ClockGenerator.clearTime();
        Main.emuThread = new Thread(new Launcher());
        queueTable.setItems(null);
        doneTable.setItems(null);

        runBTN.setDisable(false);
        stopBTN.setDisable(true);
    }

}
