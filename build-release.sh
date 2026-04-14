#!/usr/bin/env bash
set -euo pipefail

APP_NAME="Leonardo"
VERSION="10.0.14"
RELEASE_ROOT="release"
RELEASE_DIR="${RELEASE_ROOT}/${APP_NAME}-${VERSION}"

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APPDIR="${PROJECT_DIR}/Leonardo.AppDir"
LINUXDEPLOY="${PROJECT_DIR}/linuxdeploy-x86_64.AppImage"

INPUT_JAR="${PROJECT_DIR}/Leonardo.jar"
ICON_SRC="${PROJECT_DIR}/leonardo.png"

APPIMAGE_NAME="${APP_NAME}-${VERSION}.AppImage"
JAR_NAME="${APP_NAME}-${VERSION}.jar"

echo "==> Cleaning old build"
rm -rf "${APPDIR}" "${RELEASE_ROOT}"
mkdir -p "${APPDIR}/usr/bin"
mkdir -p "${APPDIR}/usr/share/applications"
mkdir -p "${APPDIR}/usr/share/icons/hicolor/256x256/apps"

mkdir -p "${RELEASE_DIR}"

# ---- Validate ----
[[ -f "${INPUT_JAR}" ]] || { echo "Missing Leonardo.jar"; exit 1; }
[[ -f "${ICON_SRC}" ]] || { echo "Missing icon"; exit 1; }
[[ -f "${LINUXDEPLOY}" ]] || { echo "Missing linuxdeploy"; exit 1; }

chmod +x "${LINUXDEPLOY}"

# ---- Copy JAR ----
cp "${INPUT_JAR}" "${APPDIR}/usr/bin/${JAR_NAME}"

# ---- Icon ----
cp "${ICON_SRC}" "${APPDIR}/leonardo.png"
cp "${ICON_SRC}" "${APPDIR}/.DirIcon"
cp "${ICON_SRC}" "${APPDIR}/usr/share/icons/hicolor/256x256/apps/leonardo.png"

# ---- Desktop file ----
cat > "${APPDIR}/leonardo.desktop" <<EOF
[Desktop Entry]
Type=Application
Name=Leonardo
Exec=AppRun
Icon=leonardo
Terminal=false
Categories=AudioVideo;
StartupWMClass=com-ross-leonardo-LeonardoApp
EOF

cp "${APPDIR}/leonardo.desktop" "${APPDIR}/usr/share/applications/"

# ---- AppRun ----
cat > "${APPDIR}/AppRun" <<EOF
#!/usr/bin/env bash
HERE="\$(dirname "\$(readlink -f "\$0")")"
exec java -jar "\$HERE/usr/bin/${JAR_NAME}" "\$@"
EOF
chmod +x "${APPDIR}/AppRun"

echo "==> Building AppImage"
(
  cd "${PROJECT_DIR}"
  ARCH=x86_64 ./linuxdeploy-x86_64.AppImage \
    --appdir "${APPDIR}" \
    --desktop-file "${APPDIR}/usr/share/applications/leonardo.desktop" \
    --output appimage
)

FOUND_APPIMAGE="$(find "${PROJECT_DIR}" -maxdepth 1 -name '*.AppImage' ! -name 'linuxdeploy-*.AppImage' | head -n 1)"

mv "${FOUND_APPIMAGE}" "${RELEASE_DIR}/${APPIMAGE_NAME}"

# ---- Copy release files ----
cp "${INPUT_JAR}" "${RELEASE_DIR}/${JAR_NAME}"
cp "${PROJECT_DIR}/install.sh" "${RELEASE_DIR}/install.sh"
cp "${PROJECT_DIR}/uninstall.sh" "${RELEASE_DIR}/uninstall.sh"
cp "${ICON_SRC}" "${RELEASE_DIR}/leonardo.png"

# ✅ Include README
cp "${PROJECT_DIR}/README-linux.txt" "${RELEASE_DIR}/README-linux.txt"

# ✅ Include License
cp "${PROJECT_DIR}/LICENSE.txt" "${RELEASE_DIR}/LICENSE.txt"

chmod +x "${RELEASE_DIR}/install.sh" "${RELEASE_DIR}/uninstall.sh"

# ---- Zip it properly ----
echo "==> Creating zip package"
cd "${RELEASE_ROOT}"
zip -r "${APP_NAME}-${VERSION}.zip" "${APP_NAME}-${VERSION}"
cd ..

echo
echo "✅ Release complete:"
echo "  ${RELEASE_ROOT}/${APP_NAME}-${VERSION}.zip"
