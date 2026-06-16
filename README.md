# Leonardo

Leonardo is a simple FFmpeg-powered video conversion tool that helps Linux creators prepare media for DaVinci Resolve, YouTube, TikTok, Pinterest, and more—without memorizing command-line options.

Built with Java and distributed as an AppImage, Leonardo provides common video conversion workflows through a clean desktop interface while leveraging the power of FFmpeg behind the scenes.

![Platform](https://img.shields.io/badge/platform-Linux-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![FFmpeg](https://img.shields.io/badge/Powered%20by-FFmpeg-orange)

⭐ If you find Leonardo useful, consider starring the project!

## Why Leonardo?

FFmpeg is incredibly powerful, but many common video conversion tasks require long command lines.

Leonardo packages proven FFmpeg workflows into simple presets so you can focus on creating content instead of searching for conversion commands.

Perfect for:

- DaVinci Resolve Linux users
- YouTube creators
- TikTok creators needing vertical 9:16 video
- Pinterest creators requiring compatible H.264 exports
- Anyone who wants FFmpeg power without the terminal

## Screenshots

![About Leonardo](docs/LeoScreen1.png)

![Leonardo Main Window](docs/LeoScreen2.png)

### Clean, simple interface — no terminal required

## Media Mention

Leonardo has been featured on LinuxLinks:

https://www.linuxlinks.com/leonardo-media-conversion-application/

LinuxLinks is a well-known Linux resource site that highlights useful open-source software and developer projects.

GitHub Stars ![GitHub stars](https://img.shields.io/github/stars/RossContino1/Leonardo) ⭐

## Features

- DaVinci Resolve Linux compatibility conversion
- OBS recording remux (fast copy mode, no re-encoding)
- YouTube H.264 export
- TikTok Vertical 9:16 auto-crop export
- Pinterest H.264 export
- Drag-and-drop file support
- AppImage distribution
- Cross-platform Java version available
- FFmpeg-powered conversions
- Dark, light, and system theme support
- Integrated help system
- Open source (MIT License)

## Requirements

Leonardo requires FFmpeg to be installed on your system.

### Install FFmpeg

**Arch / Garuda**

```bash
sudo pacman -S ffmpeg
```

**Ubuntu / Debian**

```bash
sudo apt install ffmpeg
```

**Fedora**

```bash
sudo dnf install ffmpeg
```

## Release Notes

### Leonardo 10.0.15

- Added Pinterest H.264 export preset
- Improved compatibility with Pinterest video uploads
- H.264 High Profile Level 4.1 export settings
- AAC audio support
- Additional workflow option for social media creators

### Leonardo 10.0.14

- Added optional donation support (non-intrusive, one-time prompt)
- Improved release packaging and reliability
- Fixed resource handling (icons and help system fully bundled)

### Leonardo 10.0.13

- Fixed TikTok 9:16 formatting issue
- Small fix for Fedora users involving missing libraries

### Leonardo 10.0.12

- Java/Eclipse rewrite of Leonardo
- Packaged as AppImage for Linux
- Improved FFmpeg integration
- Fixed GNOME icon issues
- Added GitHub link in menu
- Updated help system

## Linux (AppImage)

1. Download the AppImage
2. Make executable:

```bash
chmod +x Leonardo-*.AppImage
```

3. Run:

```bash
./Leonardo-*.AppImage
```

✅ Runs on most Linux systems — no installation required.

[![Download ZIP](https://img.shields.io/badge/Download-ZIP-blue?style=for-the-badge)](https://bytesbreadbbq.com/leonardo/)

## Cross-Platform (JAR)

Leonardo can also run on Windows, macOS, or Linux using Java:

```bash
java -jar Leonardo-*.jar
```

Requires Java 17 or newer.

## Verify Download Integrity

SHA256 checksums are provided with each release.

```bash
sha256sum Leonardo-*.AppImage
```

Compare the output with the checksum published on the release page.

## Having Trouble Launching?

If the AppImage does not start, your system may be missing FUSE.

### Fedora

```bash
sudo dnf install fuse fuse-libs
```

### Ubuntu / Mint

```bash
sudo apt install libfuse2
```

### Arch

```bash
sudo pacman -S fuse2
```

After installing, try launching Leonardo again.

## Support Leonardo

Leonardo is free to use. If it saves you time or simplifies your workflow, consider supporting development.

[![Support via PayPal](https://img.shields.io/badge/Support-PayPal-blue?style=for-the-badge&logo=paypal)](https://www.paypal.com/donate/?hosted_button_id=XS9MXN5AE5P3S)

Your support helps keep the code crispy and the files smokin’ hot.

## License

This project is licensed under the MIT License.
