/*
 * Leonardo - Media Conversion Tool
 * Copyright (c) 2026 Ross Contino
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */




package com.ross.leonardo;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public final class AppInfo {

    public static final String NAME = "Leonardo";
    public static final String VERSION = "10.0.14";
    public static final String AUTHOR = "Ross Contino";
    public static final String WEBSITE = "https://bytesbreadbbq.com";
    public static final String COPYRIGHT = "© 2026 Ross Contino";
    public static final String DONATE_URL = "https://www.paypal.com/donate/?hosted_button_id=XS9MXN5AE5P3S";

    private static final String[] ICON_PATHS = {
            "/leonardo.png",
            "/help/leonardo.png"
    };

    private AppInfo() {
    }

    public static URL getIconUrl() {
        for (String path : ICON_PATHS) {
            URL url = AppInfo.class.getResource(path);
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    public static ImageIcon getImageIcon() {
        URL url = getIconUrl();
        return url != null ? new ImageIcon(url) : null;
    }

    public static Image getAppImage() {
        ImageIcon icon = getImageIcon();
        return icon != null ? icon.getImage() : null;
    }
}