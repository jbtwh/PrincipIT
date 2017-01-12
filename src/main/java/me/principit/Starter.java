package me.principit;

import com.google.common.collect.Lists;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.*;

public class Starter extends Application {

    private TableView table = new TableView();
    private List<Task> current = new ArrayList<>();

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("principit");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        table.setEditable(true);

        TableColumn name = new TableColumn("name");
        name.setMinWidth(200);
        name.setCellValueFactory(
                new PropertyValueFactory<Task, String>("name"));
        TableColumn pid = new TableColumn("pid");
        pid.setMinWidth(200);
        pid.setCellValueFactory(
                new PropertyValueFactory<Task, String>("pid"));
        TableColumn memory = new TableColumn("memory");
        memory.setMinWidth(200);
        memory.setCellValueFactory(
                new PropertyValueFactory<Task, Long>("memory"));

        current = getData();
        table.setItems(FXCollections.observableArrayList(current));
        table.getColumns().addAll(name, pid, memory);

        Button btnSave = new Button();
        btnSave.setText("export to xml");
        btnSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    saveFile(TaskService.toXmlString(FXCollections.observableArrayList(current)), file);
                }
            }
        });

        Button btnOpen = new Button();
        btnOpen.setText("import from xml");
        btnOpen.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    List nw = TaskService.fromXmlString(openFile(file));
                    Collection<Task> deleted = (Collection<Task>)CollectionUtils.subtract(current, nw);
                    Collection<Task> newResult = (Collection<Task>)CollectionUtils.subtract(nw, current);

                    System.out.println(deleted);
                    System.out.println(newResult);
                    current = nw;
                    table.setItems(FXCollections.observableArrayList(current));

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("CHANGES");
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    TextArea textArea = new TextArea();
                    textArea.setText("deleted items -" + deleted.toString() + "\nupdated/added items - "+ newResult.toString());
                    VBox vbox = new VBox();
                    vbox.getChildren().addAll(textArea);
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setPadding(new Insets(15));

                    dialogStage.setScene(new Scene(vbox));
                    dialogStage.show();
                }
            }
        });

        Button btnSavexls = new Button();
        btnSavexls.setText("export to excel");
        btnSavexls.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    BarChart.create(current, file);
                }
            }
        });

        Button btnGroup = new Button();
        btnGroup.setText("group by memory");
        btnGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Map<String, Task> grouped = new HashMap();
                for(Task t : current) {
                    String key  = t.getName();
                    if(grouped.containsKey(key)){
                        Task t2 = grouped.get(key);
                        t2.setMemory(t2.getMemory() + t.getMemory());
                    } else{
                        grouped.put(key, t);
                    }
                }
                current = new ArrayList(grouped.values());
                table.setItems(FXCollections.observableArrayList(current));
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(btnSave, btnOpen, btnSavexls, btnGroup, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String openFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return stringBuffer.toString();
    }

    public static void main(String... args) throws Exception {
        launch(args);
    }

    private List<Task> getData() {
        try {

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "chcp 65001 && tasklist /fo csv /nh");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            List<Task> tasks = new ArrayList<>();
            r.readLine(); //skip first
            while ((line = r.readLine()) != null) {
                String[] splitted = line.replaceAll("\"", "").split(",");
                //System.out.println(line);
                Task task = new Task(splitted[0], splitted[1], Long.valueOf(splitted[4].replaceAll("[\\s\\xA0K|KB]+", "")));
                tasks.add(task);
            }
            //System.out.println(tasks);
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }
}
