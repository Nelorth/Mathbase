/*
 * Copyright (c) 2017 MathBox P-Seminar 16/18. All rights reserved.
 * This product is licensed under the GNU General Public License v3.0.
 * See LICENSE file for further information.
 */

package de.apian.mathbase.exc;

/**
 * Kollision der Titel zweier Knoten.
 *
 * @author Benedikt Mödl
 * @version 1.0
 * @since 1.0
 */
public class TitleCollisionException extends Exception {

    /**
     * Standard-Konstruktor
     *
     * @since 1.0
     */
    public TitleCollisionException() {
        super();
    }

    /**
     * Standard-Konstruktor mit Nachricht
     *
     * @param message Nachricht
     * @since 1.0
     */
    public TitleCollisionException(String message) {
        super(message);
    }

    /**
     * Standard-Konstruktor mit Nachricht und Grund für diese Ausnahme
     *
     * @param message Nachricht
     * @param cause   Grund für Ausnahme
     * @since 1.0
     */
    public TitleCollisionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Standard-Konstruktor mit Grund für diese Ausnahme
     *
     * @param cause Grund für Ausnahme
     * @since 1.0
     */
    public TitleCollisionException(Throwable cause) {
        super(cause);
    }
}