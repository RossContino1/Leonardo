#!/usr/bin/env bash
set -euo pipefail

APP_NAME="Leonardo"
APP_ID="com-ross-leonardo-LeonardoApp"     # must match: xprop WM_CLASS
ICON_NAME="leonardo"                       # leonardo.png
CATEGORIES="AudioVideo;"
COMMENT="Audio and Media Conversion Tool"

# User-level install locations
INSTALL_DIR="${HOME}/Applications"
DESKTOP_DIR="${HOME}/.local/share/applications"
ICON_THEME_DIR="${HOME}/.local/share/icons/hicolor/256x256/apps"
PIXMAP_DIR="${HOME}/.local/share/pixmaps"

# Script location (release folder)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Find AppImage in the same folder as this script
APPIMAGE_SRC="$(ls -1 "${SCRIPT_DIR}"/Leonardo-*.AppImage 2>/dev/null | head -n 1 || true)"
if [[ -z "${APPIMAGE_SRC}" ]]; then
  APPIMAGE_SRC="$(ls -1 "${SCRIPT_DIR}"/*.AppImage 2>/dev/null | head -n 1 || true)"
fi

if [[ -z "${APPIMAGE_SRC}" ]]; then
  echo "ERROR: No .AppImage found next to install.sh"
  echo "Place install.sh in the same folder as Leonardo-*.AppImage"
  exit 1
fi

mkdir -p "${INSTALL_DIR}" "${DESKTOP_DIR}" "${ICON_THEME_DIR}" "${PIXMAP_DIR}"

APPIMAGE_BASENAME="$(basename "${APPIMAGE_SRC}")"
APPIMAGE_DST="${INSTALL_DIR}/${APPIMAGE_BASENAME}"

echo "==> Removing older Leonardo AppImages (clean upgrade)"
rm -f "${INSTALL_DIR}/Leonardo-"*.AppImage 2>/dev/null || true

echo "==> Installing AppImage to: ${APPIMAGE_DST}"
cp -f "${APPIMAGE_SRC}" "${APPIMAGE_DST}"
chmod +x "${APPIMAGE_DST}"

echo "==> Installing icon"
BUNDLED_ICON="${SCRIPT_DIR}/${ICON_NAME}.png"

if [[ -f "${BUNDLED_ICON}" ]]; then
  # Preferred: icon shipped with the release zip (most reliable)
  cp -f "${BUNDLED_ICON}" "${ICON_THEME_DIR}/${ICON_NAME}.png"
  cp -f "${BUNDLED_ICON}" "${PIXMAP_DIR}/${ICON_NAME}.png"
else
  echo "WARN: ${ICON_NAME}.png not found next to install.sh."
  echo "      For best KDE/GNOME compatibility, include ${ICON_NAME}.png in the zip."
  echo "      Attempting to extract icon from AppImage (best effort)..."

  TMPDIR="$(mktemp -d)"
  cleanup() { rm -rf "${TMPDIR}"; }
  trap cleanup EXIT

  if ( cd "${TMPDIR}" && "${APPIMAGE_DST}" --appimage-extract >/dev/null 2>&1 ); then
    if [[ -f "${TMPDIR}/squashfs-root/${ICON_NAME}.png" ]]; then
      cp -f "${TMPDIR}/squashfs-root/${ICON_NAME}.png" "${ICON_THEME_DIR}/${ICON_NAME}.png"
      cp -f "${TMPDIR}/squashfs-root/${ICON_NAME}.png" "${PIXMAP_DIR}/${ICON_NAME}.png"
    elif [[ -f "${TMPDIR}/squashfs-root/.DirIcon" ]]; then
      cp -f "${TMPDIR}/squashfs-root/.DirIcon" "${ICON_THEME_DIR}/${ICON_NAME}.png"
      cp -f "${TMPDIR}/squashfs-root/.DirIcon" "${PIXMAP_DIR}/${ICON_NAME}.png"
    else
      echo "WARN: Could not find icon inside AppImage."
    fi
  else
    echo "WARN: AppImage extraction failed (this is OK if you ship leonardo.png in the zip)."
  fi
fi

chmod 644 "${ICON_THEME_DIR}/${ICON_NAME}.png" 2>/dev/null || true
chmod 644 "${PIXMAP_DIR}/${ICON_NAME}.png" 2>/dev/null || true

DESKTOP_FILE="${DESKTOP_DIR}/${ICON_NAME}.desktop"
ICON_ABS="${PIXMAP_DIR}/${ICON_NAME}.png"

echo "==> Writing desktop launcher: ${DESKTOP_FILE}"
cat > "${DESKTOP_FILE}" <<EOF
[Desktop Entry]
Type=Application
Name=${APP_NAME}
Comment=${COMMENT}
Exec=${APPIMAGE_DST} %U
Icon=${ICON_ABS}
Terminal=false
Categories=${CATEGORIES}
StartupWMClass=${APP_ID}
EOF

chmod 644 "${DESKTOP_FILE}"

echo "==> Refreshing desktop caches (best effort)"
command -v update-desktop-database >/dev/null 2>&1 && update-desktop-database "${DESKTOP_DIR}" || true
command -v xdg-desktop-menu >/dev/null 2>&1 && xdg-desktop-menu forceupdate || true
command -v kbuildsycoca5 >/dev/null 2>&1 && kbuildsycoca5 --noincremental || true

echo
echo "Installed!"
echo "• Launch from your application menu by searching: ${APP_NAME}"
echo "• If it doesn't appear immediately:"
echo "  - GNOME Wayland: log out and log back in"
echo "  - KDE Plasma: log out/in (or restart Plasma)"