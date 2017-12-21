package eu.audiocity.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

class TopMenu extends MenuBar {
    private MainWindow mainWindow;

    private Menu file = new javafx.scene.control.Menu("File");
    private Menu edit = new javafx.scene.control.Menu("Edit");
    private MenuItem importSoundtrack = new MenuItem("Import Soundtrack");

    TopMenu(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        this.getMenus().addAll(this.file, this.edit);
        this.file.getItems().add(this.importSoundtrack);
        this.getStyleClass().add("top-menu");

        this.importSoundtrack.setOnAction(e -> {
            this.mainWindow.importSoundtrack();
        });
    }
}
