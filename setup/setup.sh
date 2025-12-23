#!/bin/sh
set -e

cd deepseektutor

echo "Starting setup..."

# Link the build and Gradle cache directories into the user filesystem
ln -sf /bootstrap-apps/java-springai/build /usercode/FILESYSTEM/deepseektutor/build
ln -sf /bootstrap-apps/java-springai/.gradle /usercode/FILESYSTEM/deepseektutor/.gradle 

# Run Gradle to assemble the project only (no tests)
gradle --offline --configure-on-demand --build-cache --no-rebuild -q assemble || true

# Mark that setup has completed
touch /var/tmp/.setup_done
echo "Setup complete."