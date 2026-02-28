Leonardo – Linux AppImage
=========================

Thank you for downloading Leonardo!

Leonardo-10.0.7-Linux/
 ├── Leonardo-10.0.7.AppImage
 ├── install.sh
 ├── uninstall.sh
 └── README.txt
 └── LICENSE.txt
 
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Quick Start
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. Make the AppImage executable:
   chmod +x Leonardo-*.AppImage

2. Run Leonardo:
   ./Leonardo-*.AppImage


━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Desktop Integration (GNOME / KDE Recommended)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
If you run the AppImage directly, some desktop environments (especially GNOME)
may show a generic icon in the dock.

To install a proper launcher and icon (no admin rights required):

1. Extract the zip file
2. Open a Terminal in the extracted folder
3. Run:
   chmod +x install.sh Leonardo-*.AppImage
   ./install.sh

Then press the Super/Windows key and search for:
   Leonardo

To uninstall:
   ./uninstall.sh

Note:
On GNOME Wayland, you may need to log out and log back in
for the launcher and icon to appear immediately.


━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Requirements
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Leonardo requires FFmpeg to be installed for media conversion.
FFmpeg is not included with Leonardo and must be installed separately.
FFmpeg is a trademark of the FFmpeg project and is licensed under LGPL/GPL depending on the build.

Install FFmpeg:

Arch / Garuda:
   sudo pacman -S ffmpeg

Ubuntu / Debian:
   sudo apt install ffmpeg

Fedora:
   sudo dnf install ffmpeg



━━━━━━━━━━━━━━━━━━━━━━━━━━━━
About
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Leonardo is open source software licensed under the MIT License.

License:
This software is licensed under the MIT License. See LICENSE.txt.

GitHub:
https://github.com/RossContino1/Leonardo