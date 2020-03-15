import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    MemberArchive members;
    Stage primaryStage;
    Scene mainScene;
    Scene addScene;
    Scene delScene;
    Scene upgradeScene;
    Scene detailScene;

    public GUI() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;


        setUpMainScene();
        setUpAddScene();
        setUpDelScene();
        setUpUpgradeScene();
        setUpDetailScene();

        primaryStage.setTitle("Bonus member");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void setUpMainScene() {

        BorderPane rootMain = new BorderPane();
        rootMain.setTop(setUpMenuBar());
        mainScene = new Scene(rootMain, 800, 600);

    }

    private void setUpAddScene() {

        BorderPane rootAdd = new BorderPane();
        rootAdd.setTop(setUpMenuBar());
        addScene = new Scene(rootAdd, 800, 600);
    }

    private void setUpDelScene() {
        BorderPane rootDel = new BorderPane();
        rootDel.setTop(setUpMenuBar());
        delScene = new Scene(rootDel, 800, 600);
    }

    private void setUpUpgradeScene() {
        BorderPane rootUpgrade = new BorderPane();
        rootUpgrade.setTop(setUpMenuBar());
        upgradeScene = new Scene(rootUpgrade, 800, 600);
    }

    private void setUpDetailScene() {
        BorderPane rootDetail = new BorderPane();
        rootDetail.setTop(setUpMenuBar());
        detailScene = new Scene(rootDetail, 800, 600);
    }

    public VBox setUpMenuBar() {
        VBox topContainer = new VBox();

        MenuBar menu = new MenuBar();

        Menu options = new Menu("Options");
        MenuItem goBack = new MenuItem("Go back");
        goBack.setOnAction(e -> {
            if (primaryStage.getScene() != mainScene) {
                primaryStage.setScene(mainScene);
            }

        });
        options.getItems().addAll(goBack);

        Menu menus = new Menu("Tools");
        MenuItem add = new MenuItem("Add");
        add.setOnAction(e -> primaryStage.setScene(addScene));
        MenuItem del = new MenuItem("Delete");
        del.setOnAction(e -> primaryStage.setScene(delScene));
        MenuItem upgrade = new MenuItem("Upgrade");
        upgrade.setOnAction(e -> primaryStage.setScene(upgradeScene));
        menus.getItems().addAll(add, del, upgrade);

        menu.getMenus().addAll(options, menus);

        topContainer.getChildren().add(menu);
        return topContainer;
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
