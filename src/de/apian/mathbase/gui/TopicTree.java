package de.apian.mathbase.gui;

import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * Der Themenbaum im Hauptmenü als Unterklasse von <tt>Parent</tt>
 *
 * @author Nikolas Kirschstein
 * @version 1.0
 * @since 1.0
 */
public class TopicTree extends Parent {
    /**
     * Statische Instanzreferenz auf das Singleton-Objekt
     *
     * @since 1.0
     */
    private static TopicTree instance;

    /**
     * Privater Konstruktor aufgrund des Entwurfsmusters
     *
     * @since 1.0
     */
    private TopicTree() {
        Label label = new Label("Text");
        getChildren().add(label);
        System.out.println(Mathbase.getInstance());
    }

    /**
     * Statische Instanzmethode aufgrund des Singleton-Entwurfsmusters
     *
     * @return Die einzige Instanz von <tt>TopicTree</tt>
     */
    static TopicTree getInstance() {
        if (instance == null)
            instance = new TopicTree();
        return instance;
    }
}
