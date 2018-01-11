/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.gui.content;

import de.apian.mathbase.util.Logging;
import de.apian.mathbase.xml.Content;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Linkkachel.
 *
 * @author Nikolas Kirschstein
 * @version 1.0
 * @since 1.0
 */
public class LinkTile extends AbstractTile {
    public LinkTile(Content content, String directoryPath) {
        super(content, directoryPath);

        String filename = content.getFilename();

        Hyperlink hyperlink = new Hyperlink(filename.substring(0, filename.lastIndexOf('.')));
        hyperlink.setOnAction(a -> {
            try {
                Desktop.getDesktop().open(Paths.get(directoryPath, filename).toFile());
            } catch (IOException e) {
                Logging.log(Level.WARNING, "Datei " + filename + " konnte nicht geöffnet werden.");
            }
        });
        setCenter(hyperlink);
    }
}
