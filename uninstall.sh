#!/usr/bin/env bash
set -euo pipefail

ICON_NAME="leonardo"
INSTALL_DIR="${HOME}/Applications"
DESKTOP_DIR="${HOME}/.local/share/applications"
ICON_DIR="${HOME}/.local/share/icons/hicolor/256x256/apps"

echo "==> Removing desktop launcher"
rm -f "${DESKTOP_DIR}/${ICON_NAME}.desktop"

echo "==> Removing icon"
rm -f "${ICON_DIR}/${ICON_NAME}.png"

echo "==> Removing Leonardo AppImages from ${INSTALL_DIR}"
rm -f "${INSTALL_DIR}/Leonardo-"*".AppImage" 2>/dev/null || true

echo "==> Refreshing desktop database (best effort)"
command -v update-desktop-database >/dev/null 2>&1 && update-desktop-database "${DESKTOP_DIR}" || true
command -v xdg-desktop-menu >/dev/null 2>&1 && xdg-desktop-menu forceupdate || true

echo "Uninstalled."