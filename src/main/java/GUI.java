import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;


public class GUI extends Application {

    MemberArchive members;
    Stage primaryStage;
    Scene mainScene;
    Scene addScene;
    Scene delScene;
    Scene upgradeScene;
    VBox detailsContainer;

    public GUI() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        members = new MemberArchive();

        // Sets up the detail container
        setUpDetailContainer();

        // Populates the system with a few example users
        populateMembers();


        // Sets up the various scenes
        setUpMainScene();
        setUpAddScene();
        setUpDeleteScene();
        setUpUpgradeScene();

        // Primary stage settings
        primaryStage.setTitle("Bonus member application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Populates the member archive with some example members.
     * Used for testing and debugging
     */
    public void populateMembers() {
        // Basic silver
        for (int i = 0; i < 4; i++) {
            members.addMember(new Personals("Name" + i, "Surname" + i, "email" + i, "password" + i), LocalDate.now(), 0);
        }
        // Basic member qualifying for silver
        for (int i = 0; i < 3; i++) {
            members.addMember(new Personals("NameS" + i, "SurnameS" + i, "emailS" + i, "passwordS" + i), LocalDate.now(), 30000);
        }
        // Basic member qualifying for silver and gold
        for (int i = 0; i < 4; i++) {
            members.addMember(new Personals("NameG" + i, "SurnameG" + i, "emailG" + i, "passwordG" + i), LocalDate.now(), 75000);
        }
    }

    // Main scene variables
    VBox mainContainer = new VBox();
    TableView memberList = new TableView();
    BorderPane rootMain = new BorderPane();

    /**
     * Sets up the mains scene which contains a list of the bonus members
     */
    private void setUpMainScene() {

        // Root of main scene
        rootMain.setTop(setUpMenuBar());

        // Table view of main scene
        memberList = setUpMainTable();

        // Center container for the table view
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().add(memberList);

        // Fills in the table view
        updateMainScene();

        rootMain.setCenter(mainContainer);

        mainScene = new Scene(rootMain, 800, 600);

    }

    /**
     * Sets up the table view visible in the main scene which features all the bonus members
     *
     * @return a table view object
     */
    public TableView setUpMainTable() {
        TableView memberList = new TableView();
        // Sets up info columns
        TableColumn column1 = new TableColumn<>("Member number");
        column1.setCellValueFactory(new PropertyValueFactory<>("memberNo"));

        TableColumn column2 = new TableColumn<>("Full name");
        column2.setCellValueFactory((Callback<TableColumn.CellDataFeatures<BonusMember, String>, ObservableValue<String>>) m -> new SimpleStringProperty(m.getValue().getPersonals().getFullname()));

        // Sets up the details button
        TableColumn detailsCol = new TableColumn("");
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("-1"));

        Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>> cellFactory
                = //
                new Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>>() {
                    @Override
                    public TableCell call(final TableColumn<BonusMember, String> param) {
                        final TableCell<BonusMember, String> cell = new TableCell<BonusMember, String>() {

                            final Button btn = new Button("Details");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        // Gets bonus member at the buttons index
                                        BonusMember m = getTableView().getItems().get(getIndex());
                                        updateDetailContainer(m);
                                        rootMain.setRight(detailsContainer);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        detailsCol.setCellFactory(cellFactory);

        // Sets up the delete button
        TableColumn deleteCol = new TableColumn("");
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("-1"));

        Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>> cellFactory2
                = //
                new Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>>() {
                    @Override
                    public TableCell call(final TableColumn<BonusMember, String> param) {
                        final TableCell<BonusMember, String> cell = new TableCell<BonusMember, String>() {

                            final Button btn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        // Gets bonus member at the buttons index
                                        BonusMember m = getTableView().getItems().get(getIndex());
                                        updateDeleteScene(m);
                                        primaryStage.setScene(delScene);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        deleteCol.setCellFactory(cellFactory2);

        // Adds elements to the table view
        memberList.getColumns().addAll(column1, column2, detailsCol, deleteCol);

        return memberList;
    }

    /**
     * A method which updates the contents of the table present on the main scene.
     * This is executed whenever a change (such as deletion) occurs in the application
     */
    public void updateMainScene() {

        // Clears all users from the table view
        memberList.getItems().clear();

        // Fills table view up with bonus members in the member archive
        for (int i = 0; i < members.getSize(); i++) {
            memberList.getItems().add(members.getAt(i));
        }
        mainContainer.getChildren().set(0, memberList);

    }

    /**
     * Sets up the add member scene where one can provide the necessary information and create a new bonus member
     */
    private void setUpAddScene() {

        // Add scene root
        BorderPane rootAdd = new BorderPane();
        rootAdd.setTop(setUpMenuBar());
        // Input field container
        VBox addFields = new VBox();
        // Input fields
        Text text0 = new Text("Add new member");
        text0.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        Text text1 = new Text("First name");
        TextField textField1 = new TextField("");
        Text text2 = new Text("Last name");
        TextField textField2 = new TextField("");
        Text text3 = new Text("Email address");
        TextField textField3 = new TextField("");
        Text text4 = new Text("Password");
        TextField textField4 = new TextField("");
        Text text5 = new Text("Confirm password");
        TextField textField5 = new TextField("");
        Text text6 = new Text("Starting points");
        TextField textField6 = new TextField("0");
        // Makes sure the field only contains digits
        textField6.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField6.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Error field
        Text msg = new Text("");

        // Submit button
        Button submit = new Button("Add member");
        submit.setOnAction(e -> {
            msg.setStyle("-fx-text-fill: red;");
            String firstname, lastname, password, emailAddress, confirmPassword, startingPoints;
            firstname = textField1.getText();
            lastname = textField2.getText();
            emailAddress = textField3.getText();
            password = textField4.getText();
            confirmPassword = textField5.getText();
            startingPoints = textField6.getText();

            // If all of the fields are filled out
            if (!firstname.equals("") && !lastname.equals("") && !password.equals("") && !emailAddress.equals("") && !confirmPassword.equals("") && !startingPoints.equals("")) {
                // If the password and confirm password match
                if (password.equals(confirmPassword)) {

                    // Creates a personals object
                    Personals personals = new Personals(firstname, lastname, emailAddress, password);
                    LocalDate date = LocalDate.now();
                    int bonuspoints = Integer.parseInt(startingPoints);
                    // Makes a new member
                    members.addMember(personals, date, bonuspoints);

                    // Resets all the fields
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textField5.setText("");
                    textField6.setText("0");
                    // Success message
                    msg.setStyle("-fx-text-fill: green;");
                    msg.setText("Member has been added succesfully");

                }
                // Error message
                else {
                    msg.setText("Password and confirm password are not matching");
                }
            }
            // Error message
            else {
                msg.setText("Make sure all the fields are filled out");
            }


        });

        // Adds all the elements to the root
        addFields.getChildren().addAll(text0, msg, text1, textField1, text2, textField2, text3, textField3, text4, textField4, text5, textField5, text6, textField6, submit);

        rootAdd.setCenter(addFields);

        addScene = new Scene(rootAdd, 800, 600);
    }

    // Text fields for the delete scene
    Text memberNo = new Text("");
    Text firstname = new Text("");
    Text lastname = new Text("");
    // Button for the delete scene
    Button confirm = new Button("Confirm");

    /**
     * Sets up the delete scene which is a confirmation scene for the deletion of a bonus member
     */
    private void setUpDeleteScene() {

        // Root of delete scene
        BorderPane rootDel = new BorderPane();
        rootDel.setTop(setUpMenuBar());

        VBox delField = new VBox();

        Text text1 = new Text("Are you sure you want to delete this user?");

        // Confirmation box set up
        HBox buttonBox = new HBox();
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> primaryStage.setScene(mainScene));
        buttonBox.getChildren().addAll(confirm, cancel);

        // Adding elements to field
        delField.getChildren().addAll(text1, memberNo, firstname, lastname, buttonBox);

        rootDel.setCenter(delField);

        delScene = new Scene(rootDel, 800, 600);
    }

    /**
     * Updates the delete scene with info of the user to be deleted
     *
     * @param m the bonus member which info is to be displayed
     */
    public void updateDeleteScene(BonusMember m) {
        // Updates info fields
        memberNo.setText("" + m.getMemberNo());
        firstname.setText(m.getPersonals().getFirstname());
        lastname.setText(m.getPersonals().getSurname());

        // Updates buttons function
        confirm.setOnAction(e -> {
            members.deleteMember(members.getIndex(m));
            updateMainScene();
            primaryStage.setScene(mainScene);
        });
    }

    // Upgrade scene variables
    TableView qualifyingList = new TableView();
    VBox upgradeContainer = new VBox();
    BorderPane rootUpgrade = new BorderPane();

    /**
     * Sets up the upgrade scene in which you can check for any possible users which qualify for an upgrade and then choose which user to upgrade
     */
    private void setUpUpgradeScene() {

        // Root of the upgrade scene
        rootUpgrade.setTop(setUpMenuBar());

        qualifyingList = setUpUpgradeTable();

        // Check button
        Text text1 = new Text("Check for upgrade qualifying members");
        Button check = new Button("Check");
        check.setOnAction(e -> {
            updateUpgradeScene();
        });

        // Adding displayed info
        upgradeContainer.setAlignment(Pos.CENTER);
        upgradeContainer.getChildren().addAll(text1, check, qualifyingList);

        rootUpgrade.setCenter(upgradeContainer);


        upgradeScene = new Scene(rootUpgrade, 800, 600);
    }

    /**
     * Sets up the table view which contains all the users that qualify for an upgrade
     *
     * @return a table view object
     */
    private TableView setUpUpgradeTable() {
        TableView qualifyingList = new TableView();

        // Sets up info columns
        TableColumn column1 = new TableColumn<>("Member number");
        column1.setCellValueFactory(new PropertyValueFactory<>("memberNo"));
        TableColumn column2 = new TableColumn<>("Full name");
        column2.setCellValueFactory((Callback<TableColumn.CellDataFeatures<BonusMember, String>, ObservableValue<String>>) m -> new SimpleStringProperty(m.getValue().getPersonals().getFullname()));
        TableColumn column3 = new TableColumn<>("Enrolle d date");
        column3.setCellValueFactory(new PropertyValueFactory<>("enrolledDate"));

        // Sets up the details button
        TableColumn detailsCol = new TableColumn("");
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("-1"));

        Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>> cellFactory
                = //
                new Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>>() {
                    @Override
                    public TableCell call(final TableColumn<BonusMember, String> param) {
                        final TableCell<BonusMember, String> cell = new TableCell<BonusMember, String>() {

                            final Button btn = new Button("Details");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        // Gets bonus member at the buttons index
                                        BonusMember m = getTableView().getItems().get(getIndex());
                                        updateDetailContainer(m);
                                        rootUpgrade.setRight(detailsContainer);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        detailsCol.setCellFactory(cellFactory);

        // Sets up the details button
        TableColumn upgradeCol = new TableColumn("");
        upgradeCol.setCellValueFactory(new PropertyValueFactory<>("-1"));

        Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>> cellFactory2
                = //
                new Callback<TableColumn<BonusMember, String>, TableCell<BonusMember, String>>() {
                    @Override
                    public TableCell call(final TableColumn<BonusMember, String> param) {
                        final TableCell<BonusMember, String> cell = new TableCell<BonusMember, String>() {

                            final Button btn = new Button("Upgrade");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        // Gets bonus member at the buttons index
                                        BonusMember m = getTableView().getItems().get(getIndex());
                                        members.upgradeMembers(m);
                                        updateUpgradeScene();
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        upgradeCol.setCellFactory(cellFactory2);

        // Adds elements to the table view
        qualifyingList.getColumns().addAll(column1, column2, column3, detailsCol, upgradeCol);

        return qualifyingList;
    }

    /**
     * A method which updates the contents of the table present on the upgrade scene.
     * This is executed whenever a user is upgraded or when checking who qualifies
     */
    private void updateUpgradeScene() {
        // Makes a list of qualifying members as of today
        ArrayList<BonusMember> result = members.checkMembers(LocalDate.now());

        // Clears the table view
        qualifyingList.getItems().clear();

        // Fills the table view with qualifying members
        for (int i = 0; i < result.size(); i++) {
            qualifyingList.getItems().add(result.get(i));
        }
        upgradeContainer.getChildren().set(2, qualifyingList);
    }

    // Text fields for detail container
    Text detail0 = new Text("");
    Text detail1 = new Text("");
    Text info1 = new Text("");
    Text detail2 = new Text("");
    Text info2 = new Text("");
    Text detail3 = new Text("");
    Text info3 = new Text("");
    Text detail4 = new Text("");
    Text info4 = new Text("");
    Text detail5 = new Text("");
    Text info5 = new Text("");
    Text detail6 = new Text("");
    Text info6 = new Text("");

    /**
     * Sets up the detail container in which user details are shown
     */
    private void setUpDetailContainer() {

        // Text field container
        VBox container = new VBox();

        // Text field styles
        detail0.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        detail1.setStyle("-fx-font-weight: bold;");
        detail2.setStyle("-fx-font-weight: bold;");
        detail3.setStyle("-fx-font-weight: bold;");
        detail4.setStyle("-fx-font-weight: bold;");
        detail5.setStyle("-fx-font-weight: bold;");
        detail6.setStyle("-fx-font-weight: bold;");

        // Adds elements to the container
        container.getChildren().addAll(detail0, detail1, info1, detail2, info2, detail3, info3, detail4, info4, detail5, info5, detail6, info6);

        detailsContainer = container;
    }

    /**
     * Updates the detail container witht he details of the bonus member provided
     *
     * @param m bonus member which details are to be shown
     */
    public void updateDetailContainer(BonusMember m) {
        // Fills in the details of bonus member "m"
        detail0.setText("User details");
        detail1.setText("First name");
        info1.setText(m.getPersonals().getFirstname());
        detail2.setText("Last name");
        info2.setText(m.getPersonals().getSurname());
        detail3.setText("Email address");
        info3.setText(m.getPersonals().getEMailAddress());
        detail4.setText("Date joined");
        info4.setText("" + m.getEnrolledDate());
        detail5.setText("Amount of points");
        info5.setText("" + m.getPoints());
        detail6.setText("Membership level");
        String memberLevel = "";
        if (m instanceof GoldMember) {
            memberLevel = "Gold member";
        } else if (m instanceof SilverMember) {
            memberLevel = "Silver member";
        } else {
            memberLevel = "Basic member";
        }
        info6.setText(memberLevel);
    }

    /**
     * Sets up the menu bar present at every scene
     *
     * @return a VBox containing the menu bar
     */
    public VBox setUpMenuBar() {

        // Menu container
        VBox topContainer = new VBox();

        MenuBar menu = new MenuBar();

        // Menu "Options"
        Menu options = new Menu("Options");
        MenuItem goBack = new MenuItem("Go back");
        goBack.setOnAction(e -> {
            if (primaryStage.getScene() != mainScene) {
                updateMainScene();
                primaryStage.setScene(mainScene);
            }

        });
        MenuItem exit = new MenuItem("Exit application");
        exit.setOnAction(e -> stop());
        options.getItems().addAll(goBack, exit);

        // Menu "Menus"
        Menu menus = new Menu("Tools");
        MenuItem add = new MenuItem("Add");
        add.setOnAction(e -> primaryStage.setScene(addScene));
        MenuItem upgrade = new MenuItem("Upgrade");
        upgrade.setOnAction(e -> primaryStage.setScene(upgradeScene));
        menus.getItems().addAll(add, upgrade);

        // Adding elements to menu bar
        menu.getMenus().addAll(options, menus);

        // Adding elements to container
        topContainer.getChildren().add(menu);
        return topContainer;
    }

    /**
     * Stops the application
     */
    @Override
    public void stop() {
        System.exit(0);
    }
}
