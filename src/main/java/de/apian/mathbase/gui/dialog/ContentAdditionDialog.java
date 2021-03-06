/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.gui.dialog;

import de.apian.mathbase.util.Constants;
import de.apian.mathbase.util.Images;
import de.apian.mathbase.util.Logging;
import de.apian.mathbase.xml.Content;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

/**
 * Dialog zum Hinzufügen eines Inhalts
 *
 * @author Benedikt Mödl
 * @version 1.0
 * @see de.apian.mathbase.xml.Content
 * @since 1.0
 */
public class ContentAdditionDialog extends Dialog<Content> {
    private Content.Type type;
    private String filePath;
    private String caption;
    private Label infoLabel;
    private TextField titleField;
    private TextArea descriptionArea;

    public ContentAdditionDialog(Window owner) {
        type = Content.Type.OTHER;
        filePath = null;
        caption = null;

        initOwner(owner);
        setTitle(Constants.BUNDLE.getString("content_management"));
        setHeaderText(Constants.BUNDLE.getString("add_content"));
        setGraphic(new ImageView(Images.getInternal("icons_x48/multimedia.png")));
        setResizable(true);


        ComboBox<Content.Type> comboBox = new ComboBox<>(FXCollections.observableArrayList(Content.Type.values()));
        comboBox.setPromptText(Constants.BUNDLE.getString("please_select"));
        comboBox.setConverter(new StringConverter<Content.Type>() {
            @Override
            public String toString(Content.Type object) {
                return Constants.BUNDLE.getString(object.toString());
            }

            @Override
            public Content.Type fromString(String string) {
                return null;
            }
        });

        comboBox.setOnAction(a -> {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            Label typeLabel = new Label(Constants.BUNDLE.getString("content_type") + ":");
            GridPane.setHalignment(typeLabel, HPos.RIGHT);
            gridPane.addRow(0, typeLabel, comboBox);

            Label titleLabel = new Label(Constants.BUNDLE.getString("optional_title") + ":");
            GridPane.setHalignment(titleLabel, HPos.RIGHT);
            titleField = new TextField();

            infoLabel = new Label();
            infoLabel.setTextFill(Color.RED);
            infoLabel.setVisible(false);
            GridPane.setHgrow(titleField, Priority.ALWAYS);
            gridPane.addRow(1, titleLabel, titleField);
            gridPane.add(infoLabel, 1, 3);

            type = comboBox.getSelectionModel().getSelectedItem();
            if (type.equals(Content.Type.DESCRIPTION))
                initDescriptionMask(gridPane);
            else
                initDefaultMask(gridPane);

            getDialogPane().setContent(gridPane);

            getDialogPane().getScene().getWindow().sizeToScene();
            getDialogPane().getScene().getWindow().centerOnScreen();
            Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
            okButton.setDisable(false);
            titleField.requestFocus();
        });

        getDialogPane().setContent(new HBox(comboBox));

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        initOkButton();
        setResultConverter(buttonType -> {
            if (buttonType.equals(ButtonType.OK))
                return new Content(type, filePath, caption);
            return null;
        });
    }

    private void initOkButton() {
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        okButton.addEventFilter(ActionEvent.ACTION, a -> {
            caption = titleField.getText();

            if (caption != null && caption.isEmpty())
                caption = null;

            if (type == Content.Type.DESCRIPTION) {
                try {
                    Path path = Files.createTempFile("mathbase", ".txt");
                    Files.write(path, descriptionArea.getText().getBytes("utf-8"));
                    filePath = path.toAbsolutePath().toString();
                } catch (IOException e) {
                    Logging.log(Level.WARNING, "Fehler beim Erstellen der temporären Datei zum Hinzufügen einer Beschreibung", e);
                    new WarningAlert().show();
                    a.consume();
                }
            }

            if (filePath == null || filePath.isEmpty() || descriptionArea != null && descriptionArea.getText().isEmpty()) {
                infoLabel.setVisible(true);
                a.consume();
            }
        });
    }

    private void initDescriptionMask(GridPane gridPane) {
        infoLabel.setText(Constants.BUNDLE.getString("description_empty"));
        Label descriptionLabel = new Label(Constants.BUNDLE.getString("description") + ":");
        GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
        GridPane.setMargin(descriptionLabel, new Insets(3, 0, 0, 0));
        descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(5);
        ColumnConstraints c = new ColumnConstraints();
        c.setMaxWidth(100);
        gridPane.getColumnConstraints().add(c);
        GridPane.setHgrow(descriptionLabel, Priority.ALWAYS);
        GridPane.setVgrow(descriptionLabel, Priority.ALWAYS);
        GridPane.setValignment(descriptionLabel, VPos.TOP);
        gridPane.addRow(2, descriptionLabel, descriptionArea);
    }

    private void initDefaultMask(GridPane gridPane) {
        infoLabel.setText(Constants.BUNDLE.getString("file_empty"));

        //Bringt Dateierweiterungen in ein schönes Format
        StringBuilder fileExtensionsBuilder = new StringBuilder();
        for (String s : type.getFileExtensions()) {
            fileExtensionsBuilder.append(s).append(", ");
        }
        if (fileExtensionsBuilder.length() > 2) {
            //Herrauslöschen des letzten ", ", da es unnötig ist.
            fileExtensionsBuilder.delete(fileExtensionsBuilder.length() - 2, fileExtensionsBuilder.length());
        } else {
            // Sollte nicht vorkommen, da jeder Typ außer OTHER besondere Dateierweiterungen
            // zugeordnet bekommen sollte
            fileExtensionsBuilder.append("*.*");
        }


        Label selectFileLabel = new Label(Constants.BUNDLE.getString("file") + " (" +
                fileExtensionsBuilder.toString() + "):");
        GridPane.setHalignment(selectFileLabel, HPos.RIGHT);
        ColumnConstraints c = new ColumnConstraints();
        c.setMaxWidth(Math.max(new Text(selectFileLabel.getText()).getLayoutBounds().getWidth(), 100));
        gridPane.getColumnConstraints().add(c);
        Button selectFileButton = new Button(Constants.BUNDLE.getString("please_select"));
        gridPane.addRow(2, selectFileLabel, selectFileButton);
        selectFileButton.setOnAction(a1 -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Constants.BUNDLE.
                    getString("file"), (type.getFileExtensions().length > 0 ? type.getFileExtensions() :
                    new String[]{"*.*"})));
            File file = fileChooser.showOpenDialog(getOwner());
            if (file != null) {
                filePath = file.getAbsolutePath();
                selectFileButton.setText(file.getName());
            }
        });
    }
}
