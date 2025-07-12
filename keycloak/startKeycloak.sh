#!/bin/bash

# Configure Keycloak environment variables
KC_HOME="/opt/keycloak"
KC_BIN="$KC_HOME/bin"

# Validate that the keycloak home directory exists
if [ ! -d "$KC_HOME" ]; then
  echo "Keycloak home directory does not exist: $KC_HOME"
  exit 1
fi

if [ ! -x "$KC_BIN/kc.sh" ]; then
  echo "Keycloak script not found or not executable: $KC_BIN/kc.sh"
  exit 1
fi

# Start Keycloak with the provided arguments
cd "$KC_HOME" || exit 1
exec "$KC_BIN/kc.sh" start-dev --http-port=9092 "$@"
