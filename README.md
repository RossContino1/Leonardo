# Leonardo is a simple, fast media conversion app for Linux that lets you convert videos for tools like DaVinci Resolve — without memorizing FFmpeg commands.

![Platform](https://img.shields.io/badge/platform-Linux-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![FFmpeg](https://img.shields.io/badge/Powered%20by-FFmpeg-orange)

⭐ If you find Leonardo useful, consider starring the project!

🎬 Convert videos for DaVinci Resolve on Linux in seconds — no terminal required.

## Screenshots

![About Leonardo](docs/LeoScreen1.png)

![Set For Davinci](docs/LeoScreen2.png)

### Clean, simple interface — no terminal required


## 📰 Media Mention

Leonardo has been featured on LinuxLinks:

🔗 https://www.linuxlinks.com/leonardo-media-conversion-application/

LinuxLinks is a well-known Linux resource site that highlights useful open-source software and developer projects.

GitHub Stars ![GitHub stars](https://img.shields.io/github/stars/RossContino1/Leonardo) ⭐

## Features

* Audio and video conversion
* Clean desktop interface
* Linux AppImage distribution (no install required)
* Cross-platform (Linux, Windows planned, macOS planned)

## Requirements

Leonardo requires FFmpeg to be installed on your system.

### Install FFmpeg

**Arch / Garuda:**
sudo pacman -S ffmpeg

**Ubuntu / Debian:**
sudo apt install ffmpeg

**Fedora:**
sudo dnf install ffmpeg

## Leonardo 10.0.14
- Added optional donation support (non-intrusive, one-time prompt)
- Improved release packaging and reliability
- Fixed resource handling (icons, help system now fully bundled)

## Leonardo 10.0.13
- Fixed TikTok 9:16 formatting issue
- Small fix for those installing on Fedora - checks for missing libraries

## Leonardo 10.0.12

- Java/Eclipse rewrite of Leonardo
- Packaged as AppImage for Linux
- Improved FFmpeg integration
- Fixed GNOME icon issues
- Added GitHub link in menu
- Updated help system

Download the latest release below (AppImage + JAR).

Remember — at Bytes, Bread, and Barbecue we keep your code crispy and your files smokin’ hot.


## Linux (AppImage)

1. Download the AppImage
2. Make executable:
   chmod +x Leonardo-*.AppImage
3. Run:
   ./Leonardo-*.AppImage
   
✅ Runs on most Linux systems — no install required (AppImage)

[![Download ZIP](https://img.shields.io/badge/Download-ZIP-blue?style=for-the-badge)](https://bytesbreadbbq.com/leonardo/)

⚠️ Having Trouble Launching?

If the AppImage doesn’t start, your system may be missing FUSE.

Fix (quick):

**Fedora:**

sudo dnf install fuse fuse-libs

**Ubuntu / Mint:**

sudo apt install libfuse2

**Arch:**

sudo pacman -S fuse2

👉 After installing, try launching again.

## Donations

---

## ☕ Support Leonardo

Leonardo is free to use. If Leonardo saves you time or simplifies your workflow, consider supporting development:

[![Support via PayPal](https://img.shields.io/badge/Support-PayPal-blue?style=for-the-badge&logo=paypal)](https://www.paypal.com/donate/?hosted_button_id=XS9MXN5AE5P3S)

Your support helps keep the code crispy and the files smokin’ hot.


## License

This project is licensed under the MIT License.

