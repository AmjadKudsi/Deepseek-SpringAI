#!/bin/bash
set -e

# Check if setup is complete
if [ ! -f /var/tmp/.setup_done ]; then
  echo "Setup in progress. Please try again in a few seconds..."
  exit 1
fi

# Navigate to project directory
cd deepseektutor

# Launch the Spring Boot application using bootRun
gradle bootRun --quiet