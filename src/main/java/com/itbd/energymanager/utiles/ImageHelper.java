/*
MIT License

Copyright (c) 2020 Marco Antonio Anastacio Cintra <anastaciocintra@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.itbd.energymanager.utiles;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Process big images end send to the printer.
 * The image is sliced into small pieces then send to the printer
 * When to use: If your image overflow the buffer of the printer, the output can be unpredictable.
 * Beta version
 */
public class ImageHelper {
    final int maxWidth;
    final int maxHeight;

    /**
     * creates an ImageHelper with default values
     */
    public ImageHelper() {
        this(576, 48);
    }

    /**
     * create an ImageHelper
     *
     * @param maxWidth  read your printer documentation to discover the width max dots
     * @param maxHeight test / read your printer to discover the printer buffer size, this number should be as bigger as possible
     */
    public ImageHelper(int maxWidth, int maxHeight) {
        //maxHeight need to be multiple of 24
        if (maxHeight < 24) maxHeight = 24;
        if ((maxHeight % 24) != 0) maxHeight -= (maxHeight % 24);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public static Background loadBackgroundImage(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        return new Background(backgroundImage);
    }
}
